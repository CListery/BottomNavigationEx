package com.google.android.material.bottomnavigation

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.view.menu.MenuBuilder
import com.yh.bottomnavigation_base.ext.getFieldValue
import com.yh.bottomnavigation_base.ext.setFieldValue
import kotlin.math.max
import kotlin.math.min

@SuppressLint("RestrictedApi")
class BottomNavigationMenuView2 : BottomNavigationMenuView {
    
    private var itemHeight = 0
    private var menu: MenuBuilder? = null
    
    private val tempChildWidths = ArrayList<Int>()
    
    constructor(context: Context) : super(context)
    
    @Suppress("UsePropertyAccessSyntax")
    constructor(context: Context, originMenuView: BottomNavigationMenuView) : this(context) {
        setPresenter(originMenuView.getFieldValue("presenter"))
        itemHeight = originMenuView.getFieldValue("itemHeight")
        setFieldValue("itemHeight", itemHeight)
        setIconTintList(originMenuView.iconTintList)
        setItemIconSize(originMenuView.itemIconSize)
        setItemTextColor(originMenuView.itemTextColor)
        setItemTextAppearanceInactive(originMenuView.itemTextAppearanceInactive)
        setItemTextAppearanceActive(originMenuView.itemTextAppearanceActive)
        setItemBackgroundRes(originMenuView.itemBackgroundRes)
        setItemBackground(originMenuView.itemBackground)
        setLabelVisibilityMode(originMenuView.labelVisibilityMode)
        setItemHorizontalTranslationEnabled(originMenuView.isItemHorizontalTranslationEnabled)
    }
    
    override fun initialize(menu: MenuBuilder) {
        super.initialize(menu)
        this.menu = menu
    }
    
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        
        val totalCount = childCount
        
        tempChildWidths.clear()
        
        val heightSpec = MeasureSpec.makeMeasureSpec(itemHeight, MeasureSpec.EXACTLY)
        
        val activeItemMaxWidth: Int = getFieldValue("activeItemMaxWidth")
        
        var totalWidth = 0
        val itemWidth = if(totalCount > BottomNavigationView.MAX_ITEM_COUNT) {
            min(activeItemMaxWidth, width / BottomNavigationView.MAX_ITEM_COUNT)
        } else if(totalCount <= 1) {
            width
        } else {
            width / totalCount
        }
        for(i in 0 until totalCount) {
            val child = getChildAt(i)
            if(child.visibility == GONE) {
                continue
            }
            child.measure(
                MeasureSpec.makeMeasureSpec(itemWidth, MeasureSpec.EXACTLY), heightSpec
            )
            val params = child.layoutParams
            params.width = child.measuredWidth
            totalWidth += child.measuredWidth
        }
        
        setMeasuredDimension(
            resolveSizeAndState(
                totalWidth, MeasureSpec.makeMeasureSpec(totalWidth, MeasureSpec.UNSPECIFIED), 0
            ),
            resolveSizeAndState(itemHeight, heightSpec, 0)
        )
    }
}