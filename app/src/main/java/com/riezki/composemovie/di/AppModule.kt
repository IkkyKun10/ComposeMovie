package com.riezki.composemovie.di

import android.content.Context
import androidx.room.Room
import com.riezki.composemovie.core.local.db.MovieDb
import com.riezki.composemovie.core.remote.KtorClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author riezky maisyar
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideKtorClient(@ApplicationContext context: Context) : KtorClient {
        return KtorClient(context)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : MovieDb {
        return Room.databaseBuilder(
            context,
            MovieDb::class.java,
            "movie.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}