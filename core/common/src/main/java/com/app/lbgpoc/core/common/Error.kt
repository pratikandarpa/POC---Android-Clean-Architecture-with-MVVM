package com.app.lbgpoc.core.common

sealed interface AppError

sealed interface DataError : AppError {
    enum class Network : DataError {
        SERVICE_UNAVAILABLE,
        CLIENT_ERROR,
        SERVER_ERROR,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        REQUEST_TIMEOUT,
        SERIALIZATION,
        UNKNOWN
    }
    enum class Local : DataError {
        DISK_FULL,
        UNKNOWN
    }
}
