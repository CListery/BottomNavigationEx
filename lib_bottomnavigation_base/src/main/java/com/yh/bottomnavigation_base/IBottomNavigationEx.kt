package com.yh.bottomnavigation_base

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RestrictTo
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.yh.bottomnavigation_base.internal.InnerListener

interface IBottomNavigationEx<BNV : View, BNMV : View, BNIV : View> {

    val realView: BNV

    /**
     * 修改图标可见状态
     */
    fun setIconVisibility(visibility: Boolean): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 修改文字可见状态
     */
    fun setTextVisibility(visibility: Boolean): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 是否开启动画
     */
    fun enableAnimation(enable: Boolean): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 设置导航项的标签可见性
     */
    fun enableLabelVisibility(enable: Boolean): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 设置当组合项宽度填满屏幕时菜单项是否在选择时水平平移
     */
    fun enableItemHorizontalTranslation(enable: Boolean): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 获取当前选中项的index
     */
    fun getCurrentIndex(): Int

    /**
     * 获取menuItem所在position
     */
    fun menuItemPositionAt(item: MenuItem): Int

    /**
     * 设置当前选中的index
     */
    fun setCurrentItem(index: Int): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 获取监听器
     */
    fun getMenuListener(): IMenuListener?

    /**
     * 获取监听器
     */
    fun setMenuListener(menuListener: IMenuListener): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 设置双击监听器
     */
    fun setMenuDoubleClickListener(menuDoubleClickListener: IMenuDoubleClickListener): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 设置内部监听器
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    fun setInnerListener(listener: InnerListener)

    /**
     * 获取 BottomNavigationMenuView 对象
     */
    fun getBNMenuView(): BNMV

    /**
     * 清除设置的图标 Tint 颜色
     */
    fun clearIconTintColor(): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 获取所有 BottomNavigationItemView
     */
    fun getAllBNItemView(): Array<BNIV>

    /**
     * 获取指定 position 对应的 BottomNavigationItemView
     */
    fun getBNItemView(position: Int): BNIV?

    /**
     * 获取指定 position 对应的 Icon's view
     */
    fun getIconAt(position: Int): ImageView?

    /**
     * 获取指定 position 对应的 SmallLabel's view
     */
    fun getSmallLabelAt(position: Int): TextView?

    /**
     * 获取指定 position 对应的 LargeLabel's view
     */
    fun getLargeLabelAt(position: Int): TextView?

    /**
     * 获取 BottomNavigationItemView count
     */
    fun getBNItemViewCount(): Int

    /**
     * 设置 SmallText 的字体大小
     */
    fun setSmallTextSize(sp: Float): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 设置 LargeText 的字体大小
     */
    fun setLargeTextSize(sp: Float): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 设置 SmallText、LargeText 的字体大小
     */
    fun setTextSize(sp: Float): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 设置指定 position 对应的 Icon 宽高
     */
    fun setIconSizeAt(position: Int, width: Float, height: Float): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 设置 Icon 宽高
     */
    fun setIconSize(width: Float, height: Float): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 设置 Icon 大小
     */
    fun setIconSize(dpSize: Float): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 设置 BottomNavigationMenuView 高度
     */
    fun setBNMenuViewHeight(height: Int): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 获取 BottomNavigationMenuView 高度
     */
    fun getBNMenuViewHeight(): Int

    /**
     * 设置字体和样式
     */
    fun setTypeface(typeface: Typeface, style: Int): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 设置字体和样式
     */
    fun setTypeface(typeface: Typeface): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 绑定到 ViewPager
     */
    fun setupWithViewPager(vp: ViewPager?): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 绑定到 ViewPager
     */
    fun setupWithViewPager(vp: ViewPager?, smoothScroll: Boolean): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 绑定到 ViewPager2
     */
    fun setupWithViewPager2(vp2: ViewPager2?): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 绑定到 ViewPager2
     */
    fun setupWithViewPager2(
        vp2: ViewPager2?,
        smoothScroll: Boolean
    ): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 设置 BottomNavigationItemView 的标签可见性
     */
    fun enableBNItemViewLabelVisibility(
        position: Int,
        enable: Boolean
    ): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 设置指定 position 的 BottomNavigationItemView 的 background 资源
     */
    fun setBNItemViewBackgroundRes(position: Int, background: Int): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 设置 Icon 的颜色
     */
    fun setIconTintList(tint: ColorStateList?): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 设置指定 position 的 Icon 的颜色
     */
    fun setIconTintList(position: Int, tint: ColorStateList?): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 设置字体的颜色
     */
    fun setTextTintList(tint: ColorStateList?): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 设置设置指定 position 的字体的颜色
     */
    fun setTextTintList(position: Int, tint: ColorStateList?): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 设置 Icon 的顶部边距
     */
    fun setIconsMarginTop(marginTop: Int): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 设置设置指定 position 的 Icon 的顶部边距
     */
    fun setIconMarginTop(position: Int, marginTop: Int): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 设置 empty menu ids，一般用于添加特殊按钮
     */
    fun setEmptyMenuIds(emptyMenuIds: List<Int>): IBottomNavigationEx<BNV, BNMV, BNIV>

    /**
     * 获取 menu
     */
    fun getMenu(): Menu

    /**
     * 获取 MenuItems
     */
    fun getMenuItems(): List<MenuItem>
}