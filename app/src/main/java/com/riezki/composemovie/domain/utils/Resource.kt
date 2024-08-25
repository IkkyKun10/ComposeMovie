package com.riezki.composemovie.domain.utils

/**
 * @author riezky maisyar
 */

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val errorType: ErrorType = ErrorType.UNKNOWN_EXCEPTION
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(errorType: ErrorType, message: String, data: T? = null) : Resource<T>(data, message, errorType)
    class Loading<T>(val isLoading: Boolean = true) : Resource<T>(null)
}

enum class ErrorType {
    CLIENT_EXCEPTION,
    SERIALIZATION_EXCEPTION,
    SERVER_EXCEPTION,
    UNKNOWN_EXCEPTION,
    IO_EXCEPTION,
    TIMEOUT_EXCEPTION
}