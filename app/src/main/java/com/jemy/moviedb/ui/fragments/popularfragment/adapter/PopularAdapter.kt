package com.jemy.moviedb.ui.fragments.popularfragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jemy.moviedb.R
import com.jemy.moviedb.data.response.PopularResponse.Popular
import com.jemy.moviedb.utils.extensions.load
import kotlinx.android.synthetic.main.item_popular.view.*

class PopularAdapter : RecyclerView.Adapter<PopularViewHolder>() {

    private var itemCallback: ((Popular) -> Unit)? = null
    var items = mutableListOf<Popular>()

    fun addItems(items: List<Popular>) {
        this.items.addAll(items)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_popular, parent, false)
        return PopularViewHolder(view, itemCallback)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val popular = items[position]
        holder.bind(popular)
    }

    fun setItemCallBack(itemCallback: (Popular?) -> Unit) {
        this.itemCallback = itemCallback
    }

    override fun getItemCount(): Int = items.size
}

class PopularViewHolder(itemView: View, private val itemCallback: ((Popular) -> Unit)?) :
    RecyclerView.ViewHolder(itemView) {

    private var popularName = itemView.popularName
    private var popularDepartment = itemView.popularDepartment
    private var popularImage = itemView.popularImage

    fun bind(popular: Popular) {
        itemView.setOnClickListener { itemCallback?.invoke(popular) }
        popularName.text = popular.name
        popularDepartment.text = popular.knownForDepartment
        popular.profilePath?.let { popularImage.load(it) }
    }
}