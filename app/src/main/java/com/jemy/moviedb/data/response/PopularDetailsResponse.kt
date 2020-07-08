package com.jemy.moviedb.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PopularDetailsResponse(
    @Json(name = "adult") val adult: Boolean,
    @Json(name = "biography") val biography: String,
    @Json(name = "birthday") val birthday: String? = "",
    @Json(name = "deathday") val deathday: String? = "",
    @Json(name = "gender") val gender: Int,
    @Json(name = "homepage") val homepage: String? = "",
    @Json(name = "id") val id: Int,
    @Json(name = "imdb_id") val imdbId: String,
    @Json(name = "known_for_department") val knownForDepartment: String,
    @Json(name = "name") val name: String,
    @Json(name = "place_of_birth") val placeOfBirth: String? = "",
    @Json(name = "popularity") val popularity: Double,
    @Json(name = "profile_path") val profilePath: String
)