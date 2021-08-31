package com.yh.bottomnavigation_base

import android.view.MenuItem

interface IListener {
    fun onNavigationItemSelected(position: Int, menu: MenuItem, isReSelected: Boolean): Boolean
}