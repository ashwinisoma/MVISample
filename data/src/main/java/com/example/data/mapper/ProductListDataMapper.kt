package com.example.data.mapper

import com.example.data.dto.ProductItemData
import com.example.domain.model.ProductItem
import javax.inject.Inject

/**
 * This class is responsible for converting a list of `ProductItemData` models
 * into a `Products` domain object.
 */

class ProductListDataMapper @Inject constructor() {
    fun map(products: List<ProductItemData>): List<ProductItem> {
        return products.map { product ->
            with(product) {
                ProductItem(
                    description,
                    id,
                    image,
                    price,
                    title,
                )
            }
        }
    }
}
