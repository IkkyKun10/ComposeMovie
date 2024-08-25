package com.riezki.composemovie.core.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_entity")
data class MovieEntity(
    @PrimaryKey
    val id: Int? = null,
    val adult: Boolean? = null,
    val backdropPath: String? = null,
    val genreIds: String? = null,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null,
    val category: String? = null,
)
