package com.example.presentation.mapper

import com.example.presentation.utils.FakeDataProvider
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class ProductsMapperTest {
    private lateinit var productsMapper: ProductsMapper
    private lateinit var mockProductItemMapper: ProductItemMapper

    @Before
    fun setUp(){
        mockProductItemMapper = mockk()
        productsMapper = ProductsMapper(mockProductItemMapper)
    }

    @Test
    fun `Given Products are available when we map response then return List of Product`() {

        val fakeProductItem1 = FakeDataProvider.fakeProductItem1
        val fakeProductItem2 = FakeDataProvider.fakeProductItem2
        val fakeProductsModel = FakeDataProvider.fakeProductResponseList
        val expectedResult = FakeDataProvider.fakeMapProducts

        every { mockProductItemMapper.map(fakeProductItem1) } returns FakeDataProvider.fakeProduct
        every { mockProductItemMapper.map(fakeProductItem2) } returns FakeDataProvider.fakeProduct2

        // When
        val actualResult = productsMapper.map(fakeProductsModel)

        // Then
        assertEquals(FakeDataProvider.productsListSize, actualResult.size)
        assertEquals(expectedResult, actualResult)
    }
}