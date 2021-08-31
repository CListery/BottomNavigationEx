package com.yh.bottomnavigation_base.helper

import androidx.viewpager2.widget.ViewPager2

class VP2Helper(vp2: ViewPager2, smoothScroll: Boolean = false) :
    AbsViewPagerHelper<ViewPager2>(vp2, smoothScroll) {

    init {
        vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                notifyPageChanged(position)
            }
        })
    }

    override fun changeVPPosition(position: Int) {
        vp?.setCurrentItem(position, smoothScroll)
    }
}