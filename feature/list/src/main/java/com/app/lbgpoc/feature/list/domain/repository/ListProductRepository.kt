package com.app.lbgpoc.feature.list.domain.repository

import com.app.lbgpoc.core.common.Result
import com.app.lbgpoc.feature.list.domain.model.ListProduct
import kotlinx.coroutines.flow.Flow

interface ListProductRepository {
    fun getProducts(): Flow<Result<List<ListProduct>>>
}
