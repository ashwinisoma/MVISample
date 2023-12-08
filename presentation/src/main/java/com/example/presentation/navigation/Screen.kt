package com.example.presentation.navigation

import com.example.presentation.constant.productDetail
import com.example.presentation.constant.productList

/**
 * This sealed class represents different screens in the application.
 * Each screen has a unique route string that identifies it.
 */
sealed class Screen(val route: String) {
    object ProductListScreen : Screen(productList)
    object ProductDetailsScreen : Screen(productDetail)
}
