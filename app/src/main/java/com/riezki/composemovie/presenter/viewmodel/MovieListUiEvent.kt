package com.riezki.composemovie.presenter.viewmodel

/**
 * @author riezky maisyar
 */

sealed interface MovieListUiEvent {
    data object Navigate: MovieListUiEvent
    data class Paginate(val category: String): MovieListUiEvent
}