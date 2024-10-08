package com.yh.bottomnavigationex

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Typeface
import android.os.Parcelable
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.*
import com.yh.bottomnavigation_base.IBottomNavigationEx
import com.yh.bottomnavigation_base.IMenuDoubleClickListener
import com.yh.bottomnavigation_base.IMenuListener
import com.yh.bottomnavigation_base.ext.dp2px
import com.yh.bottomnavigation_base.internal.InnerListener

class BottomNavigationViewEx : View, IBottomNavigationEx<BottomNavigationView, BottomNavigationMenuView, BottomNavigationItemView> {

    private var iBottomNavigationEx: IBottomNavigationEx<BottomNavigationView, BottomNavigationMenuView, BottomNavigationItemView>
    private var isLoaded: Boolean = false

    private val inflateRunnable = { inflate() }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        MaterialUtils.init(context)
        iBottomNavigationEx = when (MaterialUtils.version) {
            MaterialUtils.MaterialVersion.V_1_4_X -> BottomNavigationViewV14x(context, attrs, defStyleAttr)
            MaterialUtils.MaterialVersion.V_1_5_X -> BottomNavigationViewV15x(context, attrs, defStyleAttr)
            MaterialUtils.MaterialVersion.V_1_6_X -> BottomNavigationViewV16x(context, attrs, defStyleAttr)
            MaterialUtils.MaterialVersion.V_1_7_X -> BottomNavigationViewV17x(context, attrs, defStyleAttr)
            MaterialUtils.MaterialVersion.V_1_8_X -> BottomNavigationViewV18x(context, attrs, defStyleAttr)
            MaterialUtils.MaterialVersion.V_1_9_X -> BottomNavigationViewV19x(context, attrs, defStyleAttr)
            MaterialUtils.MaterialVersion.V_1_10_X -> BottomNavigationViewV110x(context, attrs, defStyleAttr)
            else-> BottomNavigationViewV13x(
                context,
                attrs,
                defStyleAttr)
        }
        
        visibility = GONE
        setWillNotDraw(true)

