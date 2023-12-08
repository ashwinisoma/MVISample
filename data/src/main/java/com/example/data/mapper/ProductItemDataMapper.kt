package com.example.data.mapper

import com.example.data.dto.ProductItemData
import com.example.domain.model.ProductItem

/**
 * This class is responsible for mapping data between a `ProductItemData` model
 * and a `ProductItem` domain object.
 */
class ProductItemDataMapper {
    fun map(model: ProductItemData) = with(model) {
        ProductItem(
            description,
            id,
            image,
            price,
            title,
        )
    }
}
