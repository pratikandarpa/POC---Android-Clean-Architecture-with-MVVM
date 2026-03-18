package com.app.lbgpoc.core.common

sealed interface AppError

sealed interface DataError : AppError {
    enum class Network : DataError {
        SERVICE_UNAVAILABLE,
        CLIENT_ERROR,
        SERVER_ERROR,
        NO_INTERNET,
        UNKNOWN
    }
}
