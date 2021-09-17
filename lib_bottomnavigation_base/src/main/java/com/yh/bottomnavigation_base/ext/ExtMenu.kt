package com.yh.bottomnavigation_base.ext

import android.view.Menu
import android.view.MenuItem
import androidx.core.view.forEachIndexed


/**
 * 重新计算 index,忽略 emptyMenuIds 所占的 index
 */
fun Menu.filterEmptyMenuIndex(menuItem: MenuItem, emptyMenuIds: List<Int>): Int {
    return indexOf(menuItem) - emptyCountBeforeMenuItem(menuItem, emptyMenuIds)
}

/**
 * 计算 MenuItem 在 Menu 中的下标
 */
fun Menu.indexOf(menuItem: MenuItem): Int {
    forEachIndexed { index, item ->
        if (item.itemId == menuItem.itemId) {
            return index
        }
    }
    return -1
}

/**
 * 获取在该 MenuItem 之前的 emptyId 数量
 */
fun Menu.emptyCountBeforeMenuItem(item: MenuItem, emptyMenuIds: List<Int>): Int {
    if (emptyMenuIds.isEmpty()) {
        return 0
    } else {
        var count = 0
        var index = indexOf(item)
        forEachIndexed { i, m ->
            if (i < index) {
                if (emptyMenuIds.contains(m.itemId)) {
                    count++
                }
            } else if (i == index) {
                if (emptyMenuIds.contains(m.itemId)) {
                    count++
                    index++
                }
            } else {
                return count
            }
        }
        return count
    }
}