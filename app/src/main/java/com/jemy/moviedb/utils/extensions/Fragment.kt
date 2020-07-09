package com.jemy.moviedb.utils.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jemy.moviedb.ui.fragments.popularfragment.PopularFragment

//fun <T : ViewModel> PopularFragment.getViewModel(
//    modelClass: Class<T>, viewModelFactory: ViewModelProvider.Factory? = null
//): T {
//    return viewModelFactory?.let {
//        ViewModelProvider(this, it).get(modelClass)
//    } ?: ViewModelProviders(this).get(modelClass)
//}