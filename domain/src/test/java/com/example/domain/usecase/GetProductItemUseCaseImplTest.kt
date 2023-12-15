package com.example.domain.usecase

import app.cash.turbine.test
import com.example.domain.common.Result
import com.example.domain.repository.ProductRepository
import com.example.domain.utils.FakeDataProvider
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetProductItemUseCaseImplTest {
    private val mockProductRepository = mockk<ProductRepository>()
    private lateinit var productItemUseCase: GetProductItemUseCaseImpl

    @Before
    fun setUp() {
        productItemUseCase = GetProductItemUseCaseImpl(mockProductRepository, Dispatchers.IO)
    }

    @Test
    fun `Given product Item is available, when productItemUseCase is called, then return Success`() {
        runTest {
            val expected = Result.Success(FakeDataProvider.fakeProduct1)
            coEvery { mockProductRepository.getProductDetails(FakeDataProvider.productId_1) } returns expected

            // When
            val resultFlow = productItemUseCase.invoke(FakeDataProvider.productId_1)

            // Then
            resultFlow.test {
                TestCase.assertEquals(expected, awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `Given product item is available, when invoke is called, then product item is returned`() {
        runTest {
            val expected = FakeDataProvider.fakeProduct2
            coEvery { mockProductRepository.getProductDetails(FakeDataProvider.productId_2) } returns Result.Success(expected)

            // When
            val productList = productItemUseCase.invoke(FakeDataProvider.productId_2)

            // Then
            productList.test {
                val result = awaitItem()
                assert(result == Result.Success(expected))
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `Given product item is not available, when invoke is called, then return an error Result`() {
        runTest {
            val expectedError = Result.Error<Nothing>(Throwable(FakeDataProvider.error_msg), null)
            coEvery { mockProductRepository.getProductDetails(FakeDataProvider.productId_3) } returns expectedError

            // When
            val productList = productItemUseCase.invoke(FakeDataProvider.productId_3)

            productList.test {
                val result = awaitItem()
                assert(result == expectedError)
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}
