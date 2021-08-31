package com.yh.bottomnavigation_base.helper

import androidx.annotation.CallSuper
import java.lang.ref.WeakReference

typealias OnPageChangeCallback = (position: Int) -> Unit

abstract class AbsViewPagerHelper<VP>(vp: VP, val smoothScroll: Boolean) {

    private var vpRef: WeakReference<VP> = WeakReference(vp)
    private var isNavigationItemClicking: Boolean = false

    private var onPageChangeCallback: OnPageChangeCallback? = null
    fun setOnPageChangeCallback(callback: OnPageChangeCallback) {
        onPageChangeCallback = callback
    }

    protected val vp: VP? get() = vpRef.get()

    @CallSuper
    open fun release() {
        vpRef.clear()
    }

    protected fun notifyPageChanged(position: Int) {
        if (!isNavigationItemClicking) {
            onPageChangeCallback?.invoke(position)
        }
    }

    fun updatePosition(position: Int) {
        // use isNavigationItemClicking flag to avoid `ViewPager.OnPageChangeListener` trigger
        isNavigationItemClicking = true
        changeVPPosition(position)
        isNavigationItemClicking = false
    }

    abstract fun changeVPPosition(position: Int)

}