package com.yh.bottomnavigationex.demo.features.viewpager;

import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.MenuItem;
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
import com.yh.bottomnavigationex.demo.databinding.ActivityWithViewPagerBinding;

import java.util.ArrayList;
import java.util.List;

public class WithViewPagerActivity extends AppCompatActivity {
    private static final String TAG = WithViewPagerActivity.class.getSimpleName();
    private ActivityWithViewPagerBinding bind;
    private VpAdapter adapter;

    // collections
    private SparseIntArray items;// used for change ViewPager selected item
    private List<Fragment> fragments;// used for ViewPager adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_with_view_pager);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_with_view_pager);

        initView();
        initData();
        initEvent();
    }

    /**
     * change BottomNavigationViewEx style
     */
    private void initView() {
        bind.bnve.enableItemHorizontalTranslation(true);
        bind.bnve.enableAnimation(false);
    }

    /**
     * create fragments
     */
    private void initData() {
        fragments = new ArrayList<>(3);
        items = new SparseIntArray(3);

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
        BaseFragment friendsFragment = new BaseFragment();
        bundle = new Bundle();
        bundle.putString("title", getString(R.string.friends));
        friendsFragment.setArguments(bundle);

        // add to fragments for adapter
        fragments.add(musicFragment);
        fragments.add(backupFragment);
        fragments.add(friendsFragment);

        // add to items for change ViewPager item
        items.put(R.id.i_music, 0);
        items.put(R.id.i_backup, 1);
        items.put(R.id.i_friends, 2);

        // set adapter
        adapter = new VpAdapter(getSupportFragmentManager(), fragments);
        bind.vp.setAdapter(adapter);
    }

    /**
     * set listeners
     */
    private void initEvent() {
        bind.bnve.setupWithViewPager(bind.vp);
        bind.bnve.setMenuDoubleClickListener(new IMenuDoubleClickListener() {
            @Override
            public void onDoubleClick(int position, @NonNull MenuItem menu) {
                Toast.makeText(WithViewPagerActivity.this, "double click: " + position + " " + menu.getTitle(), Toast.LENGTH_SHORT).show();
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
