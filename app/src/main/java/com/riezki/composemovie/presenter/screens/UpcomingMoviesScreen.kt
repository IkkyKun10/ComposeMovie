package com.riezki.composemovie.presenter.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.riezki.composemovie.domain.model.Movie
import com.riezki.composemovie.presenter.screens.component.MovieItem
import com.riezki.composemovie.presenter.viewmodel.MovieListState
import com.riezki.composemovie.presenter.viewmodel.MovieListUiEvent
import com.riezki.composemovie.utils.Category

/**
 * @author riezkymaisyar
 */

@Composable
fun UpcomingMoviesScreen(
    movieListState: MovieListState,
    onEvent: (MovieListUiEvent) -> Unit,
    onClikedItem: (Movie) -> Unit,
) {
    if (movieListState.upcomingMovies.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(movieListState.upcomingMovies) {
                MovieItem(movie = it, onClikedItem)

                if (it == movieListState.upcomingMovies.last() && !movieListState.isLoading) {
                    onEvent(MovieListUiEvent.Paginate(Category.UPCOMING))
                }
            }
        }
    }
}