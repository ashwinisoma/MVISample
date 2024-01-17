package com.example.data.repository

import com.example.data.mapper.ProductItemDataMapper
import com.example.data.mapper.ProductListDataMapper
import com.example.data.network.ProductApiService
import com.example.data.utils.FakeDataProvider
import com.example.domain.common.Result
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ProductRepositoryImplTest {
    private lateinit var mockProductApiService: ProductApiService
    private lateinit var mockProductListDataMapper: ProductListDataMapper
    private lateinit var mockProductItemDataMapper: ProductItemDataMapper
    private lateinit var productRepository: ProductRepositoryImpl

    @Before
    fun setUp() {
        mockProductApiService = mockk()
        mockProductListDataMapper = mockk()
        mockProductItemDataMapper = mockk()
        productRepository = ProductRepositoryImpl(
            mockProductApiService,
            mockProductListDataMapper,
            mockProductItemDataMapper,
        )
    }

    @Test
    fun `Given product list is available when getProducts is called, then Return Success with List of Products`() {
        runTest {
            coEvery { mockProductApiService.getProducts() } returns FakeDataProvider.fakeListOfProductItemData
            coEvery { mockProductListDataMapper.map(FakeDataProvider.fakeListOfProductItemData) } returns FakeDataProvider.fakeListOfProducts


            // When
            val result = productRepository.getProducts()

            // Verify success and mapped data
            assertEquals(
                Result.Success(FakeDataProvider.fakeListOfProducts),result
            )
        }
    }

    @Test
    fun `Given product list is not available when getProducts is called, then Return Error`() {
        runTest {
            coEvery { mockProductApiService.getProducts() } throws Exception(FakeDataProvider.error_msg)

            // Execute function and collect result
            val result = productRepository.getProducts()

            // Verify error
            assertTrue(result is Result.Error)
        }
    }

    @Test
    fun `Given product Item is available when getProductDetail is called, then Return Success with  Product Item`() {
        runTest {
            coEvery { mockProductApiService.getProductDetail(FakeDataProvider.productId) } returns FakeDataProvider.fakeProductItemData1
            coEvery { mockProductItemDataMapper.map(FakeDataProvider.fakeProductItemData1) } returns FakeDataProvider.fakeProduct1

            // When
            val result = productRepository.getProductDetails(FakeDataProvider.productId)

            // Verify success and mapped data
            assertEquals(
                Result.Success(FakeDataProvider.fakeProduct1),result
            )
        }
    }

    @Test
    fun `Given product item is not available when getProductDetail is called, then Return Error`() {
        runTest {
            coEvery { mockProductApiService.getProductDetail(FakeDataProvider.productId) } throws Exception(FakeDataProvider.error_msg)

            // Execute function and collect result
            val result = productRepository.getProductDetails(FakeDataProvider.productId)

            // Verify error
            assertTrue(result is Result.Error)
        }
    }
}
