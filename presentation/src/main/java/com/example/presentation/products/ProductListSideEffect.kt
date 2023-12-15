package com.example.presentation.products

import com.example.presentation.base.SideEffect

/**
 * This sealed interface represents different side effects related to the product list feature.
 */
sealed interface ProductListSideEffect : SideEffect {
    class NavigateToProductDetails(val id: Int) : ProductListSideEffect
}
