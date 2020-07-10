package com.jemy.moviedb.ui.fragments.detailsfragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jemy.moviedb.R
import com.jemy.moviedb.data.response.PopularImagesResponse.Profile
import com.jemy.moviedb.utils.extensions.load
import kotlinx.android.synthetic.main.item_popular_image.view.*

class ImagesAdapter (private val list: List<Profile>) : RecyclerView.Adapter<ImageViewHolder>() {

    private var itemCallback: ((Profile) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_popular_image, parent, false)
        return ImageViewHolder(view, itemCallback)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val popular = list[position]
        holder.bind(popular)
    }

    fun setItemCallBack(itemCallback: (Profile?) -> Unit) {
        this.itemCallback = itemCallback
    }

    override fun getItemCount(): Int = list.size
}

class ImageViewHolder(itemView: View, private val itemCallback: ((Profile) -> Unit)?) :
    RecyclerView.ViewHolder(itemView) {

    private var popularImage = itemView.popularImages

    fun bind(profile: Profile) {
        itemView.setOnClickListener { itemCallback?.invoke(profile) }
        profile.filePath?.let { popularImage.load(it) }
    }
}