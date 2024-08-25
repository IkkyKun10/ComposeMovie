package com.riezki.composemovie.presenter.viewmodel

import com.riezki.composemovie.domain.model.Movie

/**
 * @author riezky maisyar
 */

data class MovieListState (
    val isLoading: Boolean = false,
    val popularMovies: List<Movie> = emptyList(),
    val upcomingMovies: List<Movie> = emptyList(),
    val popularMovieListPage: Int = 1,
    val upcomingMovieListPage: Int = 1,
    val isCurrentPopularScreen: Boolean = true,
    val errorMessage: String = "",
)