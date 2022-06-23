package com.yh.bottomnavigation_base.gesture

import android.os.Build
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RestrictTo

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
open class BaseGestureListener(protected val enableAll: Boolean, protected val view: View) :
    GestureDetector.SimpleOnGestureListener() {

    override fun onDown(e: MotionEvent?): Boolean = enableAll

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        if (enableAll) {
            view.performClick()
        }
        return enableAll
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean = enableAll

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean = enableAll

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean = enableAll

    override fun onDoubleTap(e: MotionEvent?): Boolean = enableAll

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean = enableAll

    override fun onContextClick(e: MotionEvent?): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && view.isContextClickable) {
            if (enableAll) {
                if (null != e && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    view.performContextClick(e.x, e.y)
                } else {
                    view.performContextClick()
                }
            }
        }
        return enableAll
    }

    override fun onLongPress(e: MotionEvent?) {
        if (enableAll && view.isLongClickable) {
            if (null != e && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                view.performLongClick(e.x, e.y)
            } else {
                view.performLongClick()
            }
        }
    }
}