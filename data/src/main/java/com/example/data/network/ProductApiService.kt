package com.example.data.network

import com.example.data.dto.ProductItemData
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * This interface defines the API service for interacting with the product data.
 */
interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): List<ProductItemData>

    @GET("products/{id}")
    suspend fun getProductDetail(@Path("id") id: Int): ProductItemData
}