package com.yh.bottomnavigationex.demo.features

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.onClick
import com.yh.appbasic.ui.ViewBindingActivity
import com.yh.bottomnavigationex.demo.databinding.ActivityMainBinding
import com.yh.bottomnavigationex.demo.features.badgeview.BadgeViewActivity
import com.yh.bottomnavigationex.demo.features.centerfab.CenterFabActivity
import com.yh.bottomnavigationex.demo.features.dynamic.DynamicMenuAct
import com.yh.bottomnavigationex.demo.features.example.ExampleAct
import com.yh.bottomnavigationex.demo.features.setupwithviewpager.SetupWithViewPagerActivity
import com.yh.bottomnavigationex.demo.features.style.StyleActivity
import com.yh.bottomnavigationex.demo.features.viewpager.WithViewPager2Act
import com.yh.bottomnavigationex.demo.features.viewpager.WithViewPagerActivity
import java.util.zip.ZipFile

class MainActivity : ViewBindingActivity<ActivityMainBinding>() {
    
    private val version: String by lazy {
        val zipFile = ZipFile(applicationInfo.sourceDir)
        val entry = zipFile.entries().toList()
            .find { it.name == "META-INF/com.google.android.material_material.version" }!!
        zipFile.getInputStream(entry).bufferedReader().use { it.readLine() }!!
    }
    
    override fun binderCreator(savedInstanceState: Bundle?) = ActivityMainBinding.inflate(layoutInflater)
    
    override fun ActivityMainBinding.onInit(savedInstanceState: Bundle?) {
        txtVersion.text = "material: $version"
        
        btnExample.openAct<ExampleAct>()
        btnStyle.openAct<StyleActivity>()
        btnWithViewPager.openAct<WithViewPagerActivity>()
        btnSetupWithViewPager.openAct<SetupWithViewPagerActivity>()
        btnSetupWithViewPager2.openAct<WithViewPager2Act>()
        btnBadgeView.openAct<BadgeViewActivity>()
        btnCenterFab.openAct<CenterFabActivity>()
        btnChangeableSize.openAct<DynamicMenuAct>()
    }
    
    private inline fun <reified T> View.openAct() {
        onClick { startActivity(Intent(this@MainActivity, T::class.java)) }
    }
    
}
