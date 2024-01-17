package com.example.domain.usecase

import app.cash.turbine.test
import com.example.domain.CoroutinesTestRule
import com.example.domain.common.Result
import com.example.domain.repository.ProductRepository
import com.example.domain.utils.FakeDataProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
@ExperimentalCoroutinesApi
class GetProductListUseCaseImplTest {
    private lateinit var mockProductRepository :ProductRepository
    private lateinit var productListUseCase: GetProductListUseCaseImpl

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setUp() {
        mockProductRepository = mockk()
        productListUseCase = GetProductListUseCaseImpl(mockProductRepository, coroutinesTestRule.dispatcher)
    }

    @Test
    fun `Given product list is available, when invoke is called, then product list is returned`() {
        runTest {
            val expected = FakeDataProvider.fakeProductResponseList
            coEvery { mockProductRepository.getProducts() } returns Result.Success(expected)

            // When
            val productList = productListUseCase()

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
            val expectedError = Result.Error<Nothing>(Throwable(FakeDataProvider.error_msg))
            coEvery { mockProductRepository.getProducts() } returns expectedError

            // When
            val productList = productListUseCase()

            productList.test {
                val result = awaitItem()
                assert(result == expectedError)
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}
