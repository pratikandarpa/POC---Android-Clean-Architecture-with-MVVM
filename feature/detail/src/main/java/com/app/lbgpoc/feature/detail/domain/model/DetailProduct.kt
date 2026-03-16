package com.app.lbgpoc.feature.detail.domain.model

data class DetailProduct(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val imageUrl: String,
    val rating: Rating
)

data class Rating(
    val rate: Double,
    val count: Int
)
