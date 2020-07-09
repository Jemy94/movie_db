package com.jemy.moviedb.data.common

import android.util.Log
import com.google.gson.Gson
import com.jemy.moviedb.data.response.ErrorResponse
import com.jemy.moviedb.utils.Constants
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class Validator @Inject constructor(private val gson: Gson) {

    /**
     * Validate response body to check if it's error or not
     *
     * @param response body
     */
    fun <T> validateResponse(response: Response<T>?): Resource<T> {
        response?.let {
            it.apply {
                return if (isSuccessful) {
                    Resource(ResourceState.SUCCESS, data = response.body())
                } else {
                    when (code()) {
                        400 -> handleErrorBody(response.errorBody())
                        // 401 ~ ...
                        else -> Resource(ResourceState.ERROR, message = Constants.Error.GENERAL)
                    }
                }
            }
        } ?: Log.d("Null Response", "Response = null")
        return Resource(ResourceState.ERROR, message = Constants.Error.GENERAL)
    }

    private fun <T> handleErrorBody(errorBody: ResponseBody?): Resource<T> {
        val errorResponse: ErrorResponse? =
            gson.fromJson(errorBody?.string(), ErrorResponse::class.java)
        return errorResponse?.let { body ->
            try {
                if (!body.statusMessage.isNullOrBlank()) {
                    Resource<T>(ResourceState.ERROR, message = body.statusMessage)
                } else {
                    Resource<T>(ResourceState.ERROR, message = Constants.Error.GENERAL)
                }
            } catch (e: Exception) {
                Log.d("Exception", e.message)
                Resource<T>(ResourceState.ERROR, message = e.message)
            }
        } ?: Resource<T>(ResourceState.ERROR, message = Constants.Error.GENERAL)
    }
}