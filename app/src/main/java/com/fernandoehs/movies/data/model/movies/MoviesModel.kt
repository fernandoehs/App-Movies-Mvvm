package com.fernandoehs.movies.data.model.movies

import com.google.gson.annotations.SerializedName

data class MoviesModel(
    @SerializedName("dates") val dates: Dates?,
    @SerializedName("page") val page: Long,
    @SerializedName("results") val results: List<MovieModel>,
    @SerializedName("total_pages") val total_pages: Long,
    @SerializedName("total_results") val total_results: Long
)

data class Dates (
    @SerializedName("maximum")val maximum: String,
    @SerializedName("minimum")val minimum: String
)



