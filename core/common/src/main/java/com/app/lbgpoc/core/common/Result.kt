package com.app.lbgpoc.core.common

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: AppError, val exception: Throwable? = null) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
