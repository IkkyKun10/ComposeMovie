package com.riezki.composemovie.presenter.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Upcoming
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.riezki.composemovie.R
import com.riezki.composemovie.presenter.ui.theme.ComposeMovieTheme
import com.riezki.composemovie.presenter.viewmodel.MovieListUiEvent
import com.riezki.composemovie.presenter.viewmodel.MovieViewModel
import com.riezki.composemovie.utils.Screen

/**
 * @author riezky maisyar
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    bottomNavController: NavHostController = rememberNavController(),
    onClickToDetail: (Int) -> Unit,
) {
    val movieListViewModel = hiltViewModel<MovieViewModel>()
    val movieListState by movieListViewModel.movieListState.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                bottomNavHostController = bottomNavController,
                onEvent = movieListViewModel::onEvent
            )
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (movieListState.isCurrentPopularScreen)
                            stringResource(R.string.popular_movies) else stringResource(R.string.upcoming_movies),
                        fontSize = 20.sp
                    )
                },
                modifier = Modifier
                    .shadow(2.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.inverseOnSurface
                )
            )
        }
    ) { innerPad ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPad)
        ) {
            NavHost(
                navController = bottomNavController,
                startDestination = Screen.PopularMovieList.route
            ) {
                composable(Screen.PopularMovieList.route) {
                    PopularMoviesScreen(
                        movieListState = movieListState,
                        onEvent = movieListViewModel::onEvent,
                        onClikedItem = {
                            it.id?.let(onClickToDetail)
                        }
                    )
                }
                composable(Screen.UpcomingMovieList.route) {
                    UpcomingMoviesScreen(
                        movieListState = movieListState,
                        onEvent = movieListViewModel::onEvent,
                        onClikedItem = {
                            it.id?.let(onClickToDetail)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    bottomNavHostController: NavHostController,
    onEvent: (MovieListUiEvent) -> Unit,
) {
    val items = listOf(
        BottomItem(
            title = "Popular",
            icon = Icons.Rounded.Movie
        ),
        BottomItem(
            title = "Upcoming",
            icon = Icons.Rounded.Upcoming
        )
    )

    var selected by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            items.forEachIndexed { index, bottomItem ->
                NavigationBarItem(
                    selected = selected == index,
                    onClick = {
                        selected = index
                        when (selected) {
                            0 -> {
                                onEvent(MovieListUiEvent.Navigate)
                                bottomNavHostController.popBackStack()
                                bottomNavHostController.navigate(Screen.PopularMovieList.route)
                            }

                            1 -> {
                                onEvent(MovieListUiEvent.Navigate)
                                bottomNavHostController.popBackStack()
                                bottomNavHostController.navigate(Screen.UpcomingMovieList.route)
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = bottomItem.icon,
                            contentDescription = bottomItem.title,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    label = {
                        Text(
                            text = bottomItem.title,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                )
            }
        }
    }
}

data class BottomItem(
    val title: String,
    val icon: ImageVector,
)

@Preview
@Composable
fun HomeScreenPrev() {
    ComposeMovieTheme {
        HomeScreen {}
    }
}