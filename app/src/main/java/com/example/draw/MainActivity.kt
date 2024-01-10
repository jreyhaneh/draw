package com.example.draw

import android.R.attr
import android.content.ClipData
import android.content.ServiceConnection
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.draw.databinding.ActivityMainBinding
import kotlin.math.cos


class MainActivity : AppCompatActivity() {

    private var xPos1 = 70f
    private var yPos1 = 1000f
    private var xPos2 = 0.0f
    private var yPos2 = 0.0f
    private var lastDegree = -90F
    private var degreeList = mutableListOf<Float>()
    private var lengthList = mutableListOf<String>()


    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val myDraw = DrawLine(binding.butDraw.context, null)

        binding.linear.addView(myDraw)
        dragAndDrop()
//        dragAndDropPoint()

        binding.butDelete.setOnClickListener {

            degreeList.last()
            degreeList.removeLast()
            lastDegree = degreeList.last()

            lengthList.last()
            lengthList.removeLast()

            val deleteLine = myDraw.deleteLine()
            xPos1 = deleteLine.first
            yPos1 = deleteLine.second
        }

        binding.butDraw.setOnClickListener {

            val degree = binding.degree.text.toString().toFloat()
            val lengthInCm = binding.size.text.toString().toFloat()
            lengthList.add(lengthInCm.toInt().toString())
            val radians = Math.toRadians(lastDegree + degree.toDouble())
            lastDegree += degree
            degreeList.add(lastDegree)
            xPos2 = xPos1 + (lengthInCm * 100) * cos(radians).toFloat()
            yPos2 = yPos1 + (lengthInCm * 100) * kotlin.math.sin(radians).toFloat()
            myDraw.addLine(xPos1, yPos1, xPos2, yPos2, degreeList, lengthList)

            xPos1 = xPos2
            yPos1 = yPos2
            binding.degree.setText("0")

            binding.imageView3.translationX= 0F
            binding.imageView3.translationY= 0F
        }

        binding.butSouth.setOnClickListener {
            val degree = binding.south.text.toString().toFloat()
            binding.imageView.rotation = degree
//            val canvas=Canvas()     //رسم فلش جنوب
//            canvas.drawLine()

        }

        binding.imageView.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_MOVE) {
                v.y = event.rawY - v.height / 2
                v.x = event.rawX - v.width / 2
            }
            true
        }

        binding.imageView3.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_MOVE) {
                v.y = event.rawY - v.height / 2
                v.x = event.rawX - v.width / 2

                xPos1 = event.rawX
                yPos1 = event.rawY-250
                lastDegree = 0F
            }
            true
        }

    }

    private fun dragAndDrop() {

        binding.linear.setOnDragListener { v, event ->
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    true
                }

                DragEvent.ACTION_DRAG_ENTERED -> {
//                    binding.linear.setBackgroundColor(Color.GREEN)
                    true
                }

                DragEvent.ACTION_DRAG_EXITED -> {
                    true
                }

                DragEvent.ACTION_DROP -> {


                    val x = event.x - binding.imageView2.width / 2
                    val y = event.y - binding.imageView2.height / 2

                    val newImageView = ImageView(this)
                    newImageView.setImageResource(R.drawable.baseline_crop)

                    newImageView.translationX = x
                    newImageView.translationY = y


                    val draggedViewParent = binding.linear.parent as? ViewGroup
                    draggedViewParent?.removeView(newImageView)
                    binding.linear.addView(newImageView)

                    true
                }

                DragEvent.ACTION_DRAG_ENDED -> {
                    binding.imageView.visibility = View.VISIBLE

                    true
                }

                else -> {
                    false
                }
            }
        }
        binding.imageView2.setImageResource(R.drawable.baseline_crop)

        binding.imageView2.setOnLongClickListener { v ->
            val clipData = ClipData.newPlainText("", "")
            val shadowBuilder = View.DragShadowBuilder(v)
            v.startDragAndDrop(clipData, shadowBuilder, v, 0)
            true
        }
    }
}

