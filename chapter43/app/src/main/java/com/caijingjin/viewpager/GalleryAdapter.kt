package com.caijingjin.viewpager

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.gallery_cell.view.*

/**
 * @ClassName GalleryAdapter
 * @Description
 * @Author cjj
 * @Date 2020-04-23 22:54
 * @Version 1.0
 */

class MyViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){


}

public class GalleryAdapter() : ListAdapter<PhotoItem, MyViewHolder>(DIFFCALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val holder = MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.gallery_cell, parent, false))
        holder.itemView.setOnClickListener{
            holder.itemView.setOnClickListener{
                Bundle().apply {
                    putParcelableArrayList("PHOTO_LIST", ArrayList(currentList));
                    putInt("PHOTO_POSITION",holder.absoluteAdapterPosition)
                    holder.itemView.findNavController().navigate(R.id.action_galleryFragment_to_viewpagerFragment,this)
                }
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.shimmerGalleryLayout.apply{
            setShimmerColor(0x55FFFFFF)
            setShimmerAngle(0)
            startShimmerAnimation()
        }
        Glide.with(holder.itemView)
            .load(getItem(position).previewUrl)
            .placeholder(R.drawable.ic_photo_grey_24dp)
            .listener(object:RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    //？避免沒有加載完成
                    return false.also { holder.itemView.shimmerGalleryLayout?.stopShimmerAnimation() }
                }
            }).into(holder.itemView.imageView)
    }

    object DIFFCALLBACK :DiffUtil.ItemCallback<PhotoItem>(){
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem === newItem
        }

        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem.photoId == newItem.photoId;
        }
    }


}