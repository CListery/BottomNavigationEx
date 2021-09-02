package com.yh.bottomnavigation_base

import android.view.MenuItem

interface IMenuListener {
    fun onNavigationItemSelected(position: Int, menu: MenuItem, isReSelected: Boolean): Boolean
}

abstract class AbsMenuListener : IMenuListener {
    override fun onNavigationItemSelected(
        position: Int,
        menu: MenuItem,
        isReSelected: Boolean
    ): Boolean {
        return false
    }

    abstract fun onEmptyItemClick(position: Int, menu: MenuItem)
}