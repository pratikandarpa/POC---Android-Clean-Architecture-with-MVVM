package com.app.lbgpoc.feature.list.data.mapper

import com.app.lbgpoc.feature.list.data.dto.ProductDto
import com.app.lbgpoc.feature.list.data.dto.RatingDto
import com.app.lbgpoc.feature.list.domain.model.Product
import com.app.lbgpoc.feature.list.domain.model.Rating

fun ProductDto.toDomain(): Product {
    return Product(
        id = this.id,
        title = this.title,
        price = this.price,
        description = this.description,
        category = this.category,
        imageUrl = this.image,
        rating = this.rating.toDomain()
    )
}

fun RatingDto.toDomain(): Rating {
    return Rating(
        rate = this.rate,
        count = this.count
    )
}

