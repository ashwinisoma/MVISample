package com.example.domain.model

/**
 * This class represents a product item within the application domain.
 **/
data class ProductItem(
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val title: String
)