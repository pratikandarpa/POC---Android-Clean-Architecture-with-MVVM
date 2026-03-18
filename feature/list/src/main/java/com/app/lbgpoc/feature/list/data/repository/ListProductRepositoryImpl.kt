package com.app.lbgpoc.feature.list.data.repository

import com.app.lbgpoc.core.common.Result
import com.app.lbgpoc.feature.list.data.mapper.toDataError
import com.app.lbgpoc.feature.list.data.mapper.toDomain
import com.app.lbgpoc.feature.list.data.remote.FakeStoreListApi
import com.app.lbgpoc.feature.list.domain.model.Product
import com.app.lbgpoc.feature.list.domain.repository.ListProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListProductRepositoryImpl @Inject constructor(
    private val api: FakeStoreListApi
) : ListProductRepository {

    override fun getProducts(): Flow<Result<List<Product>>> = flow {
        emit(Result.Loading)
        val remoteProducts = api.getProducts()
        emit(Result.Success(remoteProducts.map { it.toDomain() }))
    }.catch { e ->
        emit(Result.Error(e.toDataError(), e))
    }
}
