package com.example.draw

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.graphics.Matrix
import android.view.ScaleGestureDetector
import android.widget.FrameLayout
class Zoomable(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    private var scaleGestureDetector: ScaleGestureDetector? = null
    private var matrix: Matrix? = null

    init {
        scaleGestureDetector = ScaleGestureDetector(context, ScaleListener())
        matrix = Matrix()
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        super.dispatchTouchEvent(event)
        return event?.let { scaleGestureDetector?.onTouchEvent(it) } ?: false
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return true
    }

    override fun dispatchDraw(canvas: Canvas) {
        canvas.save()
        canvas.concat(matrix)
        super.dispatchDraw(canvas)
        canvas.restore()
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val scaleFactor = detector.scaleFactor
            matrix?.postScale(scaleFactor, scaleFactor, detector.focusX, detector.focusY)
            invalidate()
            return true
        }
    }
}
