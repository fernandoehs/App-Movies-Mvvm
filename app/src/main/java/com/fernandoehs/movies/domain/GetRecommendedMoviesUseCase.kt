package com.fernandoehs.movies.domain

import com.fernandoehs.movies.data.repository.MoviesRepository
import com.fernandoehs.movies.domain.model.Movie
import javax.inject.Inject

class GetRecommendedMoviesUseCase @Inject constructor(private val repository : MoviesRepository){
    suspend operator fun invoke(language:String, launchYear:String): List<Movie>{
        return repository.getRecommendedForYouMoviesFromDataBase(language,launchYear)
    }
}