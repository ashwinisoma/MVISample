package com.example.data.mapper

import com.example.data.utils.FakeDataProvider
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class ProductListDataMapperTest {
    private lateinit var productListDataMapper: ProductListDataMapper

    @Before
    fun setUp() {
        productListDataMapper = ProductListDataMapper()
    }


    @Test
    fun `Given list of ProductItemData is available when we map response then return list of ProductItem`() {
        val product1 = FakeDataProvider.fakeProductItemData1
        val product2 = FakeDataProvider.fakeProductItemData2
        val productList = listOf(product1, product2)

        val actualResult = productListDataMapper.map(productList)
        val expectedResult = FakeDataProvider.fakeProducts

        assertEquals(FakeDataProvider.productsListSize, actualResult.products.size)
        assertEquals(expectedResult, actualResult)
    }
}