        post { inflateRunnable }
    }

    override val realView: BottomNavigationView get() = iBottomNavigationEx as BottomNavigationView

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        inflate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(0, 0)
    }

    @SuppressLint("MissingSuperCall")
    override fun draw(canvas: Canvas) {
    }

    override fun dispatchDraw(canvas: Canvas) {}
    
    override fun setLayoutParams(params: ViewGroup.LayoutParams?) {
        super.setLayoutParams(params)
        realView.layoutParams = params
    }
    
    private fun replaceSelfWithView(parent: ViewGroup) {
        val index = parent.indexOfChild(this)
        if (-1 == index) {
            return
        }
        parent.removeViewInLayout(this)
        val itemHeight = when (MaterialUtils.version) {
            MaterialUtils.MaterialVersion.V_1_5_X,
            MaterialUtils.MaterialVersion.V_1_6_X,
            MaterialUtils.MaterialVersion.V_1_7_X,
            MaterialUtils.MaterialVersion.V_1_8_X,
            MaterialUtils.MaterialVersion.V_1_9_X,
            MaterialUtils.MaterialVersion.V_1_10_X,
            -> context.dp2px(56)
            else -> -2
        }
        val layoutParams = layoutParams ?: ViewGroup.LayoutParams(-1, itemHeight)
        layoutParams.height = if (layoutParams.height > 0) {
            layoutParams.height
        } else {
            itemHeight
        }
        parent.addView(realView, index, layoutParams)
        realView.layoutParams = layoutParams
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

    override fun enableLabelVisibility(enable: Boolean): BottomNavigationViewEx {
        iBottomNavigationEx.enableLabelVisibility(enable)
        return this
    }

    override fun enableBNItemViewLabelVisibility(position: Int, enable: Boolean): BottomNavigationViewEx {
        iBottomNavigationEx.enableBNItemViewLabelVisibility(position, enable)
        return this
    }

    override fun enableItemHorizontalTranslation(enable: Boolean): BottomNavigationViewEx {
        iBottomNavigationEx.enableLabelVisibility(enable)
        return this
    }

    override fun getCurrentIndex(): Int {
        return iBottomNavigationEx.getCurrentIndex()
    }

    override fun menuItemPositionAt(item: MenuItem): Int {
        return iBottomNavigationEx.menuItemPositionAt(item)
    }

    override fun setCurrentItem(index: Int): BottomNavigationViewEx {
        iBottomNavigationEx.setCurrentItem(index)
        return this
    }

    override fun getMenuListener(): IMenuListener? {
        return iBottomNavigationEx.getMenuListener()
    }

    override fun setMenuListener(menuListener: IMenuListener): BottomNavigationViewEx {
        iBottomNavigationEx.setMenuListener(menuListener)
        return this
    }

    override fun setMenuDoubleClickListener(menuDoubleClickListener: IMenuDoubleClickListener): BottomNavigationViewEx {
        iBottomNavigationEx.setMenuDoubleClickListener(menuDoubleClickListener)
        return this
    }

    override fun getBNMenuView(): BottomNavigationMenuView {
        return iBottomNavigationEx.getBNMenuView()
    }

    override fun clearIconTintColor(): BottomNavigationViewEx {
        iBottomNavigationEx.clearIconTintColor()
        return this
    }

    override fun getAllBNItemView(): Array<BottomNavigationItemView> {
        return iBottomNavigationEx.getAllBNItemView().asList().toTypedArray()
    }

    override fun getBNItemView(position: Int): BottomNavigationItemView? {
        return iBottomNavigationEx.getBNItemView(position)
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

    override fun getBNItemViewCount(): Int {
        return iBottomNavigationEx.getBNItemViewCount()
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

    override fun setBNMenuViewHeight(height: Int): BottomNavigationViewEx {
        iBottomNavigationEx.setBNMenuViewHeight(height)
        return this
    }

    override fun getBNMenuViewHeight(): Int {
        return iBottomNavigationEx.getBNMenuViewHeight()
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

    override fun setBNItemViewBackgroundRes(position: Int, background: Int): BottomNavigationViewEx {
        iBottomNavigationEx.setBNItemViewBackgroundRes(position, background)
        return this
    }

    override fun setIconTintList(tint: ColorStateList?): BottomNavigationViewEx {
        iBottomNavigationEx.setIconTintList(tint)
        return this
    }

    override fun setIconTintList(position: Int, tint: ColorStateList?): BottomNavigationViewEx {
        iBottomNavigationEx.setIconTintList(position, tint)
        return this
    }

    override fun setTextTintList(tint: ColorStateList?): BottomNavigationViewEx {
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

    override fun setEmptyMenuIds(emptyMenuIds: List<Int>): BottomNavigationViewEx {
        iBottomNavigationEx.setEmptyMenuIds(emptyMenuIds)
        return this
    }

    override fun getMenuItems(): List<MenuItem> {
        return iBottomNavigationEx.getMenuItems()
    }

    override fun getMenu(): Menu {
        return iBottomNavigationEx.getMenu()
    }
    
    override fun configDynamic(
        count: Int,
        generator: (menu: Menu, index: Int) -> MenuItem
    ): BottomNavigationViewEx {
        iBottomNavigationEx.configDynamic(count, generator)
        return this
    }
    
    override fun getMenuMaxItemCount(): Int {
        return iBottomNavigationEx.getMenuMaxItemCount()
    }

    override fun restoreInstanceState(state: Parcelable?) {

    }

    override fun saveInstanceState(): Parcelable? {
        return null
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        try {
            super.onRestoreInstanceState(null)
        } catch (e: Exception) {
        }
        iBottomNavigationEx.restoreInstanceState(state)
    }

    override fun onSaveInstanceState(): Parcelable? {
        try {
            super.onSaveInstanceState()
        } catch (e: Exception) {
        }
        return iBottomNavigationEx.saveInstanceState()
    }
    
    override fun setItemOnTouchListener(menuItem: MenuItem, onTouchListener: OnTouchListener) {
        iBottomNavigationEx.setItemOnTouchListener(menuItem, onTouchListener)
    }
}