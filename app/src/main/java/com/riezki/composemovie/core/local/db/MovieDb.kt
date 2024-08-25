package com.riezki.composemovie.core.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.riezki.composemovie.core.local.model.MovieEntity

/**
 * @author riezky maisyar
 */

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDb : RoomDatabase() {
    abstract val movieDao: MovieDao
}