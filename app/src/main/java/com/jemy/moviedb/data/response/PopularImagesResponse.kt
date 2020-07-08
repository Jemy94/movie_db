package com.jemy.moviedb.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PopularImagesResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "profiles") val profiles: List<Profile>
) {
    @JsonClass(generateAdapter = true)
    data class Profile(
        @Json(name = "aspect_ratio") val aspectRatio: Double,
        @Json(name = "file_path") val filePath: String,
        @Json(name = "height") val height: Int,
        @Json(name = "iso_639_1") val iso6391: String? = "",
        @Json(name = "vote_average") val voteAverage: Int,
        @Json(name = "vote_count") val voteCount: Int,
        @Json(name = "width") val width: Int
    )
}