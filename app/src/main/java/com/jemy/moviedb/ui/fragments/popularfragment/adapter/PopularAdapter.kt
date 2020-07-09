package com.jemy.moviedb.ui.fragments.popularfragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jemy.moviedb.R
import com.jemy.moviedb.data.response.PopularResponse.Popular
import com.jemy.moviedb.utils.extensions.load
import kotlinx.android.synthetic.main.item_popular.view.*

class PopularAdapter(private val list: List<Popular>) : RecyclerView.Adapter<PopularViewHolder>() {

    private var itemCallback: ((Long) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_popular, parent, false)
        return PopularViewHolder(view, itemCallback)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val popular = list[position]
        holder.bind(popular)
    }

    fun setItemCallBack(itemCallback: (Long?) -> Unit) {
        this.itemCallback = itemCallback
    }

    override fun getItemCount(): Int = list.size
}

class PopularViewHolder(itemView: View, private val itemCallback: ((Long) -> Unit)?) :
    RecyclerView.ViewHolder(itemView) {

    private var popularName = itemView.popularName
    private var popularDepartment = itemView.popularDepartment
    private var popularImage = itemView.popularImage

    fun bind(popular: Popular) {
        itemView.setOnClickListener { itemCallback?.invoke(popular.id) }
        popularName.text = popular.name
        popularDepartment.text = popular.knownForDepartment
        popular.profilePath?.let { popularImage.load(it) }
    }
}