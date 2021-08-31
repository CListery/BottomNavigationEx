package com.clistery.src

import org.gradle.api.artifacts.dsl.DependencyHandler

object AppDependencies {
    
    object kotlin {
        
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${AppVersion.kotlin.version}"
        const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${AppVersion.kotlin.version}"
    }
    
    object dokka {
        
        const val plugin = "org.jetbrains.dokka:dokka-gradle-plugin:${AppVersion.dokka.version}"
    }
    
    object jfrog {
        
        const val buildInfo = "org.jfrog.buildinfo:build-info-extractor-gradle:4.23.4"
    }
    
    object androidx {
        
        const val coreKtx = "androidx.core:core-ktx:${AppVersion.androidx.coreKtx}"
        const val appcompat = "androidx.appcompat:appcompat:${AppVersion.androidx.appcompat}"
        const val viewbinding = "androidx.databinding:viewbinding:4.2.1"
        const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:2.3.1"
        const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1"
    }
    
    object google {
        
        const val material = "com.google.android.material:material:${AppVersion.google.material}"
    }
    
    val baseLibs: ArrayList<String>
        get() = arrayListOf(
            kotlin.stdlib,
            androidx.coreKtx,
            androidx.appcompat,
        )
    
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}
