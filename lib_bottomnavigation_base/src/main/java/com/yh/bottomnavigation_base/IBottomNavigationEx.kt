package com.yh.bottomnavigation_base

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RestrictTo
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.yh.bottomnavigation_base.internal.InnerListener

interface IBottomNavigationEx<BNMV:View, BNIV:View> {
    fun setIconVisibility(visibility: Boolean): IBottomNavigationEx<BNMV, BNIV>
    fun setTextVisibility(visibility: Boolean): IBottomNavigationEx<BNMV, BNIV>
    fun enableAnimation(enable: Boolean): IBottomNavigationEx<BNMV, BNIV>
    fun enableShiftingMode(enable: Boolean): IBottomNavigationEx<BNMV, BNIV>
    fun enableItemShiftingMode(enable: Boolean): IBottomNavigationEx<BNMV, BNIV>
    fun getCurrentItem(): Int
    fun getMenuItemPosition(item: MenuItem): Int
    fun setCurrentItem(index: Int): IBottomNavigationEx<BNMV, BNIV>
    fun getMenuListener(): IMenuListener?
    fun setMenuListener(menuListener: IMenuListener)
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    fun setInnerListener(listener: InnerListener)
    fun getBottomNavigationMenuView(): BNMV
    fun clearIconTintColor(): IBottomNavigationEx<BNMV, BNIV>
    fun getBottomNavigationItemViews(): Array<BNIV>
    fun getBottomNavigationItemView(position: Int): BNIV?
    fun getIconAt(position: Int): ImageView?
    fun getSmallLabelAt(position: Int): TextView?
    fun getLargeLabelAt(position: Int): TextView?
    fun getItemCount(): Int
    fun setSmallTextSize(sp: Float): IBottomNavigationEx<BNMV, BNIV>
    fun setLargeTextSize(sp: Float): IBottomNavigationEx<BNMV, BNIV>
    fun setTextSize(sp: Float): IBottomNavigationEx<BNMV, BNIV>
    fun setIconSizeAt(position: Int, width: Float, height: Float): IBottomNavigationEx<BNMV, BNIV>
    fun setIconSize(width: Float, height: Float): IBottomNavigationEx<BNMV, BNIV>
    fun setIconSize(dpSize: Float): IBottomNavigationEx<BNMV, BNIV>
    fun setItemHeight(height: Int): IBottomNavigationEx<BNMV, BNIV>
    fun getItemHeight(): Int
    fun setTypeface(typeface: Typeface, style: Int): IBottomNavigationEx<BNMV, BNIV>
    fun setTypeface(typeface: Typeface): IBottomNavigationEx<BNMV, BNIV>
    fun setupWithViewPager(vp: ViewPager?): IBottomNavigationEx<BNMV, BNIV>
    fun setupWithViewPager(vp: ViewPager?, smoothScroll: Boolean): IBottomNavigationEx<BNMV, BNIV>
    fun setupWithViewPager2(vp2: ViewPager2?): IBottomNavigationEx<BNMV, BNIV>
    fun setupWithViewPager2(vp2: ViewPager2?, smoothScroll: Boolean): IBottomNavigationEx<BNMV, BNIV>
    fun enableShiftingMode(position: Int, enable: Boolean): IBottomNavigationEx<BNMV, BNIV>
    fun setItemBackground(position: Int, background: Int): IBottomNavigationEx<BNMV, BNIV>
    fun setIconTintList(tint: ColorStateList?): IBottomNavigationEx<BNMV, BNIV>
    fun setIconTintList(position: Int, tint: ColorStateList?): IBottomNavigationEx<BNMV, BNIV>
    fun setTextTintList(tint: ColorStateList?): IBottomNavigationEx<BNMV, BNIV>
    fun setTextTintList(position: Int, tint: ColorStateList?): IBottomNavigationEx<BNMV, BNIV>
    fun setIconsMarginTop(marginTop: Int): IBottomNavigationEx<BNMV, BNIV>
    fun setIconMarginTop(position: Int, marginTop: Int): IBottomNavigationEx<BNMV, BNIV>
    fun setEmptyMenuIds(emptyMenuIds: List<Int>):IBottomNavigationEx<BNMV, BNIV>
    fun getMenuList(): List<MenuItem>
}