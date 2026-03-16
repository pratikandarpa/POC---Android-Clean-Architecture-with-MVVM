package com.app.lbgpoc.feature.list.data.mapper

import com.app.lbgpoc.feature.list.data.dto.ListProductDto
import com.app.lbgpoc.feature.list.domain.model.ListProduct

fun ListProductDto.toDomain(): ListProduct {
    return ListProduct(
        id = this.id,
        title = this.title,
        price = this.price,
        imageUrl = this.image
    )
}
