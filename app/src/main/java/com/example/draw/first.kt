package com.example.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

class first(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint()
    private val lines = mutableListOf<Pair<Float, Float>>()

    init {
        paint.isAntiAlias = true
        paint.color = Color.BLUE
        paint.strokeWidth = 20F
    }

    private var xp1 = 0.0F
    private var yp1 = 0.0F
    private var xp2 = 0.0F
    private var yp2 = 0.0F

    //    fun drawLine(x1: Float, y1: Float, x2: Float, y2: Float) {
//        this.xp1=x1
//        this.yp1=y1
//        this.xp2=x2
//        this.yp2=y2
//        invalidate()
//    }
    fun addLine(x1: Float, y1: Float, x2: Float, y2: Float) {
        lines.add(Pair(x1, y1))
        lines.add(Pair(x2, y2))
        Log.e("11","$x1")
        Log.e("12","$y1")
        Log.e("13","$x2")
        Log.e("14","$y2")
        invalidate()

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0 until lines.size step 2) {
            val start = lines[i]
            val end = lines[i + 1]
            Log.e("21","${start.first}")
            Log.e("22","${end.first}")
            Log.e("23","${start.second}")
            Log.e("24","${end.second}")
            canvas.drawLine(start.first,  start.second,end.first, end.second, paint)
        }
//        canvas.drawLine(xp1, yp1, xp2, yp2, paint)
    }

}



