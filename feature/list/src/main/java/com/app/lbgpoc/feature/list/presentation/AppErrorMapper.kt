package com.app.lbgpoc.feature.list.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.app.lbgpoc.core.common.AppError
import com.app.lbgpoc.core.common.DataError
import com.app.lbgpoc.feature.list.R

@Composable
fun AppError.asUiText(): String {
    return when (this) {
        is DataError.Network -> {
            val resId = when (this) {
                DataError.Network.SERVICE_UNAVAILABLE -> R.string.error_server
                DataError.Network.CLIENT_ERROR -> R.string.error_client
                DataError.Network.SERVER_ERROR -> R.string.error_server
                DataError.Network.NO_INTERNET -> R.string.error_no_internet
                DataError.Network.PAYLOAD_TOO_LARGE -> R.string.error_client
                DataError.Network.REQUEST_TIMEOUT -> R.string.error_timeout
                DataError.Network.SERIALIZATION -> R.string.error_unexpected
                DataError.Network.UNKNOWN -> R.string.error_unexpected
            }
            stringResource(resId)
        }
        is DataError.Local -> {
            stringResource(R.string.error_unexpected)
        }
        else -> stringResource(R.string.error_unknown)
    }
}
