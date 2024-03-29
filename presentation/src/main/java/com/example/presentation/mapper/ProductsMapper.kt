package com.example.presentation.mapper

import com.example.domain.model.ProductItem
import com.example.presentation.model.Product
import javax.inject.Inject

/**
 * This class maps data between a `Products` model and a list of `Product` domain objects.
 *
 * @param productItemMapper The mapper used to convert individual `ProductItemData` objects to `Product` objects.
 */
class ProductsMapper @Inject constructor(private val productItemMapper: ProductItemMapper) {
    fun map(model: List<ProductItem>): List<Product> {
        return with(model) {
            this.map {
                productItemMapper.map(it)
            }
        }
    }
}
