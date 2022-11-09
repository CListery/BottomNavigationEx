package com.yh.bottomnavigation_base.ext

import android.content.Context


/**
 * dp to px
 *
 * @param context
 * @param dpValue dp
 * @return px
 */
fun Context.dp2px(dpValue: Number): Int {
    val scale = resources.displayMetrics.density
    return (dpValue.toFloat() * scale + 0.5f).toInt()
}
