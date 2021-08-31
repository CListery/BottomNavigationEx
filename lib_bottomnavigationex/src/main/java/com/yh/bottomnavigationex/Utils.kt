package com.yh.bottomnavigationex

import android.content.Context
import java.util.zip.ZipFile

object Utils {

    @JvmStatic
    fun init(ctx:Context){
        appSourceDir = ctx.applicationInfo.sourceDir
        println(materialVersion)
    }

    @JvmField
    internal var appSourceDir: String = ""

    internal val materialVersion: String? by lazy {
        if (appSourceDir.isEmpty()) {
            throw NullPointerException("appSourceDir not setup!")
        }
        try {
            val zipFile = ZipFile(appSourceDir)
            val entry = zipFile.entries().toList()
                .find { it.name == "META-INF/com.google.android.material_material.version" }
            if (null != entry) {
                return@lazy zipFile.getInputStream(entry)?.bufferedReader()?.use {
                    return@use it.readLine()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        null
    }

}
