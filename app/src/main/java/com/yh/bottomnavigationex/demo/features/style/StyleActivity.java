package com.yh.bottomnavigationex.demo.features.style;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yh.bottomnavigation_base.AbsMenuListener;
import com.yh.bottomnavigation_base.IMenuDoubleClickListener;
import com.yh.bottomnavigation_base.IMenuListener;
import com.yh.bottomnavigationex.BottomNavigationViewEx;
import com.yh.bottomnavigationex.demo.R;
import com.yh.bottomnavigationex.demo.common.base.BaseApplication;
import com.yh.bottomnavigationex.demo.databinding.ActivityStyleBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StyleActivity extends AppCompatActivity {
    private ActivityStyleBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_style);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_style);

        init();
    }

    private void init() {
        bind.bnveNoAnimation.enableAnimation(false);

        bind.bnveNoShiftingMode.enableLabelVisibility(false);

        bind.bnveNoItemShiftingMode.enableItemHorizontalTranslation(true);

        bind.bnveNoText.setTextVisibility(false);

        bind.bnveNoIcon.setIconVisibility(false);

        bind.bnveNoAnimationShiftingMode.enableAnimation(false);
        bind.bnveNoAnimationShiftingMode.enableLabelVisibility(false);

        bind.bnveNoAnimationItemShiftingMode.enableItemHorizontalTranslation(true);
        bind.bnveNoAnimationItemShiftingMode.enableAnimation(false);

        disableAllAnimation(bind.bnveNoAnimationShiftingModeItemShiftingMode);

        bind.bnveNoShiftingModeItemShiftingModeText.enableLabelVisibility(false);
        bind.bnveNoShiftingModeItemShiftingModeText.enableItemHorizontalTranslation(false);
        bind.bnveNoShiftingModeItemShiftingModeText.setTextVisibility(false);


        disableAllAnimation(bind.bnveNoAnimationShiftingModeItemShiftingModeText);
        bind.bnveNoAnimationShiftingModeItemShiftingModeText.setTextVisibility(false);

        bind.bnveNoShiftingModeItemShiftingModeAndIcon.enableLabelVisibility(false);
        bind.bnveNoShiftingModeItemShiftingModeAndIcon.enableItemHorizontalTranslation(false);
        bind.bnveNoShiftingModeItemShiftingModeAndIcon.setIconVisibility(false);

        bind.bnveNoItemShiftingModeIcon.enableItemHorizontalTranslation(true);
        bind.bnveNoItemShiftingModeIcon.setIconVisibility(false);

        disableAllAnimation(bind.bnveNoAnimationShiftingModeItemShiftingModeIcon);
        bind.bnveNoAnimationShiftingModeItemShiftingModeIcon.setIconVisibility(false);

        disableAllAnimation(bind.bnveWithPadding);
        bind.bnveWithPadding.setIconVisibility(false);

        initCenterIconOnly();

        initSmallerText();

        initBiggerIcon();

        initCustomTypeface();

        bind.bnveIconSelector.enableAnimation(false);
        bind.bnveIconSelector.setIconTintList(null);

        initMargin();

        initUncheckedFirstTime();
    }

    private void disableAllAnimation(BottomNavigationViewEx bnve) {
        bnve.enableAnimation(false);
        bnve.enableLabelVisibility(false);
        bnve.enableItemHorizontalTranslation(false);
    }

    private void initCenterIconOnly() {
        disableAllAnimation(bind.bnveCenterIconOnly);
        int centerPosition = 2;
        // attention: you must ensure the center menu item title is empty
        // make icon bigger at centerPosition
        bind.bnveCenterIconOnly.setIconSizeAt(centerPosition, 48, 48);
        bind.bnveCenterIconOnly.setBNItemViewBackgroundRes(centerPosition, R.color.colorGreen);
        bind.bnveCenterIconOnly.setIconTintList(centerPosition, getResources().getColorStateList(R.color.selector_item_gray_color));
        bind.bnveCenterIconOnly.setIconMarginTop(centerPosition, BaseApplication.dp2px(this, 4));
        List<Integer> emptyIds = new ArrayList<>();
        emptyIds.add(R.id.menu_add);
        bind.bnveCenterIconOnly.setEmptyMenuIds(emptyIds);
        // you could set a listener for bnve. and return false when click the center item so that it won't be checked.
        bind.bnveCenterIconOnly.setMenuListener(new AbsMenuListener() {
            @Override
            public void onEmptyItemClick(int position, @NonNull MenuItem menu) {
                if (menu.getItemId() == R.id.menu_add) {
                    Toast.makeText(StyleActivity.this, "add", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bind.bnveCenterIconOnly.setMenuDoubleClickListener(new IMenuDoubleClickListener() {
            @Override
            public void onDoubleClick(int position, @NonNull MenuItem menu) {
                Toast.makeText(StyleActivity.this, "double click: " + position + " " + menu.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSmallerText() {
        disableAllAnimation(bind.bnveSmallerText);
        // set text size
        bind.bnveSmallerText.setTextSize(8);
    }

    private void initBiggerIcon() {
        disableAllAnimation(bind.bnveBiggerIcon);
        // hide text
        bind.bnveBiggerIcon.setTextVisibility(false);
        // set icon size
        int iconSize = 36;
        bind.bnveBiggerIcon.setIconSize(iconSize, iconSize);
        // set item height
        bind.bnveBiggerIcon.setBNMenuViewHeight(BaseApplication.dp2px(this, iconSize + 16));
    }

    private void initCustomTypeface() {
        disableAllAnimation(bind.bnveCustomTypeface);
        // set typeface : bold
        bind.bnveCustomTypeface.setTypeface(Typeface.DEFAULT_BOLD);
        // you also could set typeface from file.
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/custom.ttf");
//        bind.bnveCustomTypeface.setTypeface(typeface);
    }

    private void initMargin() {
        disableAllAnimation(bind.bnveIconMarginTop);
        bind.bnveIconMarginTop.setTextVisibility(false);
        bind.bnveIconMarginTop.setBNMenuViewHeight(BaseApplication.dp2px(this, 56));
        bind.bnveIconMarginTop.setIconsMarginTop(BaseApplication.dp2px(this, 16));
//        bind.bnveIconMarginTop.setIconTintList(0, getResources()
//                .getColorStateList(R.color.colorGray));

    }

    /**
     * There is no idea to set no check item first time.
     * But we can let user think it is unchecked first time by control the color
     */
    private void initUncheckedFirstTime() {
        disableAllAnimation(bind.bnveUncheckedFirstTime);
        // use the unchecked color for first item
        bind.bnveUncheckedFirstTime.setIconTintList(0, getResources()
                .getColorStateList(R.color.colorGray));
        bind.bnveUncheckedFirstTime.setTextTintList(0, getResources()
                .getColorStateList(R.color.colorGray));
        bind.bnveUncheckedFirstTime.setMenuListener(new IMenuListener() {
            private boolean firstClick = true;

            @Override
            public boolean onNavigationItemSelected(int position, @NotNull MenuItem menu, boolean isReSelected) {
                // restore the color when click
                if (firstClick) {
                    if (0 == position) {
                        firstClick = false;
                        bind.bnveUncheckedFirstTime.setIconTintList(0, getResources()
                                .getColorStateList(R.color.selector_item_primary_color));
                        bind.bnveUncheckedFirstTime.setTextTintList(0, getResources()
                                .getColorStateList(R.color.selector_item_primary_color));
                    }
                }
                // do other
                return true;
            }
        });
    }

}
