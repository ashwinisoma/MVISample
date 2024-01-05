package com.example.presentation.mapper

import com.example.presentation.model.Product
import com.example.presentation.utils.FakeDataProvider
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class ProductsMapperTest {
    private lateinit var mockProductItemMapper: ProductItemMapper

    @Before
    fun setUp(){
        mockProductItemMapper = mockk()
    }

    @Test
    fun `Given Products are available when we map response then return List of Product`() {
        val model = FakeDataProvider.fakeProductResponseList
        every { mockProductItemMapper.map(any()) } returns Product(
            id = FakeDataProvider.fakeProduct1.id,
            description = FakeDataProvider.fakeProduct1.description,
            price = FakeDataProvider.fakeProduct1.price,
            image = FakeDataProvider.fakeProduct1.image,
            title = FakeDataProvider.fakeProduct1.title
        )
        val mappedProducts = ProductsMapper(mockProductItemMapper).map(model)
        assertEquals(FakeDataProvider.products_size, mappedProducts.size)
    }
}