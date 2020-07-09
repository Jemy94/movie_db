package com.jemy.moviedb.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PopularResponse(
    @Json(name = "page") val page: Int,
    @Json(name = "results") val results: List<Popular>,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results") val totalResults: Int
) {
    @JsonClass(generateAdapter = true)
    data class Popular(
        @Json(name = "adult") val adult: Boolean,
        @Json(name = "gender") val gender: Int,
        @Json(name = "id") val id: Long,
        @Json(name = "known_for") val knownFor: List<KnownFor>,
        @Json(name = "known_for_department") val knownForDepartment: String,
        @Json(name = "name") val name: String?="",
        @Json(name = "popularity") val popularity: Double,
        @Json(name = "profile_path") val profilePath: String?=""
    ) {
        @JsonClass(generateAdapter = true)
        data class KnownFor(
            @Json(name = "adult") val adult: Boolean? = false,
            @Json(name = "backdrop_path") val backdropPath: String?="",
            @Json(name = "first_air_date") val firstAirDate: String?="",
            @Json(name = "genre_ids") val genreIds: List<Int>?= emptyList(),
            @Json(name = "id") val id: Int,
            @Json(name = "media_type") val mediaType: String?="",
            @Json(name = "name") val name: String?="",
            @Json(name = "origin_country") val originCountry: List<String>?= emptyList(),
            @Json(name = "original_language") val originalLanguage: String?="",
            @Json(name = "original_name") val originalName: String?="",
            @Json(name = "original_title") val originalTitle: String?="",
            @Json(name = "overview") val overview: String?="",
            @Json(name = "poster_path") val posterPath: String?="",
            @Json(name = "release_date") val releaseDate: String?="",
            @Json(name = "title") val title: String?="",
            @Json(name = "video") val video: Boolean?=false,
            @Json(name = "vote_average") val voteAverage: Double,
            @Json(name = "vote_count") val voteCount: Int
        )
    }
}