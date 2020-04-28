package com.caijingjin.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_viewpager.*
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class viewpagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_viewpager, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val photoList: ArrayList<PhotoItem>? = arguments?.getParcelableArrayList<PhotoItem>("PHOTO_LIST")
        val index = arguments?.getInt("PHOTO_POSITION")?: 0
        PagerPhotoListAdapter().apply {
            viewPager2.adapter = this
            submitList(photoList)
        }
        viewPager2.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                photoTag.text="${position+1}/${photoList?.size}"
            }
        })
        if (index != null) {
            viewPager2.setCurrentItem(index,false)
        }
    }
}


