package com.jemy.moviedb.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.jemy.moviedb.R
import com.jemy.moviedb.utils.Constants

fun ImageView.load(url: String) {
    Glide.with(context)
        .load("${Constants.BASE_IMAGE_URL}$url")
        .placeholder(R.drawable.ic_avatar)
        .into(this)
}