package com.fernandoehs.movies.data.repository

import com.fernandoehs.movies.data.database.MovieDao
import com.fernandoehs.movies.data.model.movies.MovieEntity
import com.fernandoehs.movies.data.model.trailer.TrailerResult
import com.fernandoehs.movies.data.network.MoviesService
import com.fernandoehs.movies.domain.model.Movie
import com.fernandoehs.movies.domain.model.toDomain
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val api : MoviesService,
    private val movieDao: MovieDao
    ){

    suspend fun getUpcomingMoviesFromApi(): List<Movie> {
        val upcomingMovies = api.getUpcomingMovies()
        upcomingMovies.forEach { movie ->
            movie.setUpcomingType()
        }
        return upcomingMovies.map { it.toDomain() }
    }

    suspend fun getTopRatedMoviesFromApi(): List<Movie> {
        val topRatedMoviesModel = api.getTopRatedMovies()
        topRatedMoviesModel.forEach {
            it.setTopRatedType()
        }
        return topRatedMoviesModel.map { it.toDomain() }
    }

    suspend fun getTrailerResultsFromApi(videoId: String): List<TrailerResult>{
        return api.getTrailerResults(videoId)
    }

    suspend fun getUpcomingMoviesFromDatabase():List<Movie>{
        val response: List<MovieEntity> = movieDao.getUpcomingMovies()
        return response.map { it.toDomain() }
    }

    suspend fun getTopRatedMoviesFromDatabase():List<Movie>{
        val response: List<MovieEntity> = movieDao.getTopRatedMovies()
        return response.map { it.toDomain() }
    }

    suspend fun getRecommendedForYouMoviesFromDataBase(language: String, launchYear: String):List<Movie>{
        val response: List<MovieEntity> = movieDao.getRecommendedMovies(language,launchYear)
        return response.map { it.toDomain() }
    }

    suspend fun insertMovies(movies: List<MovieEntity>){
        movieDao.insertAll(movies)
    }

    suspend fun clearTopRatedMovies(){
        movieDao.deleteTopRatedMovies()
    }

    suspend fun clearUpcomingMovies(){
        movieDao.deleteUpcomingMovies()
    }
}