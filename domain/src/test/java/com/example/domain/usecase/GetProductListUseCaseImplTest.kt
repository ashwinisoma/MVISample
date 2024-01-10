package com.example.domain.usecase

import app.cash.turbine.test
import com.example.domain.common.Result
import com.example.domain.repository.ProductRepository
import com.example.domain.utils.FakeDataProvider
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetProductListUseCaseImplTest {
    private val mockProductRepository = mockk<ProductRepository>()
    private lateinit var productListUseCase: GetProductListUseCaseImpl

    @Before
    fun setUp() {
        productListUseCase = GetProductListUseCaseImpl(mockProductRepository, Dispatchers.IO)
    }

    @Test
    fun `Given product list is available, when productsUseCase is called, then return Success`() {
        runTest {
            val expected = Result.Success(FakeDataProvider.fakeProductResponseList)
            coEvery { mockProductRepository.getProducts() } returns expected

            // When
            val resultFlow = productListUseCase.invoke()

            // Then
            resultFlow.test {
                assertEquals(expected, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `Given product list is available, when invoke is called, then product list is returned`() {
        runTest {
            val expected = FakeDataProvider.fakeProductResponseList
            coEvery { mockProductRepository.getProducts() } returns Result.Success(expected)

            // When
            val productList = productListUseCase.invoke()

            // Then
            productList.test {
                val result = awaitItem()
                assert(result == Result.Success(expected))
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `Given product list is not available, when invoke is called, then return an error Result`() {
        runTest {
            val expectedError = Result.Error<Nothing>(Throwable(FakeDataProvider.error_msg), null)
            coEvery { mockProductRepository.getProducts() } returns expectedError

            // When
            val productList = productListUseCase.invoke()

            productList.test {
                val result = awaitItem()
                assert(result == expectedError)
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}
