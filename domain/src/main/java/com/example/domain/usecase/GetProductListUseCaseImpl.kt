package com.example.domain.usecase

import com.example.domain.model.Products
import com.example.domain.repository.ProductRepository
import javax.inject.Inject
import com.example.domain.common.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * This class implements the `GetProductListUseCase` interface, responsible for retrieving a list of product items.
 *
 * @param productRepository The repository used to access product data.
 */
class GetProductListUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository
) : GetProductListUseCase {
    override suspend fun invoke(): Flow<Result<Products>> {
        return flow {
            val result = productRepository.getProducts()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}
