package com.riezki.composemovie.core.local.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.riezki.composemovie.core.local.model.MovieEntity

/**
 * @author riezky maisyar
 */

@Dao
interface MovieDao {
    @Upsert
    suspend fun upsertAll(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies_entity WHERE id = :id LIMIT 1")
    suspend fun getMovieById(id: Int) : MovieEntity

    @Query("SELECT * FROM movies_entity WHERE category = :category")
    suspend fun getMovieListByCategory(category: String) : List<MovieEntity>
}