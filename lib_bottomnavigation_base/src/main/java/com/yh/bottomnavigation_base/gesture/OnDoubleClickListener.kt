package com.yh.bottomnavigation_base.gesture

import android.view.MotionEvent
import android.view.View
import androidx.annotation.RestrictTo

/**
 * Created by Clistery on 18-12-10.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
class OnDoubleClickListener @JvmOverloads constructor(
    enableAll: Boolean = true,
    view: View,
    private val onDoubleClick: () -> Unit
) : BaseGestureListener(enableAll, view) {

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        onDoubleClick.invoke()
        return true
    }

}
