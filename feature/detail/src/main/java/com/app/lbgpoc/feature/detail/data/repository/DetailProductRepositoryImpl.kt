package com.app.lbgpoc.feature.detail.data.repository

import com.app.lbgpoc.core.common.Result
import com.app.lbgpoc.feature.detail.data.mapper.toDomain
import com.app.lbgpoc.feature.detail.data.remote.FakeStoreDetailApi
import com.app.lbgpoc.feature.detail.domain.model.DetailProduct
import com.app.lbgpoc.feature.detail.domain.repository.DetailProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DetailProductRepositoryImpl @Inject constructor(
    private val api: FakeStoreDetailApi
) : DetailProductRepository {

    override fun getProductDetails(productId: Int): Flow<Result<DetailProduct>> = flow {
        emit(Result.Loading)
        try {
            val remoteProduct = api.getProductDetails(productId)
            emit(Result.Success(remoteProduct.toDomain()))
        } catch (e: HttpException) {
            emit(Result.Error("Network error occurred: ${e.message()}", e))
        } catch (e: IOException) {
            emit(Result.Error("Please check your internet connection.", e))
        } catch (e: Exception) {
            emit(Result.Error("An unexpected error occurred: ${e.message}", e))
        }
    }
}
