package com.yh.bottomnavigationex.demo.features.centerfab;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.yh.bottomnavigation_base.IMenuDoubleClickListener;
import com.yh.bottomnavigationex.demo.R;
import com.yh.bottomnavigationex.demo.common.base.BaseFragment;
import com.yh.bottomnavigationex.demo.databinding.ActivityCenterFabBinding;

import java.util.ArrayList;
import java.util.List;

public class CenterFabActivity extends AppCompatActivity {
    private static final String TAG = CenterFabActivity.class.getSimpleName();
    private ActivityCenterFabBinding bind;
    private VpAdapter adapter;

    // collections
    private List<Fragment> fragments;// used for ViewPager adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_with_view_pager);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_center_fab);

        initData();
        initView();
        initEvent();
    }


    /**
     * create fragments
     */
    private void initData() {
        fragments = new ArrayList<>(4);

        // create music fragment and add it
        BaseFragment musicFragment = new BaseFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", getString(R.string.music));
        musicFragment.setArguments(bundle);

        // create backup fragment and add it
        BaseFragment backupFragment = new BaseFragment();
        bundle = new Bundle();
        bundle.putString("title", getString(R.string.backup));
        backupFragment.setArguments(bundle);

        // create friends fragment and add it
        BaseFragment favorFragment = new BaseFragment();
        bundle = new Bundle();
        bundle.putString("title", getString(R.string.favor));
        favorFragment.setArguments(bundle);

        // create friends fragment and add it
        BaseFragment visibilityFragment = new BaseFragment();
        bundle = new Bundle();
        bundle.putString("title", getString(R.string.visibility));
        visibilityFragment.setArguments(bundle);


        // add to fragments for adapter
        fragments.add(musicFragment);
        fragments.add(backupFragment);
        fragments.add(favorFragment);
        fragments.add(visibilityFragment);
    }


    /**
     * change BottomNavigationViewEx style
     */
    private void initView() {
        bind.bnve.enableItemHorizontalTranslation(false);
        bind.bnve.enableLabelVisibility(false);
        bind.bnve.enableAnimation(false);

        // set adapter
        adapter = new VpAdapter(getSupportFragmentManager(), fragments);
        bind.vp.setAdapter(adapter);
        bind.bnve.setEmptyMenuIds(new ArrayList<Integer>() {
            {
                add(R.id.i_empty);
            }
        });
        bind.bnve.setMenuDoubleClickListener(new IMenuDoubleClickListener() {
            @Override
            public void onDoubleClick(int position, @NonNull MenuItem menu) {
                Toast.makeText(CenterFabActivity.this, "double click: " + position + " " + menu.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * set listeners
     */
    private void initEvent() {
        bind.bnve.setupWithViewPager(bind.vp);

        // center item click listener
        bind.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CenterFabActivity.this, "Center", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * view pager adapter
     */
    private static class VpAdapter extends FragmentPagerAdapter {
        private List<Fragment> data;

        public VpAdapter(FragmentManager fm, List<Fragment> data) {
            super(fm);
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Fragment getItem(int position) {
            return data.get(position);
        }
    }

}
