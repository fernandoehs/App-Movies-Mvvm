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


class GetUpcomingMoviesUseCaseTest{
    @RelaxedMockK
    private lateinit var repository : MoviesRepository

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `When theres internet connection return values from api`() = runBlocking {
        //Given
        val myList = listOf(Movie(1,"titulo","es","descripcion","path","1992",8.9,true,"1"))
        coEvery { repository.getUpcomingMoviesFromApi() } returns myList

        //When
        val response = repository.getUpcomingMoviesFromApi()

        //Then
        coVerify(exactly = 1) {  repository.getUpcomingMoviesFromApi()}
        coVerify(exactly = 0) {  repository.getUpcomingMoviesFromDatabase()}
        assert(response == myList)
    }

    @Test
    fun `When theres NO internet connection return values from database`() = runBlocking {
        //Given
        val myList = listOf(Movie(1,"titulo","es","descripcion","path","1992",8.9,true,"1"))
        coEvery { repository.getUpcomingMoviesFromDatabase() } returns myList

        //When
        val response = repository.getUpcomingMoviesFromDatabase()

        //Then
        coVerify(exactly = 0) {  repository.getUpcomingMoviesFromApi()}
        coVerify(exactly = 0) {  repository.clearUpcomingMovies()}
        coVerify(exactly = 0) {  repository.insertMovies(any())}
        coVerify(exactly = 1) {  repository.getUpcomingMoviesFromDatabase()}
        assert(response == myList)
    }

}
