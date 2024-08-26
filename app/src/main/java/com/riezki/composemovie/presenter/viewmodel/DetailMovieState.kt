package com.riezki.composemovie.presenter.viewmodel

import com.riezki.composemovie.domain.model.Movie

data class DetailMovieState(
    val isLoading: Boolean = false,
    val movie: Movie? = null
)
