package com.fernandoehs.movies.domain.model

import com.fernandoehs.movies.data.model.movies.MovieEntity
import com.fernandoehs.movies.data.model.movies.MovieModel

data class Movie(
    val id: Long,
    val title: String,
    val original_language: String,
    val overview: String,
    val poster_path: String,
    val release_year: String,
    val vote_average: Double,
    var posterBig: Boolean,
    val type: String
)

fun MovieModel.toDomain() = Movie(
    id,
    title,
    original_language,
    overview,
    poster_path,
    release_year,
    vote_average,
    posterBig,
    type
)
fun MovieEntity.toDomain() = Movie(
    id,
    title,
    original_language,
    overview,
    poster_path,
    release_year,
    vote_average,
    posterBig,
    type
)
