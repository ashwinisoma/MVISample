package com.example.data.mapper

import com.example.data.dto.ProductItemData
import com.example.domain.model.ProductItem
import javax.inject.Inject

/**
 * This class is responsible for mapping data between a `ProductItemData` model
 * and a `ProductItem` domain object.
 */
class ProductItemDataMapper @Inject constructor() {
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
