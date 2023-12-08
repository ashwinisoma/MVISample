package com.example.presentation.productDetails

import com.example.presentation.base.ViewIntent

/**
 * This sealed interface defines the different intents that a user can trigger in the product details view.
 *
 * @see ViewIntent
 */
sealed interface ProductDetailViewIntent : ViewIntent {
    class FetchProductDetail(val id: Int) : ProductDetailViewIntent
}
