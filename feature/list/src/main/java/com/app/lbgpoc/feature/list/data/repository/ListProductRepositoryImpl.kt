package com.app.lbgpoc.feature.list.data.repository

import com.app.lbgpoc.core.common.DataError
import com.app.lbgpoc.core.common.Result
import com.app.lbgpoc.feature.list.data.mapper.toDomain
import com.app.lbgpoc.feature.list.data.remote.FakeStoreListApi
import com.app.lbgpoc.feature.list.domain.model.Product
import com.app.lbgpoc.feature.list.domain.repository.ListProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ListProductRepositoryImpl @Inject constructor(
    private val api: FakeStoreListApi
) : ListProductRepository {

    override fun getProducts(): Flow<Result<List<Product>>> = flow {
        emit(Result.Loading)
        try {
            val remoteProducts = api.getProducts()
            emit(Result.Success(remoteProducts.map { it.toDomain() }))
        } catch (e: HttpException) {
            val error = when (e.code()) {
                408 -> DataError.Network.REQUEST_TIMEOUT
                413 -> DataError.Network.PAYLOAD_TOO_LARGE
                in 400..499 -> DataError.Network.CLIENT_ERROR
                in 500..599 -> DataError.Network.SERVER_ERROR
                else -> DataError.Network.UNKNOWN
            }
            emit(Result.Error(error, e))
        } catch (e: IOException) {
            emit(Result.Error(DataError.Network.NO_INTERNET, e))
        } catch (e: Exception) {
            emit(Result.Error(DataError.Network.UNKNOWN, e))
        }
    }
}
