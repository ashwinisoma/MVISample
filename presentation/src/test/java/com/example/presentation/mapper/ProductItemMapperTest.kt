package com.example.presentation.mapper

import com.example.presentation.utils.FakeDataProvider
import com.example.presentation.utils.FakeDataProvider.fakeProductItem1
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class ProductItemMapperTest {

    private lateinit var productItemMapper: ProductItemMapper
    @Before
    fun setUp(){
        productItemMapper = ProductItemMapper()
    }
    @Test
    fun `Given ProductItem is available when we map response then return Product`() {

        val expectedResult = FakeDataProvider.fakeProduct
        val actualResult = productItemMapper.map(fakeProductItem1)

        assertEquals(expectedResult, actualResult)
    }
}