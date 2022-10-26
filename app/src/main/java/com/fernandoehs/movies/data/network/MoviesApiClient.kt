package com.fernandoehs.movies.data.network

import com.fernandoehs.movies.utils.Constants
import com.fernandoehs.movies.data.model.movies.MoviesModel
import com.fernandoehs.movies.data.model.trailer.TrailerResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import kotlin.random.Random


interface MoviesApiClient {
    @GET(Constants.UPCOMING_MOVIES)
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("page") page: String = "4"
    ): Response<MoviesModel>

    @GET(Constants.TOP_RATED_MOVIES)
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("page") page: String = "4"
    ): Response<MoviesModel>

    @GET(Constants.PATH_MOVIE + "{movieId}" + Constants.PATH_VIDEOS)
    suspend fun getTrailerResults(
        @Path(value = "movieId", encoded = false) movieId: String,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<TrailerResults>
}

