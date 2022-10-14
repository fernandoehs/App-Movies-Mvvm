package com.fernandoehs.movies.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fernandoehs.movies.data.model.movies.MovieEntity

@Dao
interface MovieDao {
    @Query("select * from movie_table where type= " + 1)
    suspend fun getTopRatedMovies(): MutableList<MovieEntity>

    @Query("select * from movie_table where type= " + 2)
    suspend fun getUpcomingMovies(): MutableList<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Query("select * from movie_table where original_language like  :language and release_year like  :releaseYear limit 6" )
    suspend fun getRecommendedMovies(language: String, releaseYear: String): MutableList<MovieEntity>

    @Query("DELETE FROM movie_table where type = '1'")
    suspend fun deleteTopRatedMovies()

    @Query("DELETE FROM movie_table where type = '2'")
    suspend fun deleteUpcomingMovies()

}