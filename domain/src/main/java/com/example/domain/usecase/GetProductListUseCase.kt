package com.example.domain.usecase

import com.example.domain.common.Result
import com.example.domain.model.ProductItem
import kotlinx.coroutines.flow.Flow

/**
 * This interface defines a use case for fetching a list of all available products.
 *
 * @return A Flow of Result<Products>, where:
 *  * `Success` holds a list of ProductItem objects representing all available products.
 *  * `Error` indicates an error occurred while retrieving the product list.
 */
interface GetProductListUseCase {
    suspend operator fun invoke(): Flow<Result<List<ProductItem>>>
}
