package com.riezki.composemovie.domain

import com.riezki.composemovie.domain.model.Movie
import com.riezki.composemovie.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * @author riezky maisyar
 */

interface MovieRepository {

    suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>>

    suspend fun getMovie(id: Int): Flow<Resource<Movie>>
}