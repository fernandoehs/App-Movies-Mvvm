package com.fernandoehs.movies.presentation.interfaces

interface IScreenNavigation {
    interface Listener{
        fun navigateToMoviesDetail(
            title: String,
            launchDate: String,
            language: String,
            rate: String,
            overview: String,
            posterPath: String,
            movieId: String,
        )

        fun onBackGeneral()
    }
}