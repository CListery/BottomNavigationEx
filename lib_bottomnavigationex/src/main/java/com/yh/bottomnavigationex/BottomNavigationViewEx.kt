package com.yh.bottomnavigationex

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.BottomNavigationViewV130
import com.google.android.material.bottomnavigation.BottomNavigationViewV140
import com.yh.bottomnavigation_base.IBottomNavigationEx
import com.yh.bottomnavigation_base.IListener
import com.yh.bottomnavigation_base.internal.InnerListener

class BottomNavigationViewEx : View, IBottomNavigationEx<View, View> {

    @Suppress("JoinDeclarationAndAssignment")
    private var iBottomNavigationEx: IBottomNavigationEx<*, *>
    private var isLoaded: Boolean = false

    private val inflateRunnable = { inflate() }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        iBottomNavigationEx = when (Utils.materialVersion) {
            "1.4.0" -> BottomNavigationViewV140(context, attrs, defStyleAttr)
            else -> BottomNavigationViewV130(context, attrs, defStyleAttr)
        }

        visibility = GONE
        setWillNotDraw(true)

        post { inflateRunnable }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    val realView get() = iBottomNavigationEx as BottomNavigationView

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        inflate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(0, 0)
    }

    @SuppressLint("MissingSuperCall")
    override fun draw(canvas: Canvas?) {
    }

    override fun dispatchDraw(canvas: Canvas?) {}

    private fun replaceSelfWithView(parent: ViewGroup) {
        val index = parent.indexOfChild(this)
        if (-1 == index) {
            return
        }
        parent.removeViewInLayout(this)
        val layoutParams = layoutParams
        if (layoutParams != null) {
            parent.addView(realView, index, layoutParams)
        } else {
            parent.addView(realView, index)
        }
    }

    private fun inflate() {
        removeCallbacks(inflateRunnable)
        if (isLoaded) {
            return
        }
        val viewParent = parent
        if (viewParent != null && viewParent is ViewGroup) {
            isLoaded = true
            replaceSelfWithView(viewParent)
        }
    }

    override fun setIconVisibility(visibility: Boolean): BottomNavigationViewEx {
        iBottomNavigationEx.setIconVisibility(visibility)
        return this
    }

    override fun setTextVisibility(visibility: Boolean): BottomNavigationViewEx {
        iBottomNavigationEx.setTextVisibility(visibility)
        return this
    }

    override fun enableAnimation(enable: Boolean): BottomNavigationViewEx {
        iBottomNavigationEx.enableAnimation(enable)
        return this
    }

    override fun enableShiftingMode(enable: Boolean): BottomNavigationViewEx {
        iBottomNavigationEx.enableShiftingMode(enable)
        return this
    }

    override fun enableShiftingMode(position: Int, enable: Boolean): BottomNavigationViewEx {
        iBottomNavigationEx.enableShiftingMode(position, enable)
        return this
    }

    override fun enableItemShiftingMode(enable: Boolean): BottomNavigationViewEx {
        iBottomNavigationEx.enableShiftingMode(enable)
        return this
    }

    override fun getCurrentItem(): Int {
        return iBottomNavigationEx.getCurrentItem()
    }

    override fun getMenuItemPosition(item: MenuItem): Int {
        return iBottomNavigationEx.getMenuItemPosition(item)
    }

    override fun setCurrentItem(index: Int): BottomNavigationViewEx {
        iBottomNavigationEx.setCurrentItem(index)
        return this
    }

    override fun getListener(): IListener? {
        return iBottomNavigationEx.getListener()
    }

    override fun setListener(listener: IListener) {
        iBottomNavigationEx.setListener(listener)
    }

    override fun getBottomNavigationMenuView(): View {
        return iBottomNavigationEx.getBottomNavigationMenuView()
    }

    override fun clearIconTintColor(): BottomNavigationViewEx {
        iBottomNavigationEx.clearIconTintColor()
        return this
    }

    override fun getBottomNavigationItemViews(): Array<View> {
        return iBottomNavigationEx.getBottomNavigationItemViews().asList().toTypedArray()
    }

    override fun getBottomNavigationItemView(position: Int): View? {
        return iBottomNavigationEx.getBottomNavigationItemView(position)
    }

    override fun getIconAt(position: Int): ImageView? {
        return iBottomNavigationEx.getIconAt(position)
    }

    override fun getSmallLabelAt(position: Int): TextView? {
        return iBottomNavigationEx.getSmallLabelAt(position)
    }

    override fun getLargeLabelAt(position: Int): TextView? {
        return iBottomNavigationEx.getLargeLabelAt(position)
    }

    override fun getItemCount(): Int {
        return iBottomNavigationEx.getItemCount()
    }

    override fun setSmallTextSize(sp: Float): BottomNavigationViewEx {
        iBottomNavigationEx.setSmallTextSize(sp)
        return this
    }

    override fun setLargeTextSize(sp: Float): BottomNavigationViewEx {
        iBottomNavigationEx.setLargeTextSize(sp)
        return this
    }

    override fun setTextSize(sp: Float): BottomNavigationViewEx {
        iBottomNavigationEx.setTextSize(sp)
        return this
    }

    override fun setIconSizeAt(position: Int, width: Float, height: Float): BottomNavigationViewEx {
        iBottomNavigationEx.setIconSizeAt(position, width, height)
        return this
    }

    override fun setIconSize(width: Float, height: Float): BottomNavigationViewEx {
        iBottomNavigationEx.setIconSize(width, height)
        return this
    }

    override fun setIconSize(dpSize: Float): BottomNavigationViewEx {
        iBottomNavigationEx.setIconSize(dpSize)
        return this
    }

    override fun setItemHeight(height: Int): BottomNavigationViewEx {
        iBottomNavigationEx.setItemHeight(height)
        return this
    }

    override fun getItemHeight(): Int {
        return iBottomNavigationEx.getItemHeight()
    }

    override fun setTypeface(typeface: Typeface, style: Int): BottomNavigationViewEx {
        iBottomNavigationEx.setTypeface(typeface, style)
        return this
    }

    override fun setTypeface(typeface: Typeface): BottomNavigationViewEx {
        iBottomNavigationEx.setTypeface(typeface)
        return this
    }

    override fun setupWithViewPager(vp: ViewPager?): BottomNavigationViewEx {
        iBottomNavigationEx.setupWithViewPager(vp)
        return this
    }

    override fun setupWithViewPager(
        vp: ViewPager?,
        smoothScroll: Boolean
    ): BottomNavigationViewEx {
        iBottomNavigationEx.setupWithViewPager(vp, smoothScroll)
        return this
    }

    override fun setupWithViewPager2(vp2: ViewPager2?): BottomNavigationViewEx {
        iBottomNavigationEx.setupWithViewPager2(vp2)
        return this
    }

    override fun setupWithViewPager2(
        vp2: ViewPager2?,
        smoothScroll: Boolean
    ): BottomNavigationViewEx {
        iBottomNavigationEx.setupWithViewPager2(vp2, smoothScroll)
        return this
    }

    override fun setItemBackground(position: Int, background: Int): BottomNavigationViewEx {
        iBottomNavigationEx.setItemBackground(position, background)
        return this
    }

    override fun setIconTintList(tint: ColorStateList?): IBottomNavigationEx<View, View> {
        iBottomNavigationEx.setIconTintList(tint)
        return this
    }

    override fun setIconTintList(position: Int, tint: ColorStateList?): BottomNavigationViewEx {
        iBottomNavigationEx.setIconTintList(position, tint)
        return this
    }

    override fun setTextTintList(tint: ColorStateList?): IBottomNavigationEx<View, View> {
        iBottomNavigationEx.setTextTintList(tint)
        return this
    }

    override fun setTextTintList(position: Int, tint: ColorStateList?): BottomNavigationViewEx {
        iBottomNavigationEx.setTextTintList(position, tint)
        return this
    }

    override fun setIconsMarginTop(marginTop: Int): BottomNavigationViewEx {
        iBottomNavigationEx.setIconsMarginTop(marginTop)
        return this
    }

    override fun setIconMarginTop(position: Int, marginTop: Int): BottomNavigationViewEx {
        iBottomNavigationEx.setIconMarginTop(position, marginTop)
        return this
    }

    override fun setInnerListener(listener: InnerListener) {
        throw IllegalStateException("can not call this")
    }

    override fun setEmptyMenuIds(emptyMenuIds: List<Int>): IBottomNavigationEx<View, View> {
        iBottomNavigationEx.setEmptyMenuIds(emptyMenuIds)
        return this
    }

    override fun getMenuList(): List<MenuItem> {
        return iBottomNavigationEx.getMenuList()
    }
}