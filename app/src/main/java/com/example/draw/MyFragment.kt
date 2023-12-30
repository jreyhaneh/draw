package com.example.draw

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.draw.databinding.FragmentMyBinding
import kotlin.math.tan

class MyFragment : Fragment() {

    private var xPos1 = 416.614F
    private var yPos1 = 643.732F
    private var xPos2 = 0.0f
    private var yPos2 = 0.0f

    private lateinit var binding: FragmentMyBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setContentView(first(this, xPos1, yPos1, xPos2, yPos2))
        val myDraw=DrawLine(requireContext(),null)

        binding.b.setOnClickListener {
            val d = binding.d.text.toString().toFloat()
            val s = binding.s.text.toString().toFloat()
            val m = tan(d)
            xPos2 = xPos1 + s
            yPos2 = (m * xPos2) - (m * xPos1) + yPos1
//            myDraw.drawLine( xPos1, yPos1, xPos2, yPos2)
            xPos1 = xPos2
            yPos1 = yPos2
        }
    }

}