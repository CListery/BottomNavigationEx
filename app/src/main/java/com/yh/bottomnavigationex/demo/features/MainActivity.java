package com.yh.bottomnavigationex.demo.features;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.yh.bottomnavigationex.demo.R;
import com.yh.bottomnavigationex.demo.databinding.ActivityMainBinding;
import com.yh.bottomnavigationex.demo.features.badgeview.BadgeViewActivity;
import com.yh.bottomnavigationex.demo.features.centerfab.CenterFabActivity;
import com.yh.bottomnavigationex.demo.features.example.ExampleAct;
import com.yh.bottomnavigationex.demo.features.setupwithviewpager.SetupWithViewPagerActivity;
import com.yh.bottomnavigationex.demo.features.style.StyleActivity;
import com.yh.bottomnavigationex.demo.features.viewpager.WithViewPager2Act;
import com.yh.bottomnavigationex.demo.features.viewpager.WithViewPagerActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();
    }

    private void init() {
        bind.btnExample.setOnClickListener(this);
        bind.btnStyle.setOnClickListener(this);
        bind.btnWithViewPager.setOnClickListener(this);
        bind.btnSetupWithViewPager.setOnClickListener(this);
        bind.btnSetupWithViewPager2.setOnClickListener(this);
        bind.btnBadgeView.setOnClickListener(this);
        bind.btnCenterFab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_example:
                startActivity(new Intent(this, ExampleAct.class));
                break;
            case R.id.btn_style:
                startActivity(new Intent(this, StyleActivity.class));
                break;
            case R.id.btn_with_view_pager:
                startActivity(new Intent(this, WithViewPagerActivity.class));
                break;
            case R.id.btn_setup_with_view_pager:
                startActivity(new Intent(this, SetupWithViewPagerActivity.class));
                break;
            case R.id.btn_setup_with_view_pager2:
                startActivity(new Intent(this, WithViewPager2Act.class));
                break;
            case R.id.btn_badge_view:
                startActivity(new Intent(this, BadgeViewActivity.class));
                break;
            case R.id.btn_center_fab:
                startActivity(new Intent(this, CenterFabActivity.class));
                break;

        }
    }
}
