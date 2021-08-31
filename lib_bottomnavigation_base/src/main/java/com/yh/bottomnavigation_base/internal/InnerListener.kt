package com.yh.bottomnavigation_base.internal

import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RestrictTo

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
interface InnerListener {
    fun onNavigationItemSelected(menu: Menu, item: MenuItem): Boolean
    fun onNavigationItemReselected(menu: Menu, item: MenuItem)
}