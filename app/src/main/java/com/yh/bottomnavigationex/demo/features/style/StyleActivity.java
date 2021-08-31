package com.yh.bottomnavigationex.demo.features.style;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yh.bottomnavigation_base.IListener;
import com.yh.bottomnavigationex.BottomNavigationViewEx;
import com.yh.bottomnavigationex.demo.R;
import com.yh.bottomnavigationex.demo.common.base.BaseApplication;
import com.yh.bottomnavigationex.demo.databinding.ActivityStyleBinding;

import org.jetbrains.annotations.NotNull;

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

        bind.bnveNoShiftingMode.enableShiftingMode(false);

        bind.bnveNoItemShiftingMode.enableItemShiftingMode(true);

        bind.bnveNoText.setTextVisibility(false);

        bind.bnveNoIcon.setIconVisibility(false);

        bind.bnveNoAnimationShiftingMode.enableAnimation(false);
        bind.bnveNoAnimationShiftingMode.enableShiftingMode(false);

        bind.bnveNoAnimationItemShiftingMode.enableItemShiftingMode(true);
        bind.bnveNoAnimationItemShiftingMode.enableAnimation(false);

        disableAllAnimation(bind.bnveNoAnimationShiftingModeItemShiftingMode);

        bind.bnveNoShiftingModeItemShiftingModeText.enableShiftingMode(false);
        bind.bnveNoShiftingModeItemShiftingModeText.enableItemShiftingMode(false);
        bind.bnveNoShiftingModeItemShiftingModeText.setTextVisibility(false);


        disableAllAnimation(bind.bnveNoAnimationShiftingModeItemShiftingModeText);
        bind.bnveNoAnimationShiftingModeItemShiftingModeText.setTextVisibility(false);

        bind.bnveNoShiftingModeItemShiftingModeAndIcon.enableShiftingMode(false);
        bind.bnveNoShiftingModeItemShiftingModeAndIcon.enableItemShiftingMode(false);
        bind.bnveNoShiftingModeItemShiftingModeAndIcon.setIconVisibility(false);

        bind.bnveNoItemShiftingModeIcon.enableItemShiftingMode(true);
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
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);
    }

    private void initCenterIconOnly() {
        disableAllAnimation(bind.bnveCenterIconOnly);
        int centerPosition = 2;
        // attention: you must ensure the center menu item title is empty
        // make icon bigger at centerPosition
        bind.bnveCenterIconOnly.setIconSizeAt(centerPosition, 48, 48);
        bind.bnveCenterIconOnly.setItemBackground(centerPosition, R.color.colorGreen);
        bind.bnveCenterIconOnly.setIconTintList(centerPosition, getResources().getColorStateList(R.color.selector_item_gray_color));
        bind.bnveCenterIconOnly.setIconMarginTop(centerPosition, BaseApplication.dp2px(this, 4));
        // you could set a listener for bnve. and return false when click the center item so that it won't be checked.
        bind.bnveCenterIconOnly.setListener(new IListener() {
            @Override
            public boolean onNavigationItemSelected(int position, @NotNull MenuItem menu, boolean isReSelected) {
                if (menu.getItemId() == R.id.menu_add) {
                    Toast.makeText(StyleActivity.this, "add", Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
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
        bind.bnveBiggerIcon.setItemHeight(BaseApplication.dp2px(this, iconSize + 16));
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
        bind.bnveIconMarginTop.setItemHeight(BaseApplication.dp2px(this, 56));
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
        bind.bnveUncheckedFirstTime.setListener(new IListener() {
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
