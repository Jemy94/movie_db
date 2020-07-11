package com.jemy.moviedb.utils.extensions

import android.content.Context
import android.widget.Toast

fun Context.toastLong(message:String?){
    message?.let {
        Toast.makeText(this, it, Toast.LENGTH_LONG).show()
    }
}