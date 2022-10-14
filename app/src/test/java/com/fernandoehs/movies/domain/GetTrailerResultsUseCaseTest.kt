package com.fernandoehs.movies.domain

import com.fernandoehs.movies.data.model.trailer.TrailerResult
import com.fernandoehs.movies.data.repository.MoviesRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetTrailerResultsUseCaseTest{
    @RelaxedMockK
    private lateinit var repository : MoviesRepository
    lateinit var getTrailerResultsUseCase: GetTrailerResultsUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getTrailerResultsUseCase = GetTrailerResultsUseCase(repository)
    }

    @Test
    fun `when getTrailerMovie called return values from api`() = runBlocking {
        //Given
        val myList = listOf(TrailerResult("iso1", "iso2","movie", "1234drf4","youtube", 4,"other",false,"2002.03.09","1234"))
        coEvery { repository.getTrailerResultsFromApi(any()) } returns myList

        //When
        val response = getTrailerResultsUseCase("1234")

        //Then
        coVerify(exactly = 1) { repository.getTrailerResultsFromApi(any()) }
        assert(response == myList)
    }
}