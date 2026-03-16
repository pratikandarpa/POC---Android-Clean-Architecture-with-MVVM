package com.app.lbgpoc.feature.detail.domain.repository

import com.app.lbgpoc.core.common.Result
import com.app.lbgpoc.feature.detail.domain.model.DetailProduct
import kotlinx.coroutines.flow.Flow

interface DetailProductRepository {
    fun getProductDetails(productId: Int): Flow<Result<DetailProduct>>
}
