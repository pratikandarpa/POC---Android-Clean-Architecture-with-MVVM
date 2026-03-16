package com.app.lbgpoc.feature.list.domain.usecase

import com.app.lbgpoc.core.common.Result
import com.app.lbgpoc.feature.list.domain.model.ListProduct
import com.app.lbgpoc.feature.list.domain.repository.ListProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ListProductRepository
) {
    operator fun invoke(): Flow<Result<List<ListProduct>>> {
        return repository.getProducts()
    }
}
