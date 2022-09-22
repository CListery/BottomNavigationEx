package com.google.android.material.bottomnavigation

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.forEachIndexed
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.R
import com.google.android.material.navigation.NavigationBarItemView
import com.yh.bottomnavigation_base.IBottomNavigationEx
import com.yh.bottomnavigation_base.IMenuDoubleClickListener
import com.yh.bottomnavigation_base.IMenuListener
import com.yh.bottomnavigation_base.ext.getField
import com.yh.bottomnavigation_base.ext.setField
import com.yh.bottomnavigation_base.helper.BNVHelper
import com.yh.bottomnavigation_base.helper.VP2Helper
import com.yh.bottomnavigation_base.helper.VPHelper
import com.yh.bottomnavigation_base.internal.InnerListener
import kotlin.math.ceil

@SuppressLint("RestrictedApi")
class BottomNavigationViewV140 : BottomNavigationView,
    IBottomNavigationEx<BottomNavigationView, BottomNavigationMenuView, BottomNavigationItemView> {

    // used for animation
    private var labelSizeRecord = false
    private var largeLabelSize = 0f
    private var smallLabelSize = 0f
    private var visibilityHeightRecord = false
    private var itemHeight = 0
    private var textVisibility = true
    // used for animation end

    private val theBottomNavigationMenuView by lazy {
        menuView as BottomNavigationMenuView
    }
    private val theBottomNavigationItemViews: Array<BottomNavigationItemView> by lazy {
        theBottomNavigationMenuView.getField<BottomNavigationMenuView, Array<NavigationBarItemView>>("buttons")
            .filterIsInstance<BottomNavigationItemView>().toTypedArray()
    }

    private var innerListener: InnerListener? = null
    private val bnvHelper: BNVHelper

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        R.attr.bottomNavigationStyle
    )

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        R.style.Widget_Design_BottomNavigationView
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        bnvHelper = BNVHelper(this)
        setOnItemSelectedListener {
            return@setOnItemSelectedListener innerListener?.onNavigationItemSelected(menu, it)
                ?: false
        }
        setOnItemReselectedListener {
            innerListener?.onNavigationItemSelected(menu, it)
        }
    }

    override val realView: BottomNavigationView get() = this

    override fun setIconVisibility(visibility: Boolean): BottomNavigationViewV140 {
        for (b in theBottomNavigationItemViews) {
            val icon: ImageView = b.getField("icon")
            icon.visibility = if (visibility) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        }
        if (!visibility) {
            if (!visibilityHeightRecord) {
                visibilityHeightRecord = true
                itemHeight = getBNMenuViewHeight()
            }

            val button = theBottomNavigationItemViews.firstOrNull()
            if (null != button) {
                val icon: ImageView = button.getField("icon")
                icon.post {
                    setBNMenuViewHeight(itemHeight - icon.measuredHeight)
                }
            }
        } else {
            if (!visibilityHeightRecord) {
                return this
            }
            setBNMenuViewHeight(itemHeight)
        }
        return this
    }

    override fun setTextVisibility(visibility: Boolean): BottomNavigationViewV140 {
        this.textVisibility = visibility

        for (b in theBottomNavigationItemViews) {
            val largeLabel: TextView = b.getField("largeLabel")
            val smallLabel: TextView = b.getField("smallLabel")

            if (visibility) {
                // if not record the font size, we need do nothing.
                if (!labelSizeRecord) {
                    return this
                }

                // restore it
                largeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, largeLabelSize)
                smallLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallLabelSize)
            } else {
                // if not record the font size, record it
                if (!labelSizeRecord) {
                    largeLabelSize = largeLabel.textSize
                    smallLabelSize = smallLabel.textSize
                    labelSizeRecord = true
                }

                // if not visitable, set font size to 0
                largeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0f)
                smallLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0f)
            }
        }

        // 4 change mItemHeight to only icon size in menuView
        if (visibility) {
            // if not record the mItemHeight, we need do nothing.
            if (!visibilityHeightRecord) {
                return this
            }
            // restore mItemHeight
            setBNMenuViewHeight(itemHeight)
        } else {
            // if not record mItemHeight
            if (!visibilityHeightRecord) {
                itemHeight = getBNMenuViewHeight()
                visibilityHeightRecord = true
            }

            // change mItemHeight to only icon size in menuView
            // private final int mItemHeight;
            setBNMenuViewHeight(itemHeight - getFontHeight(smallLabelSize))
        }
        theBottomNavigationMenuView.updateMenuView()
        return this

    }

    /**
     * get text height by font size
     *
     * @param fontSize
     * @return
     */
    private fun getFontHeight(fontSize: Float): Int {
        val paint = Paint()
        paint.textSize = fontSize
        val fm = paint.fontMetrics
        return ceil((fm.descent - fm.top).toDouble()).toInt() + 2
    }

    override fun enableAnimation(enable: Boolean): BottomNavigationViewV140 {
        for (b in theBottomNavigationItemViews) {
            val largeLabel: TextView = b.getField("largeLabel")
            val smallLabel: TextView = b.getField("smallLabel")

            if (!enable) {
                if (!labelSizeRecord) {
                    largeLabelSize = largeLabel.textSize
                    smallLabelSize = smallLabel.textSize
                    labelSizeRecord = true
                }
                // disable
                largeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallLabelSize)

                // trigger calculateTextScaleFactors
                b.setTextAppearanceInactive(itemTextAppearanceInactive)
                b.setTextAppearanceActive(itemTextAppearanceInactive)
            } else {
                // haven't change the value. It means it was the first call this method. So nothing need to do.
                if (!labelSizeRecord) {
                    return this
                }
                // restore
                largeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, largeLabelSize)

                // trigger calculateTextScaleFactors
                b.setTextAppearanceInactive(itemTextAppearanceInactive)
                b.setTextAppearanceActive(itemTextAppearanceActive)
            }
        }
        theBottomNavigationMenuView.updateMenuView()
        return this
    }
    
    override fun enableLabelVisibility(enable: Boolean): BottomNavigationViewV140 {
        labelVisibilityMode = if (enable) LABEL_VISIBILITY_SELECTED else LABEL_VISIBILITY_LABELED
        return this
    }

    override fun enableBNItemViewLabelVisibility(position: Int, enable: Boolean): BottomNavigationViewV140 {
        getBNItemView(position)?.setShifting(enable)
        return this
    }

    override fun enableItemHorizontalTranslation(enable: Boolean): BottomNavigationViewV140 {
        isItemHorizontalTranslationEnabled = enable
        return this
    }

    override fun getCurrentIndex(): Int {
        menu.forEachIndexed { index, item ->
            if (item.isChecked) {
                return index
            }
        }
        return -1
    }

    override fun menuItemPositionAt(item: MenuItem): Int {
        menu.forEachIndexed { index, m ->
            if (m.itemId == item.itemId) {
                return index
            }
        }
        return -1
    }

    override fun setCurrentItem(index: Int): BottomNavigationViewV140 {
        val target = menu.getItem(index)
        if (bnvHelper.emptyMenuIds.contains(target.itemId)) {
            if (index >= menu.size()) {
                return this
            }
            for (pos in index + 1 until menu.size()) {
                val m = menu.getItem(pos)
                if (!bnvHelper.emptyMenuIds.contains(m.itemId)) {
                    selectedItemId = target.itemId
                    return this
                }
            }
        } else {
            selectedItemId = target.itemId
        }
        return this
    }

    override fun getMenuListener(): IMenuListener? {
        return bnvHelper.getListener()
    }

    override fun setMenuListener(menuListener: IMenuListener): BottomNavigationViewV140 {
        bnvHelper.setListener(menuListener)
        return this
    }

    override fun setMenuDoubleClickListener(menuDoubleClickListener: IMenuDoubleClickListener): BottomNavigationViewV140 {
        bnvHelper.setMenuDoubleClickListener(menuDoubleClickListener)
        return this
    }

    override fun setInnerListener(listener: InnerListener) {
        this.innerListener = listener
    }

    override fun getBNMenuView(): BottomNavigationMenuView {
        return theBottomNavigationMenuView
    }

    override fun clearIconTintColor(): BottomNavigationViewV140 {
        theBottomNavigationMenuView.iconTintList = null
        return this
    }

    override fun getAllBNItemView(): Array<BottomNavigationItemView> {
        return theBottomNavigationItemViews
    }

    override fun getBNItemView(position: Int): BottomNavigationItemView? {
        return theBottomNavigationItemViews.getOrNull(position)
    }

    override fun getIconAt(position: Int): ImageView? {
        return getBNItemView(position)?.getField("icon")
    }

    override fun getSmallLabelAt(position: Int): TextView? {
        return getBNItemView(position)?.getField("smallLabel")
    }

    override fun getLargeLabelAt(position: Int): TextView? {
        return getBNItemView(position)?.getField("largeLabel")
    }

    override fun getBNItemViewCount(): Int {
        return theBottomNavigationItemViews.size
    }

    override fun setSmallTextSize(sp: Float): BottomNavigationViewV140 {
        val count = getBNItemViewCount()
        for (i in 0 until count) {
            getSmallLabelAt(i)?.textSize = sp
        }
        theBottomNavigationMenuView.updateMenuView()
        return this
    }

    override fun setLargeTextSize(sp: Float): BottomNavigationViewV140 {
        val count = getBNItemViewCount()
        for (i in 0 until count) {
            getLargeLabelAt(i)?.textSize = sp
        }
        theBottomNavigationMenuView.updateMenuView()
        return this
    }

    override fun setTextSize(sp: Float): BottomNavigationViewV140 {
        setLargeTextSize(sp)
        setSmallTextSize(sp)
        return this
    }

    override fun setIconSizeAt(
        position: Int,
        width: Float,
        height: Float
    ): BottomNavigationViewV140 {
        val icon = getIconAt(position) ?: return this
        // update size
        val layoutParams = icon.layoutParams
        layoutParams.width = dp2px(context, width)
        layoutParams.height = dp2px(context, height)
        icon.layoutParams = layoutParams

        theBottomNavigationMenuView.updateMenuView()
        return this
    }

    /**
     * dp to px
     *
     * @param context
     * @param dpValue dp
     * @return px
     */
    private fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    override fun setIconSize(width: Float, height: Float): BottomNavigationViewV140 {
        val count = getBNItemViewCount()
        for (i in 0 until count) {
            setIconSizeAt(i, width, height)
        }
        return this
    }

    override fun setIconSize(dpSize: Float): BottomNavigationViewV140 {
        itemIconSize = dp2px(context, dpSize)
        return this
    }

    override fun setBNMenuViewHeight(height: Int): BottomNavigationViewV140 {
        if(theBottomNavigationMenuView.setField("itemHeight", height)){
            theBottomNavigationMenuView.updateMenuView()
        }
        return this
    }

    override fun getBNMenuViewHeight(): Int {
        return theBottomNavigationMenuView.getField("itemHeight")
    }

    override fun setTypeface(typeface: Typeface, style: Int): BottomNavigationViewV140 {
        val count = getBNItemViewCount()
        for (i in 0 until count) {
            getLargeLabelAt(i)?.setTypeface(typeface, style)
            getSmallLabelAt(i)?.setTypeface(typeface, style)
        }
        theBottomNavigationMenuView.updateMenuView()
        return this
    }

    override fun setTypeface(typeface: Typeface): BottomNavigationViewV140 {
        val count = getBNItemViewCount()
        for (i in 0 until count) {
            getLargeLabelAt(i)?.typeface = typeface
            getSmallLabelAt(i)?.typeface = typeface
        }
        theBottomNavigationMenuView.updateMenuView()
        return this
    }

    override fun setupWithViewPager(
        vp: ViewPager?
    ): BottomNavigationViewV140 {
        return setupWithViewPager(vp, false)
    }

    override fun setupWithViewPager(
        vp: ViewPager?,
        smoothScroll: Boolean
    ): BottomNavigationViewV140 {
        if (null == vp) {
            return this
        }
        bnvHelper.setupViewPagerHelper(VPHelper(vp, smoothScroll))
        return this
    }

    override fun setupWithViewPager2(
        vp2: ViewPager2?
    ): BottomNavigationViewV140 {
        return setupWithViewPager2(vp2, false)
    }

    override fun setupWithViewPager2(
        vp2: ViewPager2?,
        smoothScroll: Boolean,
    ): BottomNavigationViewV140 {
        if (null == vp2) {
            return this
        }
        bnvHelper.setupViewPagerHelper(VP2Helper(vp2, smoothScroll))
        return this
    }

    override fun setBNItemViewBackgroundRes(position: Int, background: Int): BottomNavigationViewV140 {
        getBNItemView(position)?.setItemBackground(background)
        return this
    }

    override fun setIconTintList(tint: ColorStateList?): BottomNavigationViewV140 {
        theBottomNavigationMenuView.iconTintList = tint
        return this
    }

    override fun setIconTintList(position: Int, tint: ColorStateList?): BottomNavigationViewV140 {
        getBNItemView(position)?.setIconTintList(tint)
        return this
    }

    override fun setTextTintList(tint: ColorStateList?): BottomNavigationViewV140 {
        theBottomNavigationMenuView.itemTextColor = tint
        return this
    }

    override fun setTextTintList(position: Int, tint: ColorStateList?): BottomNavigationViewV140 {
        getBNItemView(position)?.setTextColor(tint)
        return this
    }

    override fun setIconsMarginTop(marginTop: Int): BottomNavigationViewV140 {
        for (i in 0 until getBNItemViewCount()) {
            setIconMarginTop(i, marginTop)
        }
        return this
    }

    override fun setIconMarginTop(position: Int, marginTop: Int): BottomNavigationViewV140 {
        if(getBNItemView(position).setField("defaultMargin", marginTop)){
            theBottomNavigationMenuView.updateMenuView()
        }
        return this
    }

    override fun setEmptyMenuIds(emptyMenuIds: List<Int>): BottomNavigationViewV140 {
        bnvHelper.emptyMenuIds = emptyMenuIds
        return this
    }

    override fun getMenuItems(): List<MenuItem>{
        val result = arrayListOf<MenuItem>()
        menu.forEachIndexed { index, item ->
            result.add(index, item)
        }
        return result.toList()
    }

    override fun restoreInstanceState(state: Parcelable?){
        onRestoreInstanceState(state)
    }

    override fun saveInstanceState(): Parcelable? {
        return onSaveInstanceState()
    }
    
    override fun setItemOnTouchListener(menuItem: MenuItem, onTouchListener: OnTouchListener) {
        super.setItemOnTouchListener(menuItem.itemId, onTouchListener)
    }
}