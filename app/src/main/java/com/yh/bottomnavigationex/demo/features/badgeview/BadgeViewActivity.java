package com.yh.bottomnavigationex.demo.features.badgeview;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.badge.BadgeDrawable;
import com.yh.bottomnavigationex.demo.R;
import com.yh.bottomnavigationex.demo.databinding.ActivityBadgeViewBinding;

public class BadgeViewActivity extends AppCompatActivity {
    private ActivityBadgeViewBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_badge_view);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_badge_view);

        initView();
    }

    private void initView() {
        // disable all animations
        bind.bnve.enableAnimation(false);
        bind.bnve.enableLabelVisibility(false);
        bind.bnve.enableItemHorizontalTranslation(false);

        BadgeDrawable bd = bind.bnve.getRealView().getOrCreateBadge(R.id.i_friends);
        bd.setNumber(1);
        bd.setHorizontalOffset(12);
        bd.setVerticalOffset(2);
    }

}
