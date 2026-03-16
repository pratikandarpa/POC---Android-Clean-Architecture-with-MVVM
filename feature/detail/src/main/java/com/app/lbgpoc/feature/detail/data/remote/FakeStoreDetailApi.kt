package com.app.lbgpoc.feature.detail.data.remote

import com.app.lbgpoc.feature.detail.data.dto.DetailProductDto
import retrofit2.http.GET
import retrofit2.http.Path

interface FakeStoreDetailApi {
    @GET("products/{id}")
    suspend fun getProductDetails(@Path("id") id: Int): DetailProductDto
}
