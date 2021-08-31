package com.google.android.material.bottomnavigation

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Paint
import android.graphics.Typeface
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
import com.google.android.material.navigation.NavigationBarMenuView
import com.kotlin.safeFieldGet
import com.kotlin.safeFieldSet
import com.yh.bottomnavigation_base.IBottomNavigationEx
import com.yh.bottomnavigation_base.IListener
import com.yh.bottomnavigation_base.helper.BNVHelper
import com.yh.bottomnavigation_base.helper.VP2Helper
import com.yh.bottomnavigation_base.helper.VPHelper
import com.yh.bottomnavigation_base.internal.InnerListener
import kotlin.math.ceil

@SuppressLint("RestrictedApi")
class BottomNavigationViewV140 : BottomNavigationView,
    IBottomNavigationEx<BottomNavigationMenuView, BottomNavigationItemView> {

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
    private val theBottomNavigationItemViews by lazy {
        NavigationBarMenuView::class.java.safeFieldGet<Array<NavigationBarItemView>>(
            "buttons",
            menuView
        )?.filterIsInstance<BottomNavigationItemView>()?.toTypedArray()!!
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

    override fun setIconVisibility(visibility: Boolean): BottomNavigationViewV140 {
        for (b in theBottomNavigationItemViews) {
            val icon: ImageView? = getButtonField(b, "icon")
            icon?.visibility = if (visibility) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        }
        if (!visibility) {
            if (!visibilityHeightRecord) {
                visibilityHeightRecord = true
                itemHeight = getItemHeight()
            }

            val button = theBottomNavigationItemViews.firstOrNull()
            if (null != button) {
                val icon: ImageView? = getButtonField(button, "icon")
                icon?.post {
                    setItemHeight(itemHeight - icon.measuredHeight)
                }
            }
        } else {
            if (!visibilityHeightRecord) {
                return this
            }
            setItemHeight(itemHeight)
        }
        return this
    }

    override fun setTextVisibility(visibility: Boolean): BottomNavigationViewV140 {
        this.textVisibility = visibility

        for (b in theBottomNavigationItemViews) {
            val largeLabel: TextView = getButtonField(b, "largeLabel")!!
            val smallLabel: TextView = getButtonField(b, "smallLabel")!!

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
            setItemHeight(itemHeight)
        } else {
            // if not record mItemHeight
            if (!visibilityHeightRecord) {
                itemHeight = getItemHeight()
                visibilityHeightRecord = true
            }

            // change mItemHeight to only icon size in menuView
            // private final int mItemHeight;
            setItemHeight(itemHeight - getFontHeight(smallLabelSize))
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
            val largeLabel: TextView = getButtonField(b, "largeLabel")!!
            val smallLabel: TextView = getButtonField(b, "smallLabel")!!

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

    private fun <T> getButtonField(button: BottomNavigationItemView?, fieldName: String): T? {
        if (null == button) {
            return null
        }
        @Suppress("UNCHECKED_CAST")
        return NavigationBarItemView::class.java.safeFieldGet<Any>(fieldName, button) as? T
    }

    override fun enableShiftingMode(enable: Boolean): BottomNavigationViewV140 {
        labelVisibilityMode = if (enable) LABEL_VISIBILITY_SELECTED else LABEL_VISIBILITY_LABELED
        return this
    }

    override fun enableShiftingMode(position: Int, enable: Boolean): BottomNavigationViewV140 {
        getBottomNavigationItemView(position)?.setShifting(enable)
        return this
    }

    override fun enableItemShiftingMode(enable: Boolean): BottomNavigationViewV140 {
        isItemHorizontalTranslationEnabled = enable
        return this
    }

    override fun getCurrentItem(): Int {
        menu.forEachIndexed { index, item ->
            if (item.isChecked) {
                return index
            }
        }
        return -1
    }

    override fun getMenuItemPosition(item: MenuItem): Int {
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

    override fun getListener(): IListener? {
        return bnvHelper.getListener()
    }

    override fun setListener(listener: IListener) {
        bnvHelper.setListener(listener)
    }

    override fun setInnerListener(listener: InnerListener) {
        this.innerListener = listener
    }

    override fun getBottomNavigationMenuView(): BottomNavigationMenuView {
        return theBottomNavigationMenuView
    }

    override fun clearIconTintColor(): BottomNavigationViewV140 {
        theBottomNavigationMenuView.iconTintList = null
        return this
    }

    override fun getBottomNavigationItemViews(): Array<BottomNavigationItemView> {
        return theBottomNavigationItemViews
    }

    override fun getBottomNavigationItemView(position: Int): BottomNavigationItemView? {
        return theBottomNavigationItemViews.getOrNull(position)
    }

    override fun getIconAt(position: Int): ImageView? {
        val button = getBottomNavigationItemView(position)
        return getButtonField(button, "icon")
    }

    override fun getSmallLabelAt(position: Int): TextView? {
        val button = getBottomNavigationItemView(position)
        return getButtonField(button, "smallLabel")
    }

    override fun getLargeLabelAt(position: Int): TextView? {
        val button = getBottomNavigationItemView(position)
        return getButtonField(button, "largeLabel")
    }

    override fun getItemCount(): Int {
        return theBottomNavigationItemViews.size
    }

    override fun setSmallTextSize(sp: Float): BottomNavigationViewV140 {
        val count = getItemCount()
        for (i in 0 until count) {
            getSmallLabelAt(i)?.textSize = sp
        }
        theBottomNavigationMenuView.updateMenuView()
        return this
    }

    override fun setLargeTextSize(sp: Float): BottomNavigationViewV140 {
        val count = getItemCount()
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
        val count = getItemCount()
        for (i in 0 until count) {
            setIconSizeAt(i, width, height)
        }
        return this
    }

    override fun setIconSize(dpSize: Float): BottomNavigationViewV140 {
        itemIconSize = dp2px(context, dpSize)
        return this
    }

    override fun setItemHeight(height: Int): BottomNavigationViewV140 {
        BottomNavigationMenuView::class.java.safeFieldSet(
            "itemHeight",
            theBottomNavigationMenuView,
            height
        )
        theBottomNavigationMenuView.updateMenuView()
        return this
    }

    override fun getItemHeight(): Int {
        return BottomNavigationMenuView::class.java.safeFieldGet(
            "itemHeight",
            theBottomNavigationMenuView
        ) ?: 0
    }

    override fun setTypeface(typeface: Typeface, style: Int): BottomNavigationViewV140 {
        val count = getItemCount()
        for (i in 0 until count) {
            getLargeLabelAt(i)?.setTypeface(typeface, style)
            getSmallLabelAt(i)?.setTypeface(typeface, style)
        }
        theBottomNavigationMenuView.updateMenuView()
        return this
    }

    override fun setTypeface(typeface: Typeface): BottomNavigationViewV140 {
        val count = getItemCount()
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

    override fun setItemBackground(position: Int, background: Int): BottomNavigationViewV140 {
        getBottomNavigationItemView(position)?.setItemBackground(background)
        return this
    }

    override fun setIconTintList(tint: ColorStateList?): IBottomNavigationEx<BottomNavigationMenuView, BottomNavigationItemView> {
        theBottomNavigationMenuView.iconTintList = tint
        return this
    }

    override fun setIconTintList(position: Int, tint: ColorStateList?): BottomNavigationViewV140 {
        getBottomNavigationItemView(position)?.setIconTintList(tint)
        return this
    }

    override fun setTextTintList(tint: ColorStateList?): IBottomNavigationEx<BottomNavigationMenuView, BottomNavigationItemView> {
        theBottomNavigationMenuView.itemTextColor = tint
        return this
    }

    override fun setTextTintList(position: Int, tint: ColorStateList?): BottomNavigationViewV140 {
        getBottomNavigationItemView(position)?.setTextColor(tint)
        return this
    }

    override fun setIconsMarginTop(marginTop: Int): BottomNavigationViewV140 {
        for (i in 0 until getItemCount()) {
            setIconMarginTop(i, marginTop)
        }
        return this
    }

    override fun setIconMarginTop(position: Int, marginTop: Int): BottomNavigationViewV140 {
        val itemView = getBottomNavigationItemView(position)
        NavigationBarItemView::class.java.safeFieldSet("defaultMargin", itemView, marginTop)
        theBottomNavigationMenuView.updateMenuView()
        return this
    }

    override fun setEmptyMenuIds(emptyMenuIds: List<Int>): IBottomNavigationEx<BottomNavigationMenuView, BottomNavigationItemView> {
        bnvHelper.emptyMenuIds = emptyMenuIds
        return this
    }

    override fun getMenuList(): List<MenuItem>{
        val result = arrayListOf<MenuItem>()
        menu.forEachIndexed { index, item ->
            result.add(index, item)
        }
        return result.toList()
    }

}