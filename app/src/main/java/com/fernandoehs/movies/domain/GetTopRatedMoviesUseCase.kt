package com.fernandoehs.movies.domain

import android.content.Context
import com.fernandoehs.movies.data.model.movies.toDatabase
import com.fernandoehs.movies.utils.Utils
import com.fernandoehs.movies.data.repository.MoviesRepository
import com.fernandoehs.movies.domain.model.Movie
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(private val repository : MoviesRepository, @ApplicationContext val context: Context){

    suspend operator fun invoke(): List<Movie>{
        return if(Utils.isConnected(context)){
            val movies = repository.getTopRatedMoviesFromApi()
            repository.clearTopRatedMovies()
            repository.insertMovies(movies.map { it.toDatabase() })
            movies
        }else{
            repository.getTopRatedMoviesFromDatabase()
        }
    }
}