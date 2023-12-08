package com.example.domain.usecase

import app.cash.turbine.test
import com.example.domain.common.Result
import com.example.domain.repository.ProductRepository
import com.example.domain.utils.FakeDataProvider
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetProductItemUseCaseTest {
    private val mockProductRepository = mockk<ProductRepository>()
    private lateinit var productItemUseCase: GetProductItemUseCaseImpl

    @Before
    fun setUp() {
        productItemUseCase = GetProductItemUseCaseImpl(mockProductRepository)
    }

    @Test
    fun `Given product Item is available, when productItemUseCase is called, then return Success`() {
        runTest {
            val id = 1
            val expected = Result.Success(FakeDataProvider.fakeProduct1)
            coEvery { mockProductRepository.getProductDetails(id) } returns expected

            // When
            val resultFlow = productItemUseCase(id)

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
            val id = 2
            val expected = FakeDataProvider.fakeProduct2
            coEvery { mockProductRepository.getProductDetails(id) } returns Result.Success(expected)

            // When
            val productList = productItemUseCase.invoke(id)

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
            val id = 3
            val expectedError = Result.Error<Nothing>(Throwable("Failed to fetch products"), null)
            coEvery { mockProductRepository.getProductDetails(id) } returns expectedError

            // When
            val productList = productItemUseCase.invoke(id)

            productList.test {
                val result = awaitItem()
                assert(result == expectedError)
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}
