package com.fernandoehs.movies.data.model.movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fernandoehs.movies.domain.model.Movie

@Entity(tableName = "movie_table")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "original_language") val original_language: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "poster_path") val poster_path: String,
    @ColumnInfo(name = "release_year") val release_year: String,
    @ColumnInfo(name = "vote_average") val vote_average: Double,
    @ColumnInfo(name = "posterBig") val posterBig: Boolean,
    @ColumnInfo(name = "type") val type: String,
)

fun Movie.toDatabase() = MovieEntity(
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


