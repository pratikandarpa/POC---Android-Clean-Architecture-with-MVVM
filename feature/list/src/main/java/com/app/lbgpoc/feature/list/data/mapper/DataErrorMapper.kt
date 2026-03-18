package com.app.lbgpoc.feature.list.data.mapper

import com.app.lbgpoc.core.common.DataError
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toDataError(): DataError {
    return when (this) {
        is HttpException -> {
            when (this.code()) {
                in 400..499 -> DataError.Network.CLIENT_ERROR
                in 500..599 -> DataError.Network.SERVER_ERROR
                else -> DataError.Network.UNKNOWN
            }
        }
        is IOException -> DataError.Network.NO_INTERNET
        else -> DataError.Network.UNKNOWN
    }
}
