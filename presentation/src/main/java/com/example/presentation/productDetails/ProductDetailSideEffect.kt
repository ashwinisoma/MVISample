package com.example.presentation.productDetails

import com.example.presentation.base.SideEffect

/**
 * This interface defines a sealed interface to represent different side effects related to the product detail screen.
 *
 * It extends the `SideEffect` interface and specifies various sub-interfaces for various types of actions.
 */
sealed interface ProductDetailSideEffect : SideEffect