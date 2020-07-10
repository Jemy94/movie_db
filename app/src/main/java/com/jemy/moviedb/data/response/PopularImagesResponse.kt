package com.jemy.moviedb.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PopularImagesResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "profiles") val profiles: List<Profile>? = emptyList()
) {
    @JsonClass(generateAdapter = true)
    data class Profile(
        @Json(name = "aspect_ratio") val aspectRatio: Double? = 0.0,
        @Json(name = "file_path") val filePath: String? = "",
        @Json(name = "height") val height: Int? = 0,
        @Json(name = "iso_639_1") val iso6391: String? = "",
        @Json(name = "vote_average") val voteAverage: Double? = 0.0,
        @Json(name = "vote_count") val voteCount: Int? = 0,
        @Json(name = "width") val width: Int? = 0
    )
}