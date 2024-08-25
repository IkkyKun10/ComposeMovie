package com.riezki.composemovie.core.mappers

import com.riezki.composemovie.core.local.model.MovieEntity
import com.riezki.composemovie.core.remote.dto.MovieDto
import com.riezki.composemovie.domain.model.Movie

/**
 * @author riezky maisyar
 */

fun MovieDto.toEntity(category: String): MovieEntity {
    return MovieEntity(
        adult = adult,
        posterPath = posterPath,
        backdropPath = backdropPath,
        genreIds = try {
            genreIds?.joinToString(",") ?: "-1,-2"
        } catch (e: Exception) {
            "-1,-2"
        },
        id = id,
        originalTitle = originalTitle,
        originalLanguage = originalLanguage,
        title = title,
        video = video,
        overview = overview,
        popularity = popularity,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        category = category
    )
}

fun MovieEntity.toMovie(category: String) : Movie {
    return Movie(
        adult = adult,
        posterPath = posterPath,
        backdropPath = backdropPath,
        genreIds = try {
            genreIds?.split(",")?.map { it.toInt() }
        } catch (e: Exception) {
            emptyList()
        },
        id = id,
        originalTitle = originalTitle,
        originalLanguage = originalLanguage,
        title = title,
        video = video,
        overview = overview,
        popularity = popularity,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        category = category
    )
}