package com.riezki.composemovie.di

import com.riezki.composemovie.R
import com.riezki.composemovie.core.repository.MovieRepositoryImpl
import com.riezki.composemovie.domain.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author riezky maisyar
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}