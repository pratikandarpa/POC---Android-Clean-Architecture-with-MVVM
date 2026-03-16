package com.app.lbgpoc.feature.detail.domain.usecase

import com.app.lbgpoc.core.common.Result
import com.app.lbgpoc.feature.detail.domain.model.DetailProduct
import com.app.lbgpoc.feature.detail.domain.repository.DetailProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductDetailsUseCase @Inject constructor(
    private val repository: DetailProductRepository
) {
    operator fun invoke(productId: Int): Flow<Result<DetailProduct>> {
        return repository.getProductDetails(productId)
    }
}
