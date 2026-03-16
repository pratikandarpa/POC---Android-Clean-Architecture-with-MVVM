package com.app.lbgpoc.feature.detail.data.mapper

import com.app.lbgpoc.feature.detail.data.dto.DetailProductDto
import com.app.lbgpoc.feature.detail.domain.model.DetailProduct
import com.app.lbgpoc.feature.detail.domain.model.Rating

fun DetailProductDto.toDomain(): DetailProduct {
    return DetailProduct(
        id = this.id,
        title = this.title,
        price = this.price,
        description = this.description,
        category = this.category,
        imageUrl = this.image,
        rating = Rating(
            rate = this.rating.rate,
            count = this.rating.count
        )
    )
}
