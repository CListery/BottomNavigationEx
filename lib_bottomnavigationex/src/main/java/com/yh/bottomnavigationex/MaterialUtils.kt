package com.yh.bottomnavigationex

import android.content.Context
import android.util.Log
import java.util.zip.ZipFile

internal object MaterialUtils {
    
    private var appSourceDir: String = ""
    
    @JvmStatic
    fun init(ctx: Context) {
        if (appSourceDir.isEmpty()) {
            appSourceDir = ctx.applicationInfo.sourceDir
            Log.i("BNEx", "material: ${version.real}")
        }
    }
    
    internal val version: MaterialVersion by lazy {
        if (appSourceDir.isEmpty()) {
            throw NullPointerException("appSourceDir not setup!")
        }
        val zipFile = ZipFile(appSourceDir)
        val entry = zipFile.entries().toList()
            .find { it.name == "META-INF/com.google.android.material_material.version" }!!
        val version = zipFile.getInputStream(entry).bufferedReader().use { it.readLine() }!!
        MaterialVersion.values().find { version.startsWith(it.versionPrefix) }!!.apply {
            real = version
        }
    }
    
    internal enum class MaterialVersion(val versionPrefix: String) {
        V_1_3_X("1.3."),
        V_1_4_X("1.4."),
        V_1_5_X("1.5."),
        V_1_6_X("1.6."),
        V_1_7_X("1.7."),
        V_1_8_X("1.8."),
        V_1_9_X("1.9."),
        ;
        
        var real: String = ""
    }
    
}
