package com.fernandoehs.movies.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fernandoehs.movies.data.model.movies.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}
