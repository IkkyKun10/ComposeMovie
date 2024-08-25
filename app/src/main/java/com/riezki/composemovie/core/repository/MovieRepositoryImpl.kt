package com.riezki.composemovie.core.repository

import com.riezki.composemovie.core.local.db.MovieDb
import com.riezki.composemovie.core.mappers.toEntity
import com.riezki.composemovie.core.mappers.toMovie
import com.riezki.composemovie.core.remote.KtorClient
import com.riezki.composemovie.domain.MovieRepository
import com.riezki.composemovie.domain.model.Movie
import com.riezki.composemovie.domain.utils.ErrorType
import com.riezki.composemovie.domain.utils.Resource
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerializationException
import javax.inject.Inject

/**
 * @author riezky maisyar
 */

class MovieRepositoryImpl @Inject constructor(
    private val ktorClient: KtorClient,
    private val movieDb: MovieDb
) : MovieRepository {

    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))

            val localListMovie = movieDb.movieDao.getMovieListByCategory(category)

            val shouldLoadLocalMovie = localListMovie.isNotEmpty() && !forceFetchFromRemote
            if (shouldLoadLocalMovie) {
                emit(
                    Resource.Success(
                        localListMovie.map {
                            it.toMovie(category)
                        }
                    )
                )
                emit(Resource.Loading(false))
                return@flow
            }

            val movieListFromApi = try {
                ktorClient.getMovieList(category, page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(
                    Resource.Error(ErrorType.IO_EXCEPTION, e.message.toString(), null)
                )
                return@flow
            } catch (e: ClientRequestException) {
                e.printStackTrace()
                emit(
                    Resource.Error(ErrorType.CLIENT_EXCEPTION, e.message, null)
                )
                return@flow
            } catch(e: ServerResponseException) {
                e.printStackTrace()
                emit(
                    Resource.Error(ErrorType.SERVER_EXCEPTION, e.message, null)
                )
                return@flow
            } catch(e: SerializationException) {
                e.printStackTrace()
                emit(
                    Resource.Error(ErrorType.SERIALIZATION_EXCEPTION, e.message.toString(), null)
                )
                return@flow
            } catch (e: HttpRequestTimeoutException) {
                e.printStackTrace()
                emit(
                    Resource.Error(ErrorType.TIMEOUT_EXCEPTION, e.message.toString(), null)
                )
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    Resource.Error(ErrorType.UNKNOWN_EXCEPTION, e.message.toString(), null)
                )
                return@flow
            }

            val movieEntities = movieListFromApi.results.let {
                it.map { movieDto ->
                    movieDto.toEntity(category)
                }
            }

            movieDb.movieDao.upsertAll(movieEntities)

            emit(Resource.Success(
                movieEntities.map { it.toMovie(category) }
            ))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(true))
            val movieEntity = movieDb.movieDao.getMovieById(id)

            if (movieEntity != null) {
                emit(Resource.Success(
                    movieEntity.toMovie(movieEntity.category.toString())
                ))
            }

            emit(Resource.Error(
                ErrorType.UNKNOWN_EXCEPTION,
                "Movie not found",
                null
            ))
            emit(Resource.Loading(false))
        }
    }
}