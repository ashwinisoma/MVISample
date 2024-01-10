package com.example.data.mapper

import com.example.data.utils.FakeDataProvider
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class ProductItemDataMapperTest {
    private lateinit var productItemDataMapper: ProductItemDataMapper

    @Before
    fun setUp() {
        productItemDataMapper = ProductItemDataMapper()
    }

    @Test
    fun `Given list of ProductItemData is available when we map response then return ProductItem`() {
        val expectedResult = FakeDataProvider.fakeProduct1
        val product = FakeDataProvider.fakeProductItemData1

        val actualResult = productItemDataMapper.map(product)

        assertEquals(actualResult, expectedResult)

    }
}
