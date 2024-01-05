package com.example.presentation.mapper

import com.example.presentation.model.Product
import com.example.presentation.utils.FakeDataProvider.fakeProduct1
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class ProductItemMapperTest {

    private lateinit var mappedProduct: Product
    @Before
    fun setUp(){
        mappedProduct = ProductItemMapper().map(fakeProduct1)
    }
    @Test
    fun `Given ProductItem is available when we map response then return Product`() {
        assertEquals(mappedProduct.id,fakeProduct1.id)
        assertEquals(mappedProduct.description,fakeProduct1.description)
        assertEquals(mappedProduct.image,fakeProduct1.image)
        assertEquals(mappedProduct.title,fakeProduct1.title)
        assertEquals(mappedProduct.price,fakeProduct1.price)
    }
}