package com.example.domain.repository

import com.example.domain.common.Result
import com.example.domain.model.ProductItem

/**
 * This interface defines the contract for interacting with a product repository.
 * It provides methods for retrieving product data from a data source.
 */
interface ProductRepository {
    suspend fun getProducts(): Result<List<ProductItem>>
    suspend fun getProductDetails(id: Int): Result<ProductItem>
}
