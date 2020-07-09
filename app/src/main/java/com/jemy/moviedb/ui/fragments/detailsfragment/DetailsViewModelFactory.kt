package com.jemy.moviedb.ui.fragments.detailsfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jemy.moviedb.data.repository.DetailsRepository
import com.jemy.moviedb.ui.fragments.popularfragment.PopularViewModel
import javax.inject.Inject

class DetailsViewModelFactory @Inject constructor(
    private val detailsRepository: DetailsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(detailsRepository) as T
        }
        throw IllegalArgumentException("Unknown class name need ${DetailsViewModel::class.java.simpleName} instance")
    }

}