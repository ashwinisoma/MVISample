package com.example.data.mapper

import com.example.data.dto.ProductItemData
import com.example.domain.model.ProductItem
import com.example.domain.model.Products

/**
 * This class is responsible for converting a list of `ProductItemData` models
 * into a `Products` domain object.
 */
class ProductListDataMapper {
    fun map(products: List<ProductItemData>): Products {
        val productList = products.map { product ->
            ProductItem(
                id = product.id,
                description = product.description,
                image = product.image,
                title = product.title,
                price = product.price
            )
        }
        return Products(productList)
    }
}
