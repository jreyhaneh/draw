package com.example.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.draw.databinding.ActivityMainBinding
import java.lang.Math.toRadians
import kotlin.math.cos
import kotlin.math.tan

class MainActivity : AppCompatActivity() {

    private var xPos1 =20f
    private var yPos1 =1000f
    private var xPos2 = 0.0f
    private var yPos2 = 0.0f
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val myDraw=first(this,null)
//        setContentView(my)
        binding.root.addView(myDraw)

        binding.b.setOnClickListener {
            val d = binding.d.text.toString().toFloat()
            val s = binding.s.text.toString().toFloat()
            val m = tan(d)
            xPos2 = xPos1 + s
            yPos2 = (m * xPos2) - (m * xPos1) + yPos1

//            xPos2=xPos1+(s* cos(toRadians(d))).toFloat()
//            yPos2=yPos1+(s*cos(toRadians(d))).toFloat()

            myDraw.addLine(xPos1, yPos1, xPos2, yPos2)
            Log.e("1","$xPos1")
            Log.e("2","$yPos1")
            Log.e("3","$xPos2")
            Log.e("4","$yPos2")
            xPos1 = xPos2
            yPos1 = yPos2
        }

    }
}