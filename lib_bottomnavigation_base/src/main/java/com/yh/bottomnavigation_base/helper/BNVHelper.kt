package com.yh.bottomnavigation_base.helper

import android.view.Menu
import android.view.MenuItem
import com.yh.bottomnavigation_base.AbsMenuListener
import com.yh.bottomnavigation_base.IBottomNavigationEx
import com.yh.bottomnavigation_base.IMenuDoubleClickListener
import com.yh.bottomnavigation_base.IMenuListener
import com.yh.bottomnavigation_base.ext.emptyCountBeforeMenuItem
import com.yh.bottomnavigation_base.ext.filterEmptyMenuIndex
import com.yh.bottomnavigation_base.ext.indexOf
import com.yh.bottomnavigation_base.ext.onDoubleClick
import com.yh.bottomnavigation_base.internal.InnerListener
import java.lang.ref.WeakReference
import kotlin.concurrent.thread

class BNVHelper(bottomNavigationEx: IBottomNavigationEx<*, *, *>) {

    private val iBNERef = WeakReference(bottomNavigationEx)
    private var previousPosition: Int = -1
    private var menuListener: IMenuListener? = null

    private var viewPagerHelper: AbsViewPagerHelper<*>? = null
    var emptyMenuIds: List<Int> = emptyList()

    private val innerListener: InnerListener by lazy {
        object : InnerListener {
            override fun onNavigationItemSelected(menu: Menu, item: MenuItem): Boolean {
                if (emptyMenuIds.contains(item.itemId)) {
                    (menuListener as? AbsMenuListener)?.onEmptyItemClick(menu.indexOf(item), item)
                    return false
                }

                val position = menu.indexOf(item)
                if (previousPosition == position) {
                    return true
                }

                val result = menuListener?.onNavigationItemSelected(position, item, false) ?: true
                if (!result) {
                    return false
                }

                viewPagerHelper?.updatePosition(
                    position - menu.emptyCountBeforeMenuItem(
                        item,
                        emptyMenuIds
                    )
                )

                previousPosition = position
                return true
            }

            override fun onNavigationItemReselected(menu: Menu, item: MenuItem) {
                if (emptyMenuIds.contains(item.itemId)) {
                    return
                }

                val position = menu.indexOf(item)

                menuListener?.onNavigationItemSelected(position, item, false)

                viewPagerHelper?.updatePosition(
                    position - menu.emptyCountBeforeMenuItem(
                        item,
                        emptyMenuIds
                    )
                )

                previousPosition = position
            }
        }
    }

    init {
        bottomNavigationEx.setInnerListener(innerListener)
    }

    fun getListener() = menuListener

    fun setListener(menuListener: IMenuListener?) {
        this.menuListener = menuListener
    }

    fun setupViewPagerHelper(absViewPagerHelper: AbsViewPagerHelper<*>) {
        viewPagerHelper?.release()
        viewPagerHelper = null
        viewPagerHelper = absViewPagerHelper
        viewPagerHelper?.setOnPageChangeCallback {
            val menuList = iBNERef.get()?.getMenuItems() ?: return@setOnPageChangeCallback

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

    fun setMenuDoubleClickListener(menuDoubleClickListener: IMenuDoubleClickListener) {
        thread {
            iBNERef.get()?.getAllBNItemView()?.runCatching {
                this.forEachIndexed { index, item ->
                    val menuItem = iBNERef.get()?.getMenuItems()?.getOrNull(index) ?: return@forEachIndexed
                    if (emptyMenuIds.contains(menuItem.itemId)) {
                        return@forEachIndexed
                    }
                    val menu = iBNERef.get()?.getMenu() ?: return@forEachIndexed
                    item.onDoubleClick {
                        menuDoubleClickListener.onDoubleClick(
                            menu.filterEmptyMenuIndex(menuItem, emptyMenuIds), menuItem
                        )
                    }
                }
            }
        }
    }

}