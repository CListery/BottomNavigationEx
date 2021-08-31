package com.yh.bottomnavigation_base.helper

import android.view.Menu
import android.view.MenuItem
import androidx.core.view.forEachIndexed
import com.yh.bottomnavigation_base.IBottomNavigationEx
import com.yh.bottomnavigation_base.IListener
import com.yh.bottomnavigation_base.internal.InnerListener
import java.lang.ref.WeakReference

class BNVHelper(bottomNavigationEx: IBottomNavigationEx<*, *>) {

    private val iBNERef = WeakReference(bottomNavigationEx)
    private var previousPosition: Int = -1
    private var listener: IListener? = null

    private var viewPagerHelper: AbsViewPagerHelper<*>? = null
    var emptyMenuIds: List<Int> = emptyList()

    private val innerListener: InnerListener by lazy {
        object : InnerListener {
            override fun onNavigationItemSelected(menu: Menu, item: MenuItem): Boolean {
                if (emptyMenuIds.contains(item.itemId)) {
                    return false
                }

                val position = menu.indexOf(item)
                if (previousPosition == position) {
                    return true
                }

                val result = listener?.onNavigationItemSelected(position, item, false) ?: true
                if (!result) {
                    return false
                }

                viewPagerHelper?.updatePosition(position - menu.filterEmptyMenuCount(item))

                previousPosition = position
                return true
            }

            override fun onNavigationItemReselected(menu: Menu, item: MenuItem) {
                if (emptyMenuIds.contains(item.itemId)) {
                    return
                }

                val position = menu.indexOf(item)

                listener?.onNavigationItemSelected(position, item, false)

                viewPagerHelper?.updatePosition(position - menu.filterEmptyMenuCount(item))

                previousPosition = position
            }
        }
    }

    init {
        bottomNavigationEx.setInnerListener(innerListener)
    }

    fun getListener() = listener

    fun setListener(listener: IListener?) {
        this.listener = listener
    }

    fun setupViewPagerHelper(absViewPagerHelper: AbsViewPagerHelper<*>) {
        viewPagerHelper?.release()
        viewPagerHelper = null
        viewPagerHelper = absViewPagerHelper
        viewPagerHelper?.setOnPageChangeCallback {
            val menuList = iBNERef.get()?.getMenuList() ?: return@setOnPageChangeCallback

            var position = it
            menuList.forEachIndexed { index, item ->
                if (emptyMenuIds.contains(item.itemId)) {
                    position++
                }
                if (index == position) {
                    iBNERef.get()?.setCurrentItem(position)
                    previousPosition = position
                    return@setOnPageChangeCallback
                }
            }

            iBNERef.get()?.setCurrentItem(position)
            previousPosition = position
        }
    }

    /**
     * 过滤 emptyMenuIds
     */
    fun Menu.filterEmptyMenuIndex(menuItem: MenuItem): Int {
        return indexOf(menuItem) + filterEmptyMenuCount(menuItem)
    }

    fun Menu.indexOf(menuItem: MenuItem): Int {
        forEachIndexed { index, item ->
            if (item.itemId == menuItem.itemId) {
                return index
            }
        }
        return -1
    }

    private fun Menu.filterEmptyMenuCount(item: MenuItem): Int {
        if (emptyMenuIds.isEmpty()) {
            return 0
        } else {
            var count = 0
            var index = indexOf(item)
            forEachIndexed { i, m ->
                if (i < index) {
                    if (emptyMenuIds.contains(m.itemId)) {
                        count++
                    }
                } else if (i == index) {
                    if (emptyMenuIds.contains(m.itemId)) {
                        count++
                        index++
                    }
                } else {
                    return count
                }
            }
            return count
        }
    }
}