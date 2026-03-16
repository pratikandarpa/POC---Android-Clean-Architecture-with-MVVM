package com.app.lbgpoc.feature.list.data.repository

import com.app.lbgpoc.core.common.Result
import com.app.lbgpoc.feature.list.data.mapper.toDomain
import com.app.lbgpoc.feature.list.data.remote.FakeStoreListApi
import com.app.lbgpoc.feature.list.domain.model.ListProduct
import com.app.lbgpoc.feature.list.domain.repository.ListProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ListProductRepositoryImpl @Inject constructor(
    private val api: FakeStoreListApi
) : ListProductRepository {

    override fun getProducts(): Flow<Result<List<ListProduct>>> = flow {
        emit(Result.Loading)
        try {
            val remoteProducts = api.getProducts()
            emit(Result.Success(remoteProducts.map { it.toDomain() }))
        } catch (e: HttpException) {
            emit(Result.Error("Network error occurred: ${e.message()}", e))
        } catch (e: IOException) {
            emit(Result.Error("Please check your internet connection.", e))
        } catch (e: Exception) {
            emit(Result.Error("An unexpected error occurred: ${e.message}", e))
        }
    }
}
