package com.riezki.composemovie.presenter.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riezki.composemovie.domain.MovieRepository
import com.riezki.composemovie.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author riezkymaisyar
 */

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId = savedStateHandle.get<Int>("movieId")

    private val _detailState = MutableStateFlow(DetailMovieState())
    val detailState = _detailState.asStateFlow()

    init {
        getMovieById(movieId ?: -1)
    }

    private fun getMovieById(id: Int) = viewModelScope.launch {
        _detailState.update {
            it.copy(isLoading = true)
        }

        movieRepository.getMovie(id).collectLatest { result ->
            when (result) {
                is Resource.Error -> {
                    _detailState.update {
                        it.copy(isLoading = false)
                    }
                }
                is Resource.Loading -> {
                    _detailState.update {
                        it.copy(isLoading = result.isLoading)
                    }
                }
                is Resource.Success -> {
                    result.data?.let { movie ->
                        _detailState.update {
                            it.copy(
                                movie = movie
                            )
                        }
                    }
                }
            }
        }
    }
}