package com.google.android.material.bottomnavigation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.*;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.MenuKt;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.internal.ThemeEnforcement;
import com.yh.bottomnavigation_base.IBottomNavigationEx;
import com.yh.bottomnavigation_base.IMenuDoubleClickListener;
import com.yh.bottomnavigation_base.IMenuListener;
import com.yh.bottomnavigation_base.ext.ExtContextKt;
import com.yh.bottomnavigation_base.helper.BNVHelper;
import com.yh.bottomnavigation_base.helper.VP2Helper;
import com.yh.bottomnavigation_base.helper.VPHelper;
import com.yh.bottomnavigation_base.internal.InnerListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;

import static com.yh.bottomnavigation_base.ext.ExtReflectKt.*;

/**
 * Last modify by CListery on 2024/8/13.
 */
@SuppressLint("RestrictedApi")
public class BottomNavigationViewV13x extends BottomNavigationView implements IBottomNavigationEx<BottomNavigationView, BottomNavigationMenuView, BottomNavigationItemView> {
    // used for animation
    private Float mShiftAmount;
    private Float mScaleUpFactor;
    private Float mScaleDownFactor;
    private boolean animationRecord;
    private Float mLargeLabelSize;
    private Float mSmallLabelSize;
    private boolean visibilityTextSizeRecord;
    private boolean visibilityHeightRecord;
    private Integer mItemHeight;
    // used for animation end

    // used for setupWithViewPager
    private BottomNavigationMenuView mMenuView;
    private BottomNavigationItemView[] mButtons;
    // used for setupWithViewPager end

    private InnerListener mInnerListener;
    private final BNVHelper bnvHelper;

    public BottomNavigationViewV13x(Context context) {
        this(context, null);
    }

    public BottomNavigationViewV13x(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigationViewV13x(Context context, AttributeSet attrs, int defStyleAttr) {
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
     */
    @NonNull
    public BottomNavigationViewV13x setIconVisibility(boolean visibility) {
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
            ImageView mIcon = getFieldValue(button, "icon");
            // 4. set mIcon visibility gone
            mIcon.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
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
                final ImageView mIcon = getFieldValue(button, "icon");
//                System.out.println("mIcon.getMeasuredHeight():" + mIcon.getMeasuredHeight());
                mIcon.post(() -> {
//                            System.out.println("mIcon.getMeasuredHeight():" + mIcon.getMeasuredHeight());
                    setBNMenuViewHeight(mItemHeight - mIcon.getMeasuredHeight());
                });
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
     */
    @NonNull
    public BottomNavigationViewV13x setTextVisibility(boolean visibility) {
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
            TextView mLargeLabel = getFieldValue(button, "largeLabel");
            TextView mSmallLabel = getFieldValue(button, "smallLabel");

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
    @NonNull
    public BottomNavigationViewV13x enableAnimation(boolean enable) {
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
            TextView mLargeLabel = getFieldValue(button, "largeLabel");
            TextView mSmallLabel = getFieldValue(button, "smallLabel");

            // if disable animation, need animationRecord the source value
            if (!enable) {
                if (!animationRecord) {
                    animationRecord = true;
                    mShiftAmount = getFieldValue(button, "shiftAmount");
                    mScaleUpFactor = getFieldValue(button, "scaleUpFactor");
                    mScaleDownFactor = getFieldValue(button, "scaleDownFactor");

                    mLargeLabelSize = mLargeLabel.getTextSize();
                    mSmallLabelSize = mSmallLabel.getTextSize();

//                    System.out.println("mShiftAmount:" + mShiftAmount + " mScaleUpFactor:"
//                            + mScaleUpFactor + " mScaleDownFactor:" + mScaleDownFactor
//                            + " mLargeLabel:" + mLargeLabelSize + " mSmallLabel:" + mSmallLabelSize);
                }
                // disable
                setFieldValue(button, "shiftAmount", 0);
                setFieldValue(button, "scaleUpFactor", 1);
                setFieldValue(button, "scaleDownFactor", 1);

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
                setFieldValue(button, "shiftAmount", mShiftAmount);
                setFieldValue(button, "scaleUpFactor", mScaleUpFactor);
                setFieldValue(button, "scaleDownFactor", mScaleDownFactor);
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
    @NonNull
    @Deprecated
    public BottomNavigationViewV13x enableLabelVisibility(boolean enable) {
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
    @NonNull
    @Deprecated
    public BottomNavigationViewV13x enableItemHorizontalTranslation(boolean enable) {
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
    public BottomNavigationViewV13x setCurrentItem(int index) {
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
    public BottomNavigationViewV13x setMenuListener(@NonNull IMenuListener listener) {
        bnvHelper.setListener(listener);
        return this;
    }

    @NonNull
    @Override
    public BottomNavigationViewV13x setMenuDoubleClickListener(@NonNull IMenuDoubleClickListener menuDoubleClickListener) {
        bnvHelper.setMenuDoubleClickListener(menuDoubleClickListener);
        return this;
    }

    /**
     * get private mMenuView
     */
    @NonNull
    public BottomNavigationMenuView getBNMenuView() {
        if (null == mMenuView)
            mMenuView = getFieldValue(this, "menuView");
        return Objects.requireNonNull(mMenuView);
    }

    /**
     * The lib has a default icon tint color. You can call this method to clear it if no need.
     * It usually used when you set two image for item.
     */
    @NonNull
    public BottomNavigationViewV13x clearIconTintColor() {
        getBNMenuView().setIconTintList(null);
        return this;
    }

    /**
     * get private mButtons in mMenuView
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
        mButtons = safeGetFieldValue(mMenuView, "buttons");
        return mButtons != null
               ? mButtons
               : new BottomNavigationItemView[0];
    }

    /**
     * get private mButton in mMenuView at position
     */
    public BottomNavigationItemView getBNItemView(int position) {
        return getAllBNItemView()[position];
    }

    /**
     * get icon at position
     */
    public ImageView getIconAt(int position) {
        /*
         * 1 private final BottomNavigationMenuView mMenuView;
         * 2 private BottomNavigationItemView[] mButtons;
         * 3 private ImageView mIcon;
         */
        BottomNavigationItemView button = getBNItemView(position);
        if(null == button){
            return null;
        }
        return getFieldValue(button, "icon");
    }

    /**
     * get small label at position
     * Each item has tow label, one is large, another is small.
     */
    public TextView getSmallLabelAt(int position) {
        /*
         * 1 private final BottomNavigationMenuView mMenuView;
         * 2 private BottomNavigationItemView[] mButtons;
         * 3 private final TextView mSmallLabel;
         */
        BottomNavigationItemView button = getBNItemView(position);
        if(null == button){
            return null;
        }
        return getFieldValue(button, "smallLabel");
    }

    /**
     * get large label at position
     * Each item has tow label, one is large, another is small.
     */
    public TextView getLargeLabelAt(int position) {
        /*
         * 1 private final BottomNavigationMenuView mMenuView;
         * 2 private BottomNavigationItemView[] mButtons;
         * 3 private final TextView mLargeLabel;
         */
        BottomNavigationItemView button = getBNItemView(position);
        if(null == button){
            return null;
        }
        return getFieldValue(button, "largeLabel");
    }

    /**
     * return item count
     */
    public int getBNItemViewCount() {
        return getAllBNItemView().length;
    }

    /**
     * set all item small TextView size
     * Each item has tow label, one is large, another is small.
     * Small one will be shown when item state is normal
     * Large one will be shown when item checked.
     */
    @NonNull
    public BottomNavigationViewV13x setSmallTextSize(float sp) {
        int count = getBNItemViewCount();
        for (int i = 0; i < count; i++) {
            Objects.requireNonNull(getSmallLabelAt(i)).setTextSize(sp);
        }
        mMenuView.updateMenuView();
        return this;
    }

    /**
     * set all item large TextView size
     * Each item has tow label, one is large, another is small.
     * Small one will be shown when item state is normal.
     * Large one will be shown when item checked.
     */
    @NonNull
    public BottomNavigationViewV13x setLargeTextSize(float sp) {
        int count = getBNItemViewCount();
        for (int i = 0; i < count; i++) {
            Objects.requireNonNull(getLargeLabelAt(i)).setTextSize(sp);
        }
        mMenuView.updateMenuView();
        return this;
    }

    /**
     * set all item large and small TextView size
     * Each item has tow label, one is large, another is small.
     * Small one will be shown when item state is normal
     * Large one will be shown when item checked.
     */
    @NonNull
    public BottomNavigationViewV13x setTextSize(float sp) {
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
    @NonNull
    public BottomNavigationViewV13x setIconSizeAt(int position, float width, float height) {
        ImageView icon = getIconAt(position);
        if (null == icon) {
            return this;
        }
        // update size
        ViewGroup.LayoutParams layoutParams = icon.getLayoutParams();
        layoutParams.width = ExtContextKt.dp2px(getContext(), width);
        layoutParams.height = ExtContextKt.dp2px(getContext(), height);
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
    @NonNull
    public BottomNavigationViewV13x setIconSize(float width, float height) {
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
    @NonNull
    public BottomNavigationViewV13x setIconSize(float dpSize) {
        setItemIconSize(ExtContextKt.dp2px(getContext(), dpSize));
        return this;
    }

    /**
     * set menu item height
     *
     * @param height in px
     */
    @NonNull
    public BottomNavigationViewV13x setBNMenuViewHeight(int height) {
        // 1. get mMenuView
        final BottomNavigationMenuView menuView = getBNMenuView();
        // 2. set private final int mItemHeight in mMenuView
        setFieldValue(menuView, "itemHeight", height);

        menuView.updateMenuView();
        return this;
    }

    /**
     * get menu item height
     *
     * @return in px
     */
    public int getBNMenuViewHeight() {
        // 1. get mMenuView
        final BottomNavigationMenuView menuView = getBNMenuView();
        // 2. get private final int mItemHeight in mMenuView
        return getFieldValue(menuView, "itemHeight");
    }

    /**
     * set Typeface for all item TextView
     *
     * @param typeface ref android.R.styleable#TextView_typeface
     * @param style ref android.R.styleable#TextView_textStyle
     */
    @NonNull
    public BottomNavigationViewV13x setTypeface(@NonNull Typeface typeface, int style) {
        int count = getBNItemViewCount();
        for (int i = 0; i < count; i++) {
            Objects.requireNonNull(getLargeLabelAt(i)).setTypeface(typeface, style);
            Objects.requireNonNull(getSmallLabelAt(i)).setTypeface(typeface, style);
        }
        mMenuView.updateMenuView();
        return this;
    }

    /**
     * set Typeface for all item TextView
     *
     * @param typeface ref android.R.styleable#TextView_typeface
     */
    @NonNull
    public BottomNavigationViewV13x setTypeface(@NonNull Typeface typeface) {
        int count = getBNItemViewCount();
        for (int i = 0; i < count; i++) {
            Objects.requireNonNull(getLargeLabelAt(i)).setTypeface(typeface);
            Objects.requireNonNull(getSmallLabelAt(i)).setTypeface(typeface);
        }
        mMenuView.updateMenuView();
        return this;
    }

    /**
     * This method will link the given ViewPager and this BottomNavigationViewInner together so that
     * changes in one are automatically reflected in the other. This includes scroll state changes
     * and clicks.
     *
     * @param vp ViewPager
     */
    @NonNull
    public BottomNavigationViewV13x setupWithViewPager(@org.jetbrains.annotations.Nullable final ViewPager vp) {
        return setupWithViewPager(vp, false);
    }

    /**
     * This method will link the given ViewPager and this BottomNavigationViewInner together so that
     * changes in one are automatically reflected in the other. This includes scroll state changes
     * and clicks.
     *  @param vp ViewPager
     * @param smoothScroll whether ViewPager changed with smooth scroll animation
     */
    @NonNull
    public BottomNavigationViewV13x setupWithViewPager(@org.jetbrains.annotations.Nullable final ViewPager vp, boolean smoothScroll) {
        if (null == vp) {
            return this;
        }
        bnvHelper.setupViewPagerHelper(new VPHelper(vp, smoothScroll));
        return this;
    }

    @NonNull
    @Override
    public BottomNavigationViewV13x setupWithViewPager2(@org.jetbrains.annotations.Nullable ViewPager2 vp2) {
        return setupWithViewPager2(vp2, false);
    }

    @NonNull
    @Override
    public BottomNavigationViewV13x setupWithViewPager2(@org.jetbrains.annotations.Nullable ViewPager2 vp2, boolean smoothScroll) {
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

    @NonNull
    public BottomNavigationViewV13x enableBNItemViewLabelVisibility(int position, boolean enable) {
        BottomNavigationItemView button = getBNItemView(position);
        if(null == button){
            return this;
        }
        button.setShifting(enable);
        return this;
    }

    @NonNull
    public BottomNavigationViewV13x setBNItemViewBackgroundRes(int position, int background) {
        BottomNavigationItemView button = getBNItemView(position);
        if(null == button){
            return this;
        }
        button.setItemBackground(background);
        return this;
    }

    @NonNull
    @Override
    public BottomNavigationViewV13x setIconTintList(@Nullable ColorStateList tint) {
        getBNMenuView().setIconTintList(tint);
        return this;
    }

    @NonNull
    public BottomNavigationViewV13x setIconTintList(int position, ColorStateList tint) {
        BottomNavigationItemView button = getBNItemView(position);
        if(null == button){
            return this;
        }
        button.setIconTintList(tint);
        return this;
    }

    @NonNull
    @Override
    public BottomNavigationViewV13x setTextTintList(@Nullable ColorStateList tint) {
        getBNMenuView().setItemTextColor(tint);
        return this;
    }

    @NonNull
    public BottomNavigationViewV13x setTextTintList(int position, ColorStateList tint) {
        BottomNavigationItemView button = getBNItemView(position);
        if(null == button){
            return this;
        }
        button.setTextColor(tint);
        return this;
    }

    /**
     * set margin top for all icons
     *
     * @param marginTop in px
     */
    @NonNull
    public BottomNavigationViewV13x setIconsMarginTop(int marginTop) {
        for (int i = 0; i < getBNItemViewCount(); i++) {
            setIconMarginTop(i, marginTop);
        }
        return this;
    }

    /**
     * set margin top for icon
     *
     * @param position position
     * @param marginTop in px
     */
    @NonNull
    public BottomNavigationViewV13x setIconMarginTop(int position, int marginTop) {
        /*
        1. BottomNavigationItemView
        2. private final int mDefaultMargin;
         */
        BottomNavigationItemView itemView = getBNItemView(position);
        setFieldValue(itemView, "defaultMargin", marginTop);
        mMenuView.updateMenuView();
        return this;
    }

    @NonNull
    @Override
    public BottomNavigationViewV13x setEmptyMenuIds(@NonNull List<Integer> emptyMenuIds) {
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

    @Override
    public void restoreInstanceState(@Nullable Parcelable state) {
        onRestoreInstanceState(state);
    }

    @Nullable
    @Override
    public Parcelable saveInstanceState() {
        return onSaveInstanceState();
    }

    @Override
    public void setItemOnTouchListener(@NonNull MenuItem menuItem, @NonNull OnTouchListener onTouchListener) {
        super.setItemOnTouchListener(menuItem.getItemId(), onTouchListener);
    }
    
    @NotNull
    @Override
    public BottomNavigationViewV13x configDynamic(int count, @NotNull Function2<? super Menu, ? super Integer, ? extends MenuItem> generator){
        if(count > 0){
            BottomNavigationPresenter presenter = getFieldValue(this, "presenter");
            MenuBuilder menu = configMenu(presenter);
            presenter.setUpdateSuspended(false);
            menu.clearAll();
            for(int i = 1; i <= count; i++){
                generator.invoke(menu, i);
            }
            menu.addMenuPresenter(presenter);
            presenter.setUpdateSuspended(false);
            presenter.updateMenuView(true);
            mButtons = safeGetFieldValue(getBNMenuView(), "buttons");
        }
        return this;
    }
    
    private MenuBuilder configMenu(BottomNavigationPresenter presenter){
        MenuBuilder originMenu = (MenuBuilder) this.getMenu();
        if(originMenu instanceof BottomNavigationMenu2){
            return originMenu;
        }
        
        BottomNavigationMenuView originMenuView = this.getBNMenuView();
        
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.START;
        
        BottomNavigationMenuView2 newMenuView = new BottomNavigationMenuView2(getContext(), originMenuView);
        
        newMenuView.setLayoutParams(params);
        
        presenter.setBottomNavigationMenuView(newMenuView);
        
        BottomNavigationMenu2 newMenu = new BottomNavigationMenu2(getContext(), originMenu);
        
        mMenuView = newMenuView;
        
        presenter.initForMenu(getContext(), newMenu);
        
        OnNavigationItemSelectedListener selectedListener = safeGetFieldValue(this, "selectedListener");
        OnNavigationItemReselectedListener reselectedListener = safeGetFieldValue(this, "reselectedListener");
        
        removeView(originMenuView);
        addView(newMenuView, params);
        
        newMenu.setCallback(new MenuBuilder.Callback(){
            @Override
            public boolean onMenuItemSelected(@NonNull @NotNull MenuBuilder menu, @NonNull @NotNull MenuItem item){
                if(reselectedListener != null && item.getItemId() == getSelectedItemId()){
                    reselectedListener.onNavigationItemReselected(item);
                    return true; // item is already selected
                }
                return selectedListener != null && !selectedListener.onNavigationItemSelected(item);
            }
            
            @Override
            public void onMenuModeChange(@NonNull @NotNull MenuBuilder menu){
            
            }
        });
        
        setFieldValue(this, "menu", newMenu);
        return newMenu;
    }
    
    @Override
    public int getMenuMaxItemCount(){
        return Math.max(getBNItemViewCount(), BottomNavigationMenu.MAX_ITEM_COUNT);
    }
}
