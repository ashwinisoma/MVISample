package com.example.presentation.products

import com.example.presentation.base.ViewState
import com.example.presentation.model.Product

/**
 * This sealed interface represents the possible states of the product list view.
 */
sealed interface ProductListViewState : ViewState {
    object Loading : ProductListViewState
    data class Success(val data: List<Product>) : ProductListViewState
    data class Error(val throwable: Throwable) : ProductListViewState
}
