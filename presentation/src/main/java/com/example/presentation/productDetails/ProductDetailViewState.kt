package com.example.presentation.productDetails

import com.example.presentation.base.ViewState
import com.example.presentation.model.Product

/**
 * This sealed interface defines the different states that a Product Detail View can be in.
 */
sealed interface ProductDetailViewState : ViewState {
    object Loading : ProductDetailViewState
    class Success(val data: Product) : ProductDetailViewState
    class Error(val throwable: Throwable) : ProductDetailViewState
}