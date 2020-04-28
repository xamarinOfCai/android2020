package com.caijingjin.viewpager2demo

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_rotato.*
import kotlin.random.Random

/**
 * A simple [Fragment] subclass.
 */
class RotatoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rotato, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imageViewrotato.setOnClickListener{
            var random = 30f
            ObjectAnimator.ofFloat(it,"rotation",it.rotation+random).start()
        }
    }

}
