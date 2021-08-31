package com.yh.bottomnavigationex

import com.google.android.material.bottomnavigation.BottomNavigationView
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun loadVersion() {
        BottomNavigationView::class.java.getResourceAsStream("/META-INF/com.google.android.material_material.version")
            ?.bufferedReader()?.useLines {
                println(it.joinToString("\n"))
            }
    }
}