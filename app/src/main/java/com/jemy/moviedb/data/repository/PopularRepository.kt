package com.jemy.moviedb.data.repository

import com.jemy.moviedb.data.common.Resource
import com.jemy.moviedb.data.common.Validator
import com.jemy.moviedb.data.remote.ApiInterface
import com.jemy.moviedb.data.response.PopularResponse
import io.reactivex.Single
import javax.inject.Inject

class PopularRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val validator: Validator
) {

    fun getPopular(): Single<Resource<PopularResponse>> {
        return apiInterface.getPopular()
            .map { validator.validateResponse(it) }
            .map { Resource(it.state, if (it.data == null) null else it.data, it.message) }
    }
}