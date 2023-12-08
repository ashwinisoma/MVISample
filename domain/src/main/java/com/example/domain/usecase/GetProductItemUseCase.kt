package com.example.domain.usecase

import com.example.domain.model.ProductItem
import com.example.domain.common.Result
import kotlinx.coroutines.flow.Flow

/**
 * This interface defines a use case for fetching a product item by its ID.
 *
 *
 * @return A Flow of Result<ProductItem>, where:
 *   * `Success` holds the retrieved ProductItem.
 *   * `Error` indicates an error occurred while retrieving the product item.
 */
interface GetProductItemUseCase {
    suspend operator fun invoke(productId: Int): Flow<Result<ProductItem>>
}