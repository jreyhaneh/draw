package com.example.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class DrawLine(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint()
    private val matrix = Matrix()
    private var point = Paint()
    private var canvas = Canvas()
    private var finalDegree: List<Float>? = null
    private var finalText: List<String>? = null
    private val lines = mutableListOf<Pair<Float, Float>>()

    init {
        paint.isAntiAlias = true
        paint.color = Color.WHITE
        paint.strokeWidth = 20F

        point.color=Color.RED
        point.style=Paint.Style.FILL
    }

    fun deleteLine(): Pair<Float, Float> {
        var delete: Pair<Float, Float>? = null
        for (i in 1..2) {
            delete = lines.last()
            lines.removeLast()

        }
        invalidate()
        return delete!!
    }

    fun addLine(
        x1: Float,
        y1: Float,
        x2: Float,
        y2: Float,
        degree: List<Float>,
        text: List<String>
    ) {
        lines.add(Pair(x1, y1))
        lines.add(Pair(x2, y2))

        finalDegree = degree
        finalText = text

        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.textSize = 45f
        var counter = 0

        for (i in 0 until lines.size step 2) {
            val start = lines[i]
            val end = lines[i + 1]

            canvas.drawLine(start.first, start.second, end.first, end.second, paint)
            canvas.drawCircle(start.first,start.second,20F,point)
            canvas.drawCircle(end.first,end.second,20F,point)

            val xPos = ((start.first + end.first) / 2)
            val yPos = ((start.second + end.second) / 2)
            if (end.first < start.first)
                matrix.setRotate(180+(finalDegree?.get(counter) ?: 0F), xPos, yPos)
            else
                matrix.setRotate(finalDegree?.get(counter) ?: 0F, xPos, yPos)

            canvas.save()
            canvas.concat(matrix)

            canvas.drawText(
                finalText?.get(counter)!!,
                xPos - 20,
                yPos - 20,
                paint
            )
            counter++

            canvas.restore()
        }

    }
//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        val x = event.x
//        val y = event.y
//
//        when (event.action) {
//            MotionEvent.ACTION_DOWN -> {
//                // شروع رسم خط جدید
//                path.reset()
//                path.moveTo(x, y)
//                return true
//            }
//            MotionEvent.ACTION_MOVE -> {
//                // ادامه رسم خط
//                path.lineTo(x, y)
//                invalidate()
//                return true
//            }
//            MotionEvent.ACTION_UP -> {
//                // پایان رسم خط
//                path.lineTo(x, y)
//                invalidate()
//                return true
//            }
//        }
//        return super.onTouchEvent(event)
//    }


}
