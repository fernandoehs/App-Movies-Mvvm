package com.fernandoehs.movies.domain

import com.fernandoehs.movies.data.model.trailer.TrailerResult
import com.fernandoehs.movies.data.repository.MoviesRepository
import javax.inject.Inject

class GetTrailerResultsUseCase @Inject constructor(private val repository : MoviesRepository){

    suspend operator fun invoke(videoId: String):List<TrailerResult> = repository.getTrailerResultsFromApi(videoId)
}