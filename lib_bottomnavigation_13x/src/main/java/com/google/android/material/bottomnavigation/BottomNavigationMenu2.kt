package com.google.android.material.bottomnavigation

import android.annotation.SuppressLint
import android.content.Context
import android.view.MenuItem
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuItemImpl
import androidx.appcompat.view.menu.MenuPresenter
import com.yh.bottomnavigation_base.ext.getFieldValue
import java.lang.ref.WeakReference
import java.util.concurrent.CopyOnWriteArrayList

@SuppressLint("RestrictedApi")
class BottomNavigationMenu2(context: Context) : MenuBuilder(context) {
    
    constructor(context: Context, originMenu: MenuBuilder) : this(context) {
        originMenu.getFieldValue<MenuBuilder, CopyOnWriteArrayList<*>>("mPresenters")
            .filterIsInstance<WeakReference<MenuPresenter>>()
            .mapNotNull { it.get() }
            .forEach {
                addMenuPresenter(it)
            }
    }
    
    override fun addInternal(group: Int, id: Int, categoryOrder: Int, title: CharSequence?): MenuItem {
        stopDispatchingItemsChanged()
        val item = super.addInternal(group, id, categoryOrder, title)
        if(item is MenuItemImpl) {
            item.isExclusiveCheckable = true
        }
        startDispatchingItemsChanged()
        return item
    }
}