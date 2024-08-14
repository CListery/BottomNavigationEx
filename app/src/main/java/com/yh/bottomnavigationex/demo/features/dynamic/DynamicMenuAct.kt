package com.yh.bottomnavigationex.demo.features.dynamic

import android.os.Bundle
import android.view.onClick
import com.yh.appbasic.logger.logD
import com.yh.appbasic.ui.ViewBindingActivity
import com.yh.bottomnavigationex.demo.R
import com.yh.bottomnavigationex.demo.databinding.ActDynamicMenuBinding
import kotlin.math.max

class DynamicMenuAct : ViewBindingActivity<ActDynamicMenuBinding>() {
    
    override fun binderCreator(savedInstanceState: Bundle?) = ActDynamicMenuBinding.inflate(layoutInflater)
    
    override fun ActDynamicMenuBinding.onInit(savedInstanceState: Bundle?) {
        dynamicConfigBNVE()
        btn.onClick {
            dynamicConfigBNVE()
        }
    }
    
    private fun ActDynamicMenuBinding.dynamicConfigBNVE() {
        var menuCount: Int
        do {
            menuCount = max((Math.random() * 30).toInt(), 1)
        } while(txtItemCount.tag == menuCount)
        txtItemCount.text = "item: $menuCount"
        txtItemCount.tag = menuCount
        
        bnve.configDynamic(menuCount) { menu, index ->
            menu.add(0, index, index, "menu${index}")
                .setIcon(R.drawable.ic_favorite_black_24dp)
        }
        
        logD("MenuMaxItemCount: ${bnve.getMenuMaxItemCount()}", tag = "dynamic")
    }
    
}