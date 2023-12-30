package com.example.draw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.draw.databinding.ActivityMainBinding
import java.lang.Math.sin
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
        binding.root.addView(myDraw)

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
            lastDegree += degree.toFloat()
            degreeList.add(lastDegree)
            xPos2 = xPos1 + lengthInCm * cos(radians).toFloat()
            yPos2 = yPos1 + lengthInCm * kotlin.math.sin(radians).toFloat()
            myDraw.addLine(xPos1, yPos1, xPos2, yPos2,degreeList,lengthList)

            xPos1 = xPos2
            yPos1 = yPos2
            binding.degree.setText("0")

        }

    }
}