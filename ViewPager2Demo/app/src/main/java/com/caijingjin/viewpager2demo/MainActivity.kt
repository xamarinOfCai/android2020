package com.caijingjin.viewpager2demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager2.adapter = object : FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                return 3
            }
            override fun createFragment(position: Int): Fragment {
                var result = when(position){
                    0->RotatoFragment()
                    1->ScaleFragment()
                    else->TranslatorFragment()
                }
                return result
            }
        }
        TabLayoutMediator(tablayout,viewPager2) { tab, position->
            tab.text = when(position){
                0->"旋转"
                1->"缩放"
                else->"移动"
            }
        }.attach()
    }
}
