package com.jemy.moviedb.ui.fragments.popularfragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jemy.moviedb.data.common.Resource
import com.jemy.moviedb.data.repository.PopularRepository
import com.jemy.moviedb.data.response.PopularResponse
import com.jemy.moviedb.utils.Constants
import com.jemy.moviedb.utils.extensions.addTo
import com.jemy.moviedb.utils.extensions.setError
import com.jemy.moviedb.utils.extensions.setLoading
import com.jemy.moviedb.utils.extensions.setSuccess
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PopularViewModel(private val popularRepository: PopularRepository) : ViewModel() {

    val compositeDisposable = CompositeDisposable()
    private val _popular = MutableLiveData<Resource<PopularResponse>>()

    val popular: LiveData<Resource<PopularResponse>>
        get() = _popular

    fun getPopular() {
        popularRepository.getPopular()
            .doOnSubscribe { _popular.setLoading() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ popularResource ->
                popularResource?.data?.let { _popular.setSuccess(it) } ?: _popular.setError(
                    popularResource.message
                )
            }, { throwable ->
                _popular.setError(Constants.Error.GENERAL)
                Log.e("PopularFragment", throwable.message ?: "unknown error")
            })
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}