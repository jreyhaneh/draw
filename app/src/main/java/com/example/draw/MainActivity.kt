package com.example.draw

import android.content.ClipData
import android.content.ClipDescription
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.draw.databinding.ActivityMainBinding
import kotlin.math.cos
import android.graphics.Color
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView

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
        binding.root.addView(myDraw)
        dragAndDrop()
//
//        binding.imageView.setOnTouchListener { view, motionEvent ->
//
//            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
//                view.y = motionEvent.rawY - view.height / 2
//                view.x = motionEvent.rawX - view.width / 2
//            }
//            true
//        }
//
//        binding.imageView2.setOnTouchListener { view, motionEvent ->
//
//
//            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
//
//                view.y = motionEvent.rawY - view.height / 2
//                view.x = motionEvent.rawX - view.width / 2
//            }
//            true
//        }

        binding.butDelete.setOnClickListener {

            degreeList.last()
            degreeList.removeLast()
            lastDegree = degreeList.last()

            lengthList.last()
            lengthList.removeLast()
            Log.e("12", "$lengthList")
            Log.e("12", "${lengthList.size}")

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
            xPos2 = xPos1 + lengthInCm * cos(radians).toFloat()
            yPos2 = yPos1 + lengthInCm * kotlin.math.sin(radians).toFloat()
            myDraw.addLine(xPos1, yPos1, xPos2, yPos2, degreeList, lengthList)

            xPos1 = xPos2
            yPos1 = yPos2
            binding.degree.setText("0")

        }
    }


    private fun dragAndDrop() {
        binding.cardView.setOnDragListener { v, event ->

            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    true
                }

                DragEvent.ACTION_DRAG_ENTERED -> {
                    binding.cardView.setCardBackgroundColor(Color.GREEN)
                    true
                }

                DragEvent.ACTION_DRAG_EXITED -> {
                    binding.cardView.setCardBackgroundColor(Color.WHITE)
                    true
                }

                DragEvent.ACTION_DROP -> {
                    val draggedView = event.localState as View
                    val draggedViewParent = draggedView.parent as? ViewGroup
                    draggedViewParent?.removeView(draggedView)
                    binding.cardView.setCardBackgroundColor(Color.WHITE)
                    binding.cardView.addView(draggedView)
                    true
                }

                DragEvent.ACTION_DRAG_ENDED -> {
                    binding.cardView.setCardBackgroundColor(Color.WHITE)
                    true
                }

                else -> false
            }
        }

        binding.imageView.setOnLongClickListener { v ->

            val item = ClipData.Item(v.tag as? CharSequence)
            val dragData = ClipData(
                v.tag as? CharSequence,
                arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                item)
            val shadowBuilder = View.DragShadowBuilder(v)
            v.startDragAndDrop(dragData, shadowBuilder, v, 0)
            true
        }
    }
}
