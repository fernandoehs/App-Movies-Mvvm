package com.fernandoehs.movies.presentation.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fernandoehs.movies.domain.GetRecommendedMoviesUseCase
import com.fernandoehs.movies.domain.GetTopRatedMoviesUseCase
import com.fernandoehs.movies.domain.GetUpcomingMoviesUseCase
import com.fernandoehs.movies.domain.model.Movie
import com.fernandoehs.movies.presentation.movies.MoviesViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MoviesViewModelTest{

    @RelaxedMockK
    private lateinit var getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase
    @RelaxedMockK
    private lateinit var getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase
    @RelaxedMockK
    private lateinit var getRecommendedMoviesUseCase: GetRecommendedMoviesUseCase

    private lateinit var moviesViewModel: MoviesViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        moviesViewModel = MoviesViewModel(getUpcomingMoviesUseCase, getTopRatedMoviesUseCase, getRecommendedMoviesUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewmodel is created at the first time, get upcoming Movies and set on the livedata`() = runTest {
        //Given
        val upcomingMovie = listOf(Movie(1,"Minions","en","Una descripcion","path","1999",9.8,false,"1"))
        coEvery { getUpcomingMoviesUseCase() } returns upcomingMovie

        //When
        moviesViewModel.onCreate()

        //Then
        assert(moviesViewModel.upcomingMoviesModel.value == upcomingMovie)
    }

    @Test
    fun `when viewmodel is created at the first time, get top rated Movies and set on the livedata`() = runTest {
        //Given
        val topRated = listOf(Movie(1,"Minions","en","Una descripcion","path","1999",9.8,false,"2"))
        coEvery { getTopRatedMoviesUseCase() } returns topRated

        //When
        moviesViewModel.onCreate()

        //Then
        assert(moviesViewModel.topRatedMoviesModel.value == topRated)
    }

    @Test
    fun `when get Recommended Movies called, get them and set on the livedata`() = runTest {
        //Given
        val recommendedMovies = listOf(Movie(1,"Minions","en","Una descripcion","path","1999",9.8,false,"2"))
        coEvery { getRecommendedMoviesUseCase(any(), any()) } returns recommendedMovies

        //When
        moviesViewModel.getRecommendedMovies("","")

        //Then
        assert(moviesViewModel.recommendedMoviesModel.value == recommendedMovies)
    }

    @Test
    fun `when get Recommended Movies is Empty, keep the last value`() = runTest {
        //Given
        val recommendedMovies = listOf(Movie(1,"Minions","en","Una descripcion","path","1999",9.8,false,"2"))
        moviesViewModel.recommendedMoviesModel.value = recommendedMovies
        val recommendedMoviesEmpty = emptyList<Movie>()
        coEvery { getRecommendedMoviesUseCase(any(), any()) } returns recommendedMoviesEmpty

        //When
        moviesViewModel.getRecommendedMovies("","")

        //Then
        assert(moviesViewModel.recommendedMoviesModel.value == recommendedMovies)
    }

}