package com.example.domain.usecase

import com.example.domain.common.Result
import com.example.domain.model.Products
import com.example.domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * This class implements the `GetProductListUseCase` interface, responsible for retrieving a list of product items.
 *
 * @param productRepository The repository used to access product data.
 */
class GetProductListUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository,
    private val dispatcher: CoroutineDispatcher,
) : GetProductListUseCase {
    override suspend fun invoke(): Flow<Result<Products>> {
        return flow {
            val result = productRepository.getProducts()
            emit(result)
        }.flowOn(dispatcher)
    }
}
