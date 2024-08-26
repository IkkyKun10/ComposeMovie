package com.riezki.composemovie.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riezki.composemovie.domain.MovieRepository
import com.riezki.composemovie.domain.utils.Resource
import com.riezki.composemovie.utils.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author riezky maisyar
 */

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movieListState = MutableStateFlow(MovieListState())
    val movieListState = _movieListState.asStateFlow()

    init {
        getPopularMovies(false)
        getUpcomingMovies(false)
    }

    fun onEvent(event: MovieListUiEvent) {
        when (event) {
            MovieListUiEvent.Navigate -> {
                _movieListState.update {
                    it.copy(
                        isCurrentPopularScreen = !movieListState.value.isCurrentPopularScreen
                    )
                }
            }
            is MovieListUiEvent.Paginate -> {
                if (event.category == Category.POPULAR) {
                    getPopularMovies(true)
                } else if (event.category == Category.UPCOMING) {
                    getUpcomingMovies(true)
                }
            }
        }
    }

    private fun getPopularMovies(forceFetchFromRemote: Boolean) = viewModelScope.launch {
        _movieListState.update {
            it.copy(isLoading = true)
        }

        repository.getMovieList(
            forceFetchFromRemote = forceFetchFromRemote,
            category = Category.POPULAR,
            movieListState.value.popularMovieListPage
        ).collectLatest { result ->
            when (result) {
                is Resource.Error -> {
                    _movieListState.update {
                        it.copy(isLoading = false)
                    }
                }

                is Resource.Success -> {
                    result.data?.let { popularMovies ->
                        _movieListState.update {
                            it.copy(
                                popularMovies = movieListState.value.popularMovies
                                        + popularMovies.shuffled(),
                                popularMovieListPage = if (!forceFetchFromRemote) {
                                    1
                                } else {
                                    movieListState.value.popularMovieListPage + 1
                                }
                            )
                        }
                    }
                }

                is Resource.Loading -> {
                    _movieListState.update {
                        it.copy(isLoading = result.isLoading)
                    }
                }
            }
        }
    }

    private fun getUpcomingMovies(forceFetchFromRemote: Boolean) = viewModelScope.launch {
        _movieListState.update {
            it.copy(isLoading = true)
        }

        repository.getMovieList(
            forceFetchFromRemote = forceFetchFromRemote,
            category = Category.UPCOMING,
            movieListState.value.upcomingMovieListPage
        ).collectLatest { result ->
            when (result) {
                is Resource.Error -> {
                    _movieListState.update {
                        it.copy(isLoading = false)
                    }
                }

                is Resource.Success -> {
                    result.data?.let { upcomingMovies ->
                        _movieListState.update {
                            it.copy(
                                upcomingMovies = movieListState.value.upcomingMovies
                                        + upcomingMovies.shuffled(),
                                upcomingMovieListPage = if (!forceFetchFromRemote) {
                                    1
                                } else {
                                    movieListState.value.upcomingMovieListPage + 1
                                }
                            )
                        }
                    }
                }

                is Resource.Loading -> {
                    _movieListState.update {
                        it.copy(isLoading = result.isLoading)
                    }
                }
            }
        }
    }
}