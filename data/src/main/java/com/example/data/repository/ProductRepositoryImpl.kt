package com.example.data.repository

import com.example.data.mapper.ProductItemDataMapper
import com.example.data.mapper.ProductListDataMapper
import com.example.data.network.ProductApiService
import com.example.domain.common.Result
import com.example.domain.model.ProductItem
import com.example.domain.repository.ProductRepository
import javax.inject.Inject

/**
 * This class implements the `ProductRepository` interface and provides an implementation for fetching product data from a remote API.
 * It utilizes the `ProductApiService` to make network requests and utilizes mappers to convert data models between layers.
 */
class ProductRepositoryImpl @Inject constructor(
    private val productApiService: ProductApiService,
    private val productListDataMapper: ProductListDataMapper,
    private val productItemDataMapper: ProductItemDataMapper,
) : ProductRepository {
    override suspend fun getProducts(): Result<List<ProductItem>> {
        return try {
            val products = productApiService.getProducts()
            Result.Success(productListDataMapper.map(products))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getProductDetails(id: Int): Result<ProductItem> {
        return try {
            val productItem = productApiService.getProductDetail(id)
            Result.Success(productItemDataMapper.map(productItem))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
