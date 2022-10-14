package com.fernandoehs.movies.domain

import com.fernandoehs.movies.data.repository.MoviesRepository
import com.fernandoehs.movies.domain.model.Movie
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetRecommendedMoviesUseCaseTest{
    @RelaxedMockK
    private lateinit var repository : MoviesRepository
    lateinit var getRecommendedMoviesUseCase: GetRecommendedMoviesUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getRecommendedMoviesUseCase = GetRecommendedMoviesUseCase(repository)
    }

    @Test
    fun `when getRecommended called return values from database`() = runBlocking {
        //Given
        val myList = listOf(Movie(1,"titulo","es","descripcion","path","1992",8.9,true,"1"))
        coEvery { repository.getRecommendedForYouMoviesFromDataBase(any(),any()) } returns myList

        //When
        val response = getRecommendedMoviesUseCase("","")

        //Then
        coVerify(exactly = 1) { repository.getRecommendedForYouMoviesFromDataBase(any(),any()) }
        assert(response == myList)
    }
}
