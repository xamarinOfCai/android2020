package com.caijingjin.viewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.pager_photo_view.view.*

/**
 * @ClassName PagerPhotoListAdapter
 * @Description
 * @Author cjj
 * @Date 2020-04-28 21:34
 * @Version 1.0
 */
class PagerPhotoListAdapter: ListAdapter<PhotoItem, PagerPhotoViewHolder>(DiffCallBack) {
    object DiffCallBack: DiffUtil.ItemCallback<PhotoItem>() {
        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem.photoId == newItem.photoId
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerPhotoViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.pager_photo_view,parent,false).apply{

            return PagerPhotoViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: PagerPhotoViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(getItem(position).largeImageURL)
            .placeholder(R.drawable.ic_photo_grey_24dp)
            .into(holder.itemView.pagerPhoto)
    }
}

class PagerPhotoViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)