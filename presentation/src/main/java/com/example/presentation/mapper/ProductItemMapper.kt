package com.example.presentation.mapper

import com.example.domain.model.ProductItem
import com.example.presentation.model.Product
import javax.inject.Inject

/**
 * This class maps data between a `ProductItem` model and a `Product` domain object.
 */
class ProductItemMapper @Inject constructor() {
    fun map(model: ProductItem): Product {
        return with(model) {
            Product(
                id = id,
                description = description,
                image = image,
                title = title,
                price = price,
            )
        }
    }
}
