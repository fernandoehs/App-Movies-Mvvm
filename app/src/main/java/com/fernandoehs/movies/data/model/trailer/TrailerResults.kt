package com.fernandoehs.movies.data.model.trailer

data class TrailerResults (
    val id: Long,
    val results: List<TrailerResult>
)

data class TrailerResult (
    val iso639_1: String,
    val iso3166_1: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Long,
    val type: String,
    val official: Boolean,
    val publishedAt: String,
    val id: String
)
