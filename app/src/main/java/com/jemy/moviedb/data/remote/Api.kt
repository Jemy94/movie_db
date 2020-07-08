package com.jemy.moviedb.data.remote

import com.jemy.moviedb.data.response.PopularDetailsResponse
import com.jemy.moviedb.data.response.PopularImagesResponse
import com.jemy.moviedb.data.response.PopularResponse
import com.jemy.moviedb.utils.Constants
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {


    @GET(Endpoints.POPULAR)
    fun getPopular(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Single<Response<PopularResponse>>

    @GET(Endpoints.POPULAR_DETAILS)
    fun getPopularDetails(
        @Path("person_id") personId: Long,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Single<Response<PopularDetailsResponse>>

    @GET(Endpoints.POPULAR_IMAGES)
    fun getPopularImages(
        @Path("person_id") personId: Long,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Single<Response<PopularImagesResponse>>

}