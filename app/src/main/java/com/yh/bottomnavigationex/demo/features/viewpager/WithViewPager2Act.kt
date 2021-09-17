package com.yh.bottomnavigationex.demo.features.viewpager

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yh.bottomnavigation_base.IMenuDoubleClickListener
import com.yh.bottomnavigationex.demo.R
import com.yh.bottomnavigationex.demo.common.base.BaseFragment
import com.yh.bottomnavigationex.demo.databinding.ActivityWithViewPager2Binding

class WithViewPager2Act : AppCompatActivity() {

    private val bind: ActivityWithViewPager2Binding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_with_view_pager2)
    }

    private var adapter: Vp2Adapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initData()
        initEvent()
    }

    /**
     * change BottomNavigationViewEx style
     */
    private fun initView() {
        bind.bnve.enableItemHorizontalTranslation(true)
        bind.bnve.enableAnimation(false)
    }

    /**
     * create fragments
     */
    private fun initData() {
        adapter = Vp2Adapter(this)
        bind.vp.adapter = adapter
    }

    private fun initEvent() {
        bind.bnve.setupWithViewPager2(bind.vp)
        bind.bnve.setMenuDoubleClickListener(object : IMenuDoubleClickListener {
            override fun onDoubleClick(position: Int, menu: MenuItem) {
                Toast.makeText(this@WithViewPager2Act, "double click: $position ${menu.title}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    inner class Vp2Adapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> {
                    val musicFragment = BaseFragment()
                    val bundle = Bundle()
                    bundle.putString("title", getString(R.string.music))
                    musicFragment.arguments = bundle
                    return musicFragment
                }
                1 -> {
                    val backupFragment = BaseFragment()
                    val bundle = Bundle()
                    bundle.putString("title", getString(R.string.backup))
                    backupFragment.arguments = bundle
                    return backupFragment
                }
                else -> {
                    val friendsFragment = BaseFragment()
                    val bundle = Bundle()
                    bundle.putString("title", getString(R.string.friends))
                    friendsFragment.arguments = bundle
                    return friendsFragment
                }
            }
        }
    }
}