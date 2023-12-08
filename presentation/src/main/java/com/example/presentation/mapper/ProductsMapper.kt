package com.example.presentation.mapper

import com.example.domain.model.Products
import com.example.presentation.model.Product
import javax.inject.Inject

/**
 * This class maps data between a `Products` model and a list of `Product` domain objects.
 *
 * @param productItemMapper The mapper used to convert individual `ProductItemData` objects to `Product` objects.
 */
class ProductsMapper @Inject constructor(private val productItemMapper: ProductItemMapper) {
    fun map(model: Products): List<Product> {
        return with(model) {
            products.map {
                productItemMapper.map(it)
            }
        }
    }
}
