package com.example.domain.common

/**
 *  Sealed class represents the result of an operation, either a success with a value or an error.*/
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val error: Throwable, val errorData: Any? = null) : Result<T>()
}
