package com.example.presentation.products

import com.example.presentation.base.ViewIntent

/**
 * This sealed interface represents the different types of intents that can be sent to the ProductListViewModel.
 */
sealed interface ProductListViewIntent : ViewIntent {
    object FetchProductListList : ProductListViewIntent
}