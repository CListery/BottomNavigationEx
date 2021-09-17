package com.yh.bottomnavigation_base.ext

import android.annotation.SuppressLint
import android.view.GestureDetector
import android.view.View
import androidx.annotation.RestrictTo
import com.yh.bottomnavigation_base.gesture.OnDoubleClickListener

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
@JvmOverloads
@SuppressLint("ClickableViewAccessibility")
internal fun <T : View> T.onDoubleClick(enableAll: Boolean = true, onDoubleClick: () -> Unit) {
    val gestureDetector = GestureDetector(context, OnDoubleClickListener(enableAll, this, onDoubleClick))
    setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
}
