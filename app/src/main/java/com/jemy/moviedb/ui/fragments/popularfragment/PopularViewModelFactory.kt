package com.jemy.moviedb.ui.fragments.popularfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jemy.moviedb.data.repository.PopularRepository
import javax.inject.Inject


class PopularViewModelFactory@Inject constructor(
    private val popularRepository: PopularRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PopularViewModel::class.java)) {
            return PopularViewModel(popularRepository) as T
        }
        throw IllegalArgumentException("Unknown class name need ${PopularViewModel::class.java.simpleName} instance")
    }

}