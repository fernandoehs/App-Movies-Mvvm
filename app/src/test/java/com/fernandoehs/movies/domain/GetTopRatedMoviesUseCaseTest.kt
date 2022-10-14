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


class GetTopRatedMoviesUseCaseTest{
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
        coEvery { repository.getTopRatedMoviesFromApi() } returns myList

        //When
        val response = repository.getTopRatedMoviesFromApi()

        //Then
        coVerify(exactly = 1) {  repository.getTopRatedMoviesFromApi()}
        coVerify(exactly = 0) {  repository.getTopRatedMoviesFromDatabase()}
        assert(response == myList)
    }

    @Test
    fun `When theres NO internet connection return values from database`() = runBlocking {
        //Given
        val myList = listOf(Movie(1,"titulo","es","descripcion","path","1992",8.9,true,"1"))
        coEvery { repository.getTopRatedMoviesFromDatabase() } returns myList

        //When
        val response = repository.getTopRatedMoviesFromDatabase()

        //Then
        coVerify(exactly = 0) {  repository.getTopRatedMoviesFromApi()}
        coVerify(exactly = 0) {  repository.clearTopRatedMovies()}
        coVerify(exactly = 0) {  repository.insertMovies(any())}
        coVerify(exactly = 1) {  repository.getTopRatedMoviesFromDatabase()}
        assert(response == myList)
    }

}