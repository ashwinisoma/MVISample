package com.example.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * This data class represents a product entity in the application.
 * This class is annotated with @Parcelize to enable efficient serialization and deserialization when necessary.
 */
@Parcelize
data class Product(
    val description: String?,
    val id: Int,
    val image: String?,
    val title: String?,
    val price: Double
) : Parcelable
