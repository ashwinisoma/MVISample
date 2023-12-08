package com.example.domain.usecase

import com.example.domain.model.ProductItem
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.domain.common.Result
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * This class implements the `GetProductItemUseCase` interface and retrieves product details based on its ID.
 *
 * @param productRepository The repository used to access product data.
 */
class GetProductItemUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository
) :
    GetProductItemUseCase {
    override suspend fun invoke(productId: Int): Flow<Result<ProductItem>> {
        return flow {
            val result = productRepository.getProductDetails(productId)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}