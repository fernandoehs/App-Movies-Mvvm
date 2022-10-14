package com.fernandoehs.movies.data.network

import com.fernandoehs.movies.data.model.movies.MovieModel
import com.fernandoehs.movies.data.model.trailer.TrailerResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesService

@Inject
constructor(private val api: MoviesApiClient){

    suspend fun getUpcomingMovies(): List<MovieModel>{
        return withContext(Dispatchers.IO){
            val response = api.getUpcomingMovies()
            response.body()?.results ?: emptyList()
        }
    }

    suspend fun getTopRatedMovies(): List<MovieModel>{
        return withContext(Dispatchers.IO){
            val response = api.getTopRatedMovies()
            response.body()?.results ?: emptyList()
        }
    }

    suspend fun getTrailerResults(videoId: String): List<TrailerResult> {
        return withContext(Dispatchers.IO){
            val response = api.getTrailerResults(videoId)
            response.body()?.results ?: emptyList()
        }
    }
}