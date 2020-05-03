package com.example.pagergallery


import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.get
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_pager_photo.*
import kotlinx.android.synthetic.main.pager_photo_view.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.jar.Manifest

/**
 * A simple [Fragment] subclass.
 */
const val REQUEST_WRITE_EXTERNAL_STORGE = 1
class PagerPhotoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pager_photo, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val photoList = arguments?.getParcelableArrayList<PhotoItem>("PHOTO_LIST")
        PagerPhotoListAdapter().apply {
            viewPager2.adapter = this
            submitList(photoList)
        }

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                photoTag.text = getString(R.string.photo_tag,position + 1,photoList?.size)
            }
        })

        viewPager2.setCurrentItem(arguments?.getInt("PHOTO_POSITION")?:0,false)
        savebutton.setOnClickListener{
            if(Build.VERSION.SDK_INT < 29 && ContextCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),REQUEST_WRITE_EXTERNAL_STORGE)
            }else{
                viewLifecycleOwner.lifecycleScope.launch {
                    savePhoto()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_WRITE_EXTERNAL_STORGE->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    viewLifecycleOwner.lifecycleScope.launch{
                        savePhoto()
                    }
                }else{
                    Toast.makeText(requireContext(),"存储失败",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

     private suspend fun savePhoto() {
         withContext(Dispatchers.IO) {
             val holder =
                 ((viewPager2[0] as RecyclerView).findViewHolderForAdapterPosition(viewPager2.currentItem)) as PagerPhotoViewHolder
             val bitmap = holder.itemView.pagerPhoto.drawable.toBitmap();
             //insertImage outofdate
//        if(MediaStore.Images.Media.insertImage(requireContext().contentResolver,bitmap,"","") != null){
//            Toast.makeText(requireContext(), "存储成功", Toast.LENGTH_SHORT).show()
//        }else {
//            Toast.makeText(requireContext(),"存储失败",Toast.LENGTH_SHORT).show()
//        }
             val saveUrl = requireContext().contentResolver.insert(
                 MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                 ContentValues()
             ) ?: kotlin.run {
                 Toast.makeText(requireContext(), "存储失败", Toast.LENGTH_SHORT).show()
                 return@withContext
             }
             //存储
             requireContext().contentResolver.openOutputStream(saveUrl).use {
                 if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, it)) {
                     MainScope().launch{Toast.makeText(requireContext(), "存储成功", Toast.LENGTH_SHORT).show()}
                 } else {
                     MainScope().launch{Toast.makeText(requireContext(), "存储失败", Toast.LENGTH_SHORT).show()}
                 }
             }
         }
    }
}
