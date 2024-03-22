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
import android.view.ViewGroup
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
import com.yh.bottomnavigation_base.ext.dp2px
import com.yh.bottomnavigation_base.ext.getField
import com.yh.bottomnavigation_base.ext.setField
import com.yh.bottomnavigation_base.helper.BNVHelper
import com.yh.bottomnavigation_base.helper.VP2Helper
import com.yh.bottomnavigation_base.helper.VPHelper
import com.yh.bottomnavigation_base.internal.InnerListener
import kotlin.math.ceil
import kotlin.math.max

@SuppressLint("RestrictedApi")
class BottomNavigationViewV19x : BottomNavigationView,
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
    
    override fun getSuggestedMinimumHeight(): Int {
        return context.dp2px(56F)
    }
    
    override fun setLayoutParams(params: ViewGroup.LayoutParams?) {
        super.setLayoutParams(params)
        itemHeight = layoutParams.height + paddingTop + paddingBottom
    }
    
    override fun setIconVisibility(visibility: Boolean): BottomNavigationViewV19x {
        for(b in theBottomNavigationItemViews) {
            val icon: ImageView = b.getField("icon")
            icon.visibility = if(visibility) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        }
        if(!visibility) {
            if(!visibilityHeightRecord) {
                visibilityHeightRecord = true
                itemHeight = getBNMenuViewHeight()
            }
            
            val button = theBottomNavigationItemViews.firstOrNull()
            if(null != button) {
                val icon: ImageView = button.getField("icon")
                icon.post {
                    setBNMenuViewHeight(itemHeight - icon.measuredHeight)
                }
            }
        } else {
            if(!visibilityHeightRecord) {
                return this
            }
            setBNMenuViewHeight(itemHeight)
        }
        return this
    }
    
    override fun setTextVisibility(visibility: Boolean): BottomNavigationViewV19x {
        this.textVisibility = visibility
        
        for(b in theBottomNavigationItemViews) {
            val largeLabel: TextView = b.getField("largeLabel")
            val smallLabel: TextView = b.getField("smallLabel")
            
            if(visibility) {
                // if not record the font size, we need do nothing.
                if(!labelSizeRecord) {
                    return this
                }
                
                // restore it
                largeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, largeLabelSize)
                smallLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallLabelSize)
            } else {
                // if not record the font size, record it
                if(!labelSizeRecord) {
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
        if(visibility) {
            // if not record the mItemHeight, we need do nothing.
            if(!visibilityHeightRecord) {
                return this
            }
            // restore mItemHeight
            setBNMenuViewHeight(itemHeight)
        } else {
            // if not record mItemHeight
            if(!visibilityHeightRecord) {
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
    
    override fun enableAnimation(enable: Boolean): BottomNavigationViewV19x {
        for(b in theBottomNavigationItemViews) {
            val largeLabel: TextView = b.getField("largeLabel")
            val smallLabel: TextView = b.getField("smallLabel")
            
            if(!enable) {
                if(!labelSizeRecord) {
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
                if(!labelSizeRecord) {
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
    
    override fun enableLabelVisibility(enable: Boolean): BottomNavigationViewV19x {
        labelVisibilityMode = if(enable) LABEL_VISIBILITY_SELECTED else LABEL_VISIBILITY_LABELED
        return this
    }
    
    override fun enableBNItemViewLabelVisibility(position: Int, enable: Boolean): BottomNavigationViewV19x {
        getBNItemView(position)?.setShifting(enable)
        return this
    }
    
    override fun enableItemHorizontalTranslation(enable: Boolean): BottomNavigationViewV19x {
        isItemHorizontalTranslationEnabled = enable
        return this
    }
    
    override fun getCurrentIndex(): Int {
        menu.forEachIndexed { index, item ->
            if(item.isChecked) {
                return index
            }
        }
        return -1
    }
    
    override fun menuItemPositionAt(item: MenuItem): Int {
        menu.forEachIndexed { index, m ->
            if(m.itemId == item.itemId) {
                return index
            }
        }
        return -1
    }
    
    override fun setCurrentItem(index: Int): BottomNavigationViewV19x {
        val target = menu.getItem(index)
        if(bnvHelper.emptyMenuIds.contains(target.itemId)) {
            if(index >= menu.size()) {
                return this
            }
            for(pos in index + 1 until menu.size()) {
                val m = menu.getItem(pos)
                if(!bnvHelper.emptyMenuIds.contains(m.itemId)) {
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
    
    override fun setMenuListener(menuListener: IMenuListener): BottomNavigationViewV19x {
        bnvHelper.setListener(menuListener)
        return this
    }
    
    override fun setMenuDoubleClickListener(menuDoubleClickListener: IMenuDoubleClickListener): BottomNavigationViewV19x {
        bnvHelper.setMenuDoubleClickListener(menuDoubleClickListener)
        return this
    }
    
    override fun setInnerListener(listener: InnerListener) {
        this.innerListener = listener
    }
    
    override fun getBNMenuView(): BottomNavigationMenuView {
        return theBottomNavigationMenuView
    }
    
    override fun clearIconTintColor(): BottomNavigationViewV19x {
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
    
    override fun setSmallTextSize(sp: Float): BottomNavigationViewV19x {
        val count = getBNItemViewCount()
        for(i in 0 until count) {
            getSmallLabelAt(i)?.textSize = sp
        }
        theBottomNavigationMenuView.updateMenuView()
        return this
    }
    
    override fun setLargeTextSize(sp: Float): BottomNavigationViewV19x {
        val count = getBNItemViewCount()
        for(i in 0 until count) {
            getLargeLabelAt(i)?.textSize = sp
        }
        theBottomNavigationMenuView.updateMenuView()
        return this
    }
    
    override fun setTextSize(sp: Float): BottomNavigationViewV19x {
        setLargeTextSize(sp)
        setSmallTextSize(sp)
        return this
    }
    
    override fun setIconSizeAt(
        position: Int,
        width: Float,
        height: Float
    ): BottomNavigationViewV19x {
        val icon = getIconAt(position)
            ?: return this
        // update size
        val layoutParams = icon.layoutParams
        layoutParams.width = context.dp2px(width)
        layoutParams.height = context.dp2px(height)
        icon.layoutParams = layoutParams
        
        theBottomNavigationMenuView.updateMenuView()
        return this
    }
    
    override fun setIconSize(width: Float, height: Float): BottomNavigationViewV19x {
        val count = getBNItemViewCount()
        for(i in 0 until count) {
            setIconSizeAt(i, width, height)
        }
        return this
    }
    
    override fun setIconSize(dpSize: Float): BottomNavigationViewV19x {
        itemIconSize = context.dp2px(dpSize)
        return this
    }
    
    override fun setBNMenuViewHeight(height: Int): BottomNavigationViewV19x {
        val lp = layoutParams
        lp.height = max(height, -2)
        layoutParams = lp
        requestLayout()
        theBottomNavigationMenuView.requestLayout()
        theBottomNavigationMenuView.updateMenuView()
        itemHeight = lp.height
        return this
    }
    
    override fun getBNMenuViewHeight(): Int {
        return itemHeight
    }
    
    override fun setTypeface(typeface: Typeface, style: Int): BottomNavigationViewV19x {
        val count = getBNItemViewCount()
        for(i in 0 until count) {
            getLargeLabelAt(i)?.setTypeface(typeface, style)
            getSmallLabelAt(i)?.setTypeface(typeface, style)
        }
        theBottomNavigationMenuView.updateMenuView()
        return this
    }
    
    override fun setTypeface(typeface: Typeface): BottomNavigationViewV19x {
        val count = getBNItemViewCount()
        for(i in 0 until count) {
            getLargeLabelAt(i)?.typeface = typeface
            getSmallLabelAt(i)?.typeface = typeface
        }
        theBottomNavigationMenuView.updateMenuView()
        return this
    }
    
    override fun setupWithViewPager(
        vp: ViewPager?
    ): BottomNavigationViewV19x {
        return setupWithViewPager(vp, false)
    }
    
    override fun setupWithViewPager(
        vp: ViewPager?,
        smoothScroll: Boolean
    ): BottomNavigationViewV19x {
        if(null == vp) {
            return this
        }
        bnvHelper.setupViewPagerHelper(VPHelper(vp, smoothScroll))
        return this
    }
    
    override fun setupWithViewPager2(
        vp2: ViewPager2?
    ): BottomNavigationViewV19x {
        return setupWithViewPager2(vp2, false)
    }
    
    override fun setupWithViewPager2(
        vp2: ViewPager2?,
        smoothScroll: Boolean,
    ): BottomNavigationViewV19x {
        if(null == vp2) {
            return this
        }
        bnvHelper.setupViewPagerHelper(VP2Helper(vp2, smoothScroll))
        return this
    }
    
    override fun setBNItemViewBackgroundRes(position: Int, background: Int): BottomNavigationViewV19x {
        getBNItemView(position)?.setItemBackground(background)
        return this
    }
    
    override fun setIconTintList(tint: ColorStateList?): BottomNavigationViewV19x {
        theBottomNavigationMenuView.iconTintList = tint
        return this
    }
    
    override fun setIconTintList(position: Int, tint: ColorStateList?): BottomNavigationViewV19x {
        getBNItemView(position)?.setIconTintList(tint)
        return this
    }
    
    override fun setTextTintList(tint: ColorStateList?): BottomNavigationViewV19x {
        theBottomNavigationMenuView.itemTextColor = tint
        return this
    }
    
    override fun setTextTintList(position: Int, tint: ColorStateList?): BottomNavigationViewV19x {
        getBNItemView(position)?.setTextColor(tint)
        return this
    }
    
    override fun setIconsMarginTop(marginTop: Int): BottomNavigationViewV19x {
        for(i in 0 until getBNItemViewCount()) {
            setIconMarginTop(i, marginTop)
        }
        return this
    }
    
    override fun setIconMarginTop(position: Int, marginTop: Int): BottomNavigationViewV19x {
        if(getBNItemView(position).setField("itemPaddingTop", marginTop)) {
            theBottomNavigationMenuView.updateMenuView()
        }
        return this
    }
    
    override fun setEmptyMenuIds(emptyMenuIds: List<Int>): BottomNavigationViewV19x {
        bnvHelper.emptyMenuIds = emptyMenuIds
        return this
    }
    
    override fun getMenuItems(): List<MenuItem> {
        val result = arrayListOf<MenuItem>()
        menu.forEachIndexed { index, item ->
            result.add(index, item)
        }
        return result.toList()
    }
    
    override fun restoreInstanceState(state: Parcelable?) {
        onRestoreInstanceState(state)
    }
    
    override fun saveInstanceState(): Parcelable? {
        return onSaveInstanceState()
    }
    
    override fun setItemOnTouchListener(menuItem: MenuItem, onTouchListener: OnTouchListener) {
        super.setItemOnTouchListener(menuItem.itemId, onTouchListener)
    }
}