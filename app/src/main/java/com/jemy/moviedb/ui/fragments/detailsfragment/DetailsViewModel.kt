package com.jemy.moviedb.ui.fragments.detailsfragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jemy.moviedb.data.common.Resource
import com.jemy.moviedb.data.repository.DetailsRepository
import com.jemy.moviedb.data.repository.PopularRepository
import com.jemy.moviedb.data.response.PopularDetailsResponse
import com.jemy.moviedb.data.response.PopularImagesResponse
import com.jemy.moviedb.data.response.PopularResponse
import com.jemy.moviedb.utils.Constants
import com.jemy.moviedb.utils.extensions.addTo
import com.jemy.moviedb.utils.extensions.setError
import com.jemy.moviedb.utils.extensions.setLoading
import com.jemy.moviedb.utils.extensions.setSuccess
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailsViewModel(private val detailsRepository: DetailsRepository) : ViewModel() {

    val compositeDisposable = CompositeDisposable()
    private val _popularDetails = MutableLiveData<Resource<PopularDetailsResponse>>()
    private val _popularImages = MutableLiveData<Resource<PopularImagesResponse>>()

    val popularDetails: LiveData<Resource<PopularDetailsResponse>>
        get() = _popularDetails
    val popularImages: LiveData<Resource<PopularImagesResponse>>
        get() = _popularImages

    fun getDetails(popularId: Long) {
        detailsRepository.getDetails(popularId)
            .doOnSubscribe { _popularDetails.setLoading() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ popularResource ->
                popularResource?.data?.let { _popularDetails.setSuccess(it) }
                    ?: _popularDetails.setError(
                        popularResource.message
                    )
            }, { throwable ->
                _popularDetails.setError(Constants.Error.GENERAL)
                Log.e("DetailsFragment", throwable.message ?: "unknown error")
            })
            .addTo(compositeDisposable)
    }

    fun getImages(popularId: Long) {
        detailsRepository.getPopularImages(popularId)
            .doOnSubscribe { _popularImages.setLoading() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ popularImages ->
                popularImages.data.let { response ->
                    val images = response!!.profiles
                    if (images.isNullOrEmpty()) {
                        _popularImages.setError(Constants.Error.NO_DATA)
                    } else {
                        _popularImages.setSuccess(response)
                    }
                }
            }, { throwable ->
                _popularImages.setError(Constants.Error.GENERAL)
                Log.e("DetailsFragment", throwable.message ?: "unknown error")
            })
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}