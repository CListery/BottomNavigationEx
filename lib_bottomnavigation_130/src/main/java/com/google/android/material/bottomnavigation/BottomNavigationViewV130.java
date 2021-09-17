package com.google.android.material.bottomnavigation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.MenuKt;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.internal.ThemeEnforcement;
import com.yh.bottomnavigation_base.IBottomNavigationEx;
import com.yh.bottomnavigation_base.IMenuDoubleClickListener;
import com.yh.bottomnavigation_base.IMenuListener;
import com.yh.bottomnavigation_base.helper.BNVHelper;
import com.yh.bottomnavigation_base.helper.VP2Helper;
import com.yh.bottomnavigation_base.helper.VPHelper;
import com.yh.bottomnavigation_base.internal.InnerListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import kotlin.collections.CollectionsKt;

/**
 * Created by yu on 2016/11/10.
 */
@SuppressLint("RestrictedApi")
public class BottomNavigationViewV130 extends BottomNavigationView implements IBottomNavigationEx<BottomNavigationView, BottomNavigationMenuView, BottomNavigationItemView> {
    // used for animation
    private float mShiftAmount;
    private float mScaleUpFactor;
    private float mScaleDownFactor;
    private boolean animationRecord;
    private float mLargeLabelSize;
    private float mSmallLabelSize;
    private boolean visibilityTextSizeRecord;
    private boolean visibilityHeightRecord;
    private int mItemHeight;
    private boolean textVisibility = true;
    // used for animation end

    // used for setupWithViewPager
    private BottomNavigationMenuView mMenuView;
    private BottomNavigationItemView[] mButtons;
    // used for setupWithViewPager end

    // detect navigation tab changes when the user clicking on navigation item
    private static boolean isNavigationItemClicking = false;

    private InnerListener mInnerListener;
    private final BNVHelper bnvHelper;

    public BottomNavigationViewV130(Context context) {
        this(context, null);
    }

    public BottomNavigationViewV130(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigationViewV130(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TintTypedArray a = ThemeEnforcement.obtainTintedStyledAttributes(context, attrs,
                com.google.android.material.R.styleable.BottomNavigationView,
                defStyleAttr, com.google.android.material.R.style.Widget_Design_BottomNavigationView,
                com.google.android.material.R.styleable.BottomNavigationView_itemTextAppearanceInactive,
                com.google.android.material.R.styleable.BottomNavigationView_itemTextAppearanceActive);
        // clear if you don't have set item icon tint list
        if (!a.hasValue(com.google.android.material.R.styleable.BottomNavigationView_itemIconTint)) {
            clearIconTintColor();
        }
        a.recycle();

        setOnNavigationItemSelectedListener(item -> {
            if (null != mInnerListener) {
                return mInnerListener.onNavigationItemSelected(getMenu(), item);
            }
            return true;
        });
        setOnNavigationItemReselectedListener(item -> {
            if (null != mInnerListener) {
                mInnerListener.onNavigationItemReselected(getMenu(), item);
            }
        });
        bnvHelper = new BNVHelper(this);
    }

    @NonNull
    @Override
    public BottomNavigationView getRealView() {
        return this;
    }

    /**
     * change the visibility of icon
     *
     * @param visibility
     */
    @NonNull
    public BottomNavigationViewV130 setIconVisibility(boolean visibility) {
        /*
        1. get field in this class
        private final BottomNavigationMenuView mMenuView;

        2. get field in mButtons
        private BottomNavigationItemView[] mButtons;

        3. get mIcon in mButtons
        private ImageView mIcon

        4. set mIcon visibility gone

        5. change mItemHeight to only text size in mMenuView
         */
        // 1. get mMenuView
        final BottomNavigationMenuView mMenuView = getBNMenuView();
        // 2. get mButtons
        BottomNavigationItemView[] mButtons = getAllBNItemView();
        // 3. get mIcon in mButtons
        for (BottomNavigationItemView button : mButtons) {
            ImageView mIcon = getField(button.getClass(), button, "icon");
            // 4. set mIcon visibility gone
            if (mIcon != null) {
                mIcon.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
            }
        }

        // 5. change mItemHeight to only text size in mMenuView
        if (!visibility) {
            // if not record mItemHeight
            if (!visibilityHeightRecord) {
                visibilityHeightRecord = true;
                mItemHeight = getBNMenuViewHeight();
            }

            // change mItemHeight
            BottomNavigationItemView button = mButtons[0];
            if (null != button) {
                final ImageView mIcon = getField(button.getClass(), button, "icon");
//                System.out.println("mIcon.getMeasuredHeight():" + mIcon.getMeasuredHeight());
                if (null != mIcon) {
                    mIcon.post(new Runnable() {
                        @Override
                        public void run() {
//                            System.out.println("mIcon.getMeasuredHeight():" + mIcon.getMeasuredHeight());
                            setBNMenuViewHeight(mItemHeight - mIcon.getMeasuredHeight());
                        }
                    });
                }
            }
        } else {
            // if not record the mItemHeight, we need do nothing.
            if (!visibilityHeightRecord)
                return this;

            // restore it
            setBNMenuViewHeight(mItemHeight);
        }

        mMenuView.updateMenuView();
        return this;
    }

    /**
     * change the visibility of text
     *
     * @param visibility
     */
    @NonNull
    public BottomNavigationViewV130 setTextVisibility(boolean visibility) {
        this.textVisibility = visibility;
        /*
        1. get field in this class
        private final BottomNavigationMenuView mMenuView;

        2. get field in mButtons
        private BottomNavigationItemView[] mButtons;

        3. set text size in mButtons
        private final TextView mLargeLabel
        private final TextView mSmallLabel

        4. change mItemHeight to only icon size in mMenuView
         */
        // 1. get mMenuView
        BottomNavigationMenuView mMenuView = getBNMenuView();
        // 2. get mButtons
        BottomNavigationItemView[] mButtons = getAllBNItemView();

        // 3. change field mShiftingMode value in mButtons
        for (BottomNavigationItemView button : mButtons) {
            TextView mLargeLabel = getField(button.getClass(), button, "largeLabel");
            TextView mSmallLabel = getField(button.getClass(), button, "smallLabel");

            if (!visibility) {
                // if not record the font size, record it
                if (!visibilityTextSizeRecord && !animationRecord) {
                    visibilityTextSizeRecord = true;
                    mLargeLabelSize = mLargeLabel.getTextSize();
                    mSmallLabelSize = mSmallLabel.getTextSize();
                }

                // if not visitable, set font size to 0
                mLargeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0);
                mSmallLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0);

            } else {
                // if not record the font size, we need do nothing.
                if (!visibilityTextSizeRecord)
                    break;

                // restore it
                mLargeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLargeLabelSize);
                mSmallLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSmallLabelSize);
            }
        }

        // 4 change mItemHeight to only icon size in mMenuView
        if (!visibility) {
            // if not record mItemHeight
            if (!visibilityHeightRecord) {
                visibilityHeightRecord = true;
                mItemHeight = getBNMenuViewHeight();
            }

            // change mItemHeight to only icon size in mMenuView
            // private final int mItemHeight;

            // change mItemHeight
//            System.out.println("mLargeLabel.getMeasuredHeight():" + getFontHeight(mSmallLabelSize));
            setBNMenuViewHeight(mItemHeight - getFontHeight(mSmallLabelSize));

        } else {
            // if not record the mItemHeight, we need do nothing.
            if (!visibilityHeightRecord)
                return this;
            // restore mItemHeight
            setBNMenuViewHeight(mItemHeight);
        }

        mMenuView.updateMenuView();
        return this;
    }

    /**
     * get text height by font size
     *
     * @param fontSize
     * @return
     */
    private static int getFontHeight(float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.top) + 2;
    }

    /**
     * enable or disable click item animation(text scale and icon move animation in no item shifting mode)
     *
     * @param enable It means the text won't scale and icon won't move when active it in no item shifting mode if false.
     */
    public BottomNavigationViewV130 enableAnimation(boolean enable) {
        /*
        1. get field in this class
        private final BottomNavigationMenuView mMenuView;

        2. get field in mButtons
        private BottomNavigationItemView[] mButtons;

        3. chang mShiftAmount to 0 in mButtons
        private final int mShiftAmount

        change mScaleUpFactor and mScaleDownFactor to 1f in mButtons
        private final float mScaleUpFactor
        private final float mScaleDownFactor

        4. change label font size in mButtons
        private final TextView mLargeLabel
        private final TextView mSmallLabel
         */

        // 1. get mMenuView
        BottomNavigationMenuView mMenuView = getBNMenuView();
        // 2. get mButtons
        BottomNavigationItemView[] mButtons = getAllBNItemView();
        // 3. change field mShiftingMode value in mButtons
        for (BottomNavigationItemView button : mButtons) {
            TextView mLargeLabel = getField(button.getClass(), button, "largeLabel");
            TextView mSmallLabel = getField(button.getClass(), button, "smallLabel");

            // if disable animation, need animationRecord the source value
            if (!enable) {
                if (!animationRecord) {
                    animationRecord = true;
                    mShiftAmount = getField(button.getClass(), button, "shiftAmount");
                    mScaleUpFactor = getField(button.getClass(), button, "scaleUpFactor");
                    mScaleDownFactor = getField(button.getClass(), button, "scaleDownFactor");

                    mLargeLabelSize = mLargeLabel.getTextSize();
                    mSmallLabelSize = mSmallLabel.getTextSize();

//                    System.out.println("mShiftAmount:" + mShiftAmount + " mScaleUpFactor:"
//                            + mScaleUpFactor + " mScaleDownFactor:" + mScaleDownFactor
//                            + " mLargeLabel:" + mLargeLabelSize + " mSmallLabel:" + mSmallLabelSize);
                }
                // disable
                setField(button.getClass(), button, "shiftAmount", 0);
                setField(button.getClass(), button, "scaleUpFactor", 1);
                setField(button.getClass(), button, "scaleDownFactor", 1);

                // let the mLargeLabel font size equal to mSmallLabel
                mLargeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSmallLabelSize);

                // debug start
//                mLargeLabelSize = mLargeLabel.getTextSize();
//                System.out.println("mLargeLabel:" + mLargeLabelSize);
                // debug end

            } else {
                // haven't change the value. It means it was the first call this method. So nothing need to do.
                if (!animationRecord)
                    return this;
                // enable animation
                setField(button.getClass(), button, "shiftAmount", mShiftAmount);
                setField(button.getClass(), button, "scaleUpFactor", mScaleUpFactor);
                setField(button.getClass(), button, "scaleDownFactor", mScaleDownFactor);
                // restore
                mLargeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLargeLabelSize);
            }
        }
        mMenuView.updateMenuView();
        return this;
    }

    /**
     * @param enable It will has a shift animation if true. Otherwise all items are the same width.
     * @Deprecated use {@link #setLabelVisibilityMode }
     * enable the shifting mode for navigation
     */
    @Deprecated
    public BottomNavigationViewV130 enableLabelVisibility(boolean enable) {
        /*
        1. get field in this class
        private final BottomNavigationMenuView mMenuView;

        2. change field mShiftingMode value in mMenuView
        private boolean mShiftingMode = true;
         */
        // 1. get mMenuView
//        BottomNavigationMenuView mMenuView = getBottomNavigationMenuView();
        // 2. change field mShiftingMode value in mMenuView
//        setField(mMenuView.getClass(), mMenuView, "isShifting", enable);
//        mMenuView.updateMenuView();
        setLabelVisibilityMode(enable ? LabelVisibilityMode.LABEL_VISIBILITY_SELECTED : LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        return this;
    }

    /**
     * @param enable It will has a shift animation for item if true. Otherwise the item text always be shown.
     * @Deprecated use {@link #setItemHorizontalTranslationEnabled(boolean)}
     * enable the shifting mode for each item
     */
    @Deprecated
    public BottomNavigationViewV130 enableItemHorizontalTranslation(boolean enable) {
        /*
        1. get field in this class
        private final BottomNavigationMenuView mMenuView;

        2. get field in this mMenuView
        private BottomNavigationItemView[] mButtons;

        3. change field mShiftingMode value in mButtons
        private boolean mShiftingMode = true;
         */
        // 1. get mMenuView
//        BottomNavigationMenuView mMenuView = getBottomNavigationMenuView();
        // 2. get buttons
//        BottomNavigationItemView[] mButtons = getBottomNavigationItemViews();
        // 3. change field mShiftingMode value in mButtons
//        for (BottomNavigationItemView button : mButtons) {
//            button.setShifting(enable);
//        }
//        mMenuView.updateMenuView();

        setItemHorizontalTranslationEnabled(enable);

        return this;
    }

    /**
     * get the current checked item position
     *
     * @return index of item, start from 0.
     */
    public int getCurrentIndex() {
        /*
        1. get field in this class
        private final BottomNavigationMenuView mMenuView;

        2. get field in mMenuView
        private BottomNavigationItemView[] mButtons;

        3. get menu and traverse it to get the checked one
         */

        // 2. get mButtons
        BottomNavigationItemView[] mButtons = getAllBNItemView();
        // 3. get menu and traverse it to get the checked one
        Menu menu = getMenu();
        for (int i = 0; i < mButtons.length; i++) {
            if (menu.getItem(i).isChecked()) {
                return i;
            }
        }
        return 0;
    }

    /**
     * get menu item position in menu
     *
     * @return position if success, -1 otherwise
     */
    public int menuItemPositionAt(@NonNull MenuItem item) {
        // get item id
        int itemId = item.getItemId();
        // get meunu
        Menu menu = getMenu();
        int size = menu.size();
        for (int i = 0; i < size; i++) {
            if (menu.getItem(i).getItemId() == itemId) {
                return i;
            }
        }
        return -1;
    }

    /**
     * set the current checked item
     *
     * @param index start from 0.
     */
    @NonNull
    public BottomNavigationViewV130 setCurrentItem(int index) {
        setSelectedItemId(getMenu().getItem(index).getItemId());
        return this;
    }

    @Nullable
    @Override
    public IMenuListener getMenuListener() {
        return bnvHelper.getListener();
    }

    @NonNull
    @Override
    public BottomNavigationViewV130 setMenuListener(@NonNull IMenuListener listener) {
        bnvHelper.setListener(listener);
        return this;
    }

    @NonNull
    @Override
    public BottomNavigationViewV130 setMenuDoubleClickListener(@NonNull IMenuDoubleClickListener menuDoubleClickListener) {
        bnvHelper.setMenuDoubleClickListener(menuDoubleClickListener);
        return this;
    }

    /**
     * get private mMenuView
     *
     * @return
     */
    @NonNull
    public BottomNavigationMenuView getBNMenuView() {
        if (null == mMenuView)
            mMenuView = getField(BottomNavigationView.class, this, "menuView");
        return Objects.requireNonNull(mMenuView);
    }

    /**
     * The lib has a default icon tint color. You can call this method to clear it if no need.
     * It usually used when you set two image for item.
     *
     * @return
     */
    public BottomNavigationViewV130 clearIconTintColor() {
        getBNMenuView().setIconTintList(null);
        return this;
    }

    /**
     * get private mButtons in mMenuView
     *
     * @return
     */
    @NonNull
    public BottomNavigationItemView[] getAllBNItemView() {
        if (null != mButtons)
            return mButtons;
        /*
         * 1 private final BottomNavigationMenuView mMenuView;
         * 2 private BottomNavigationItemView[] mButtons;
         */
        BottomNavigationMenuView mMenuView = getBNMenuView();
        mButtons = getField(mMenuView.getClass(), mMenuView, "buttons");
        return Objects.requireNonNull(mButtons);
    }

    /**
     * get private mButton in mMenuView at position
     *
     * @param position
     * @return
     */
    public BottomNavigationItemView getBNItemView(int position) {
        return getAllBNItemView()[position];
    }

    /**
     * get icon at position
     *
     * @param position
     * @return
     */
    public ImageView getIconAt(int position) {
        /*
         * 1 private final BottomNavigationMenuView mMenuView;
         * 2 private BottomNavigationItemView[] mButtons;
         * 3 private ImageView mIcon;
         */
        BottomNavigationItemView mButtons = getBNItemView(position);
        ImageView mIcon = getField(BottomNavigationItemView.class, mButtons, "icon");
        return mIcon;
    }

    /**
     * get small label at position
     * Each item has tow label, one is large, another is small.
     *
     * @param position
     * @return
     */
    public TextView getSmallLabelAt(int position) {
        /*
         * 1 private final BottomNavigationMenuView mMenuView;
         * 2 private BottomNavigationItemView[] mButtons;
         * 3 private final TextView mSmallLabel;
         */
        BottomNavigationItemView mButtons = getBNItemView(position);
        TextView mSmallLabel = getField(BottomNavigationItemView.class, mButtons, "smallLabel");
        return mSmallLabel;
    }

    /**
     * get large label at position
     * Each item has tow label, one is large, another is small.
     *
     * @param position
     * @return
     */
    public TextView getLargeLabelAt(int position) {
        /*
         * 1 private final BottomNavigationMenuView mMenuView;
         * 2 private BottomNavigationItemView[] mButtons;
         * 3 private final TextView mLargeLabel;
         */
        BottomNavigationItemView mButtons = getBNItemView(position);
        TextView mLargeLabel = getField(BottomNavigationItemView.class, mButtons, "largeLabel");
        return mLargeLabel;
    }

    /**
     * return item count
     *
     * @return
     */
    public int getBNItemViewCount() {
        BottomNavigationItemView[] bottomNavigationItemViews = getAllBNItemView();
        if (null == bottomNavigationItemViews)
            return 0;
        return bottomNavigationItemViews.length;
    }

    /**
     * set all item small TextView size
     * Each item has tow label, one is large, another is small.
     * Small one will be shown when item state is normal
     * Large one will be shown when item checked.
     *
     * @param sp
     */
    public BottomNavigationViewV130 setSmallTextSize(float sp) {
        int count = getBNItemViewCount();
        for (int i = 0; i < count; i++) {
            getSmallLabelAt(i).setTextSize(sp);
        }
        mMenuView.updateMenuView();
        return this;
    }

    /**
     * set all item large TextView size
     * Each item has tow label, one is large, another is small.
     * Small one will be shown when item state is normal.
     * Large one will be shown when item checked.
     *
     * @param sp
     */
    public BottomNavigationViewV130 setLargeTextSize(float sp) {
        int count = getBNItemViewCount();
        for (int i = 0; i < count; i++) {
            TextView tvLarge = getLargeLabelAt(i);
            if (null != tvLarge)
                tvLarge.setTextSize(sp);
        }
        mMenuView.updateMenuView();
        return this;
    }

    /**
     * set all item large and small TextView size
     * Each item has tow label, one is large, another is small.
     * Small one will be shown when item state is normal
     * Large one will be shown when item checked.
     *
     * @param sp
     */
    public BottomNavigationViewV130 setTextSize(float sp) {
        setLargeTextSize(sp);
        setSmallTextSize(sp);
        return this;
    }

    /**
     * set item ImageView size which at position
     *
     * @param position position start from 0
     * @param width    in dp
     * @param height   in dp
     */
    public BottomNavigationViewV130 setIconSizeAt(int position, float width, float height) {
        ImageView icon = getIconAt(position);
        // update size
        ViewGroup.LayoutParams layoutParams = icon.getLayoutParams();
        layoutParams.width = dp2px(getContext(), width);
        layoutParams.height = dp2px(getContext(), height);
        icon.setLayoutParams(layoutParams);

        mMenuView.updateMenuView();
        return this;
    }

    /**
     * set all item ImageView size
     *
     * @param width  in dp
     * @param height in dp
     */
    public BottomNavigationViewV130 setIconSize(float width, float height) {
        int count = getBNItemViewCount();
        for (int i = 0; i < count; i++) {
            setIconSizeAt(i, width, height);
        }
        return this;
    }

    /**
     * set all item ImageView size
     *
     * @param dpSize in dp
     */
    public BottomNavigationViewV130 setIconSize(float dpSize) {
        setItemIconSize(dp2px(getContext(), dpSize));
        return this;
    }

    /**
     * set menu item height
     *
     * @param height in px
     */
    public BottomNavigationViewV130 setBNMenuViewHeight(int height) {
        // 1. get mMenuView
        final BottomNavigationMenuView mMenuView = getBNMenuView();
        // 2. set private final int mItemHeight in mMenuView
        setField(mMenuView.getClass(), mMenuView, "itemHeight", height);

        mMenuView.updateMenuView();
        return this;
    }

    /**
     * get menu item height
     *
     * @return in px
     */
    public int getBNMenuViewHeight() {
        // 1. get mMenuView
        final BottomNavigationMenuView mMenuView = getBNMenuView();
        // 2. get private final int mItemHeight in mMenuView
        return getField(mMenuView.getClass(), mMenuView, "itemHeight");
    }

    /**
     * dp to px
     *
     * @param context
     * @param dpValue dp
     * @return px
     */
    private int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * set Typeface for all item TextView
     *
     * @attr ref android.R.styleable#TextView_typeface
     * @attr ref android.R.styleable#TextView_textStyle
     */
    public BottomNavigationViewV130 setTypeface(Typeface typeface, int style) {
        int count = getBNItemViewCount();
        for (int i = 0; i < count; i++) {
            getLargeLabelAt(i).setTypeface(typeface, style);
            getSmallLabelAt(i).setTypeface(typeface, style);
        }
        mMenuView.updateMenuView();
        return this;
    }

    /**
     * set Typeface for all item TextView
     *
     * @attr ref android.R.styleable#TextView_typeface
     */
    public BottomNavigationViewV130 setTypeface(Typeface typeface) {
        int count = getBNItemViewCount();
        for (int i = 0; i < count; i++) {
            getLargeLabelAt(i).setTypeface(typeface);
            getSmallLabelAt(i).setTypeface(typeface);
        }
        mMenuView.updateMenuView();
        return this;
    }

    /**
     * get private filed in this specific object
     *
     * @param targetClass
     * @param instance    the filed owner
     * @param fieldName
     * @param <T>
     * @return field if success, null otherwise.
     */
    private <T> T getField(Class targetClass, Object instance, String fieldName) {
        try {
            Field field = targetClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(instance);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * change the field value
     *
     * @param targetClass
     * @param instance    the filed owner
     * @param fieldName
     * @param value
     */
    private void setField(Class targetClass, Object instance, String fieldName, Object value) {
        try {
            Field field = targetClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will link the given ViewPager and this BottomNavigationViewInner together so that
     * changes in one are automatically reflected in the other. This includes scroll state changes
     * and clicks.
     *
     * @param vp
     */
    @NonNull
    public BottomNavigationViewV130 setupWithViewPager(@org.jetbrains.annotations.Nullable final ViewPager vp) {
        return setupWithViewPager(vp, false);
    }

    /**
     * This method will link the given ViewPager and this BottomNavigationViewInner together so that
     * changes in one are automatically reflected in the other. This includes scroll state changes
     * and clicks.
     *  @param vp
     * @param smoothScroll whether ViewPager changed with smooth scroll animation
     */
    @NonNull
    public BottomNavigationViewV130 setupWithViewPager(@org.jetbrains.annotations.Nullable final ViewPager vp, boolean smoothScroll) {
        if (null == vp) {
            return this;
        }
        bnvHelper.setupViewPagerHelper(new VPHelper(vp, smoothScroll));
        return this;
    }

    @NonNull
    @Override
    public BottomNavigationViewV130 setupWithViewPager2(@org.jetbrains.annotations.Nullable ViewPager2 vp2) {
        return setupWithViewPager2(vp2, false);
    }

    @NonNull
    @Override
    public BottomNavigationViewV130 setupWithViewPager2(@org.jetbrains.annotations.Nullable ViewPager2 vp2, boolean smoothScroll) {
        if (null == vp2) {
            return this;
        }
        bnvHelper.setupViewPagerHelper(new VP2Helper(vp2, smoothScroll));
        return this;
    }

    @Override
    public void setInnerListener(@NonNull InnerListener listener) {
        this.mInnerListener = listener;
    }

    public BottomNavigationViewV130 enableBNItemViewLabelVisibility(int position, boolean enable) {
        getBNItemView(position).setShifting(enable);
        return this;
    }

    public BottomNavigationViewV130 setBNItemViewBackgroundRes(int position, int background) {
        getBNItemView(position).setItemBackground(background);
        return this;
    }

    @NonNull
    @Override
    public BottomNavigationViewV130 setIconTintList(@Nullable ColorStateList tint) {
        getBNMenuView().setIconTintList(tint);
        return this;
    }

    public BottomNavigationViewV130 setIconTintList(int position, ColorStateList tint) {
        getBNItemView(position).setIconTintList(tint);
        return this;
    }

    @NonNull
    @Override
    public BottomNavigationViewV130 setTextTintList(@Nullable ColorStateList tint) {
        getBNMenuView().setItemTextColor(tint);
        return this;
    }

    public BottomNavigationViewV130 setTextTintList(int position, ColorStateList tint) {
        getBNItemView(position).setTextColor(tint);
        return this;
    }

    /**
     * set margin top for all icons
     *
     * @param marginTop in px
     */
    public BottomNavigationViewV130 setIconsMarginTop(int marginTop) {
        for (int i = 0; i < getBNItemViewCount(); i++) {
            setIconMarginTop(i, marginTop);
        }
        return this;
    }

    /**
     * set margin top for icon
     *
     * @param position
     * @param marginTop in px
     */
    public BottomNavigationViewV130 setIconMarginTop(int position, int marginTop) {
        /*
        1. BottomNavigationItemView
        2. private final int mDefaultMargin;
         */
        BottomNavigationItemView itemView = getBNItemView(position);
        setField(BottomNavigationItemView.class, itemView, "defaultMargin", marginTop);
        mMenuView.updateMenuView();
        return this;
    }

    @NonNull
    @Override
    public BottomNavigationViewV130 setEmptyMenuIds(@NonNull List<Integer> emptyMenuIds) {
        bnvHelper.setEmptyMenuIds(emptyMenuIds);
        return this;
    }

    @NonNull
    @Override
    public List<MenuItem> getMenuItems() {
        ArrayList<MenuItem> result = new ArrayList<>();
        MenuKt.forEachIndexed(getMenu(), (index, item) -> {
            result.add(index, item);
            return null;
        });
        return CollectionsKt.toList(result);
    }
}
