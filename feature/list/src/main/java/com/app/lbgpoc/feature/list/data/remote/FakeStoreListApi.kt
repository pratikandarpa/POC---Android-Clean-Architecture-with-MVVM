package com.app.lbgpoc.feature.list.data.remote

import com.app.lbgpoc.feature.list.data.dto.ListProductDto
import retrofit2.http.GET

interface FakeStoreListApi {
    @GET("products")
    suspend fun getProducts(): List<ListProductDto>
}
