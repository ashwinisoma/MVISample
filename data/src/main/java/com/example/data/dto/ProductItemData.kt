package com.example.data.dto

import com.google.gson.annotations.SerializedName

/**
 * This class represents data for a product item.*/
data class ProductItemData(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("price")
    val price: Double
)
