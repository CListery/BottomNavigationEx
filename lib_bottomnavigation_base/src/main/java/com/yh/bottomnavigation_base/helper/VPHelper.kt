package com.yh.bottomnavigation_base.helper

import androidx.annotation.RestrictTo
import androidx.viewpager.widget.ViewPager

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
class VPHelper(vp: ViewPager, smoothScroll: Boolean = false) :
    AbsViewPagerHelper<ViewPager>(vp, smoothScroll) {

    init {
        vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            override fun onPageSelected(position: Int) {
                notifyPageChanged(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    override fun release() {
        vp?.clearOnPageChangeListeners()
        super.release()
    }

    override fun changeVPPosition(position: Int) {
        vp?.setCurrentItem(position, smoothScroll)
    }

}
