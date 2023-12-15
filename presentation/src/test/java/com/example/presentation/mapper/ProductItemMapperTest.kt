package com.example.presentation.mapper

import com.example.presentation.utils.FakeDataProvider.fakeProduct1
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ProductItemMapperTest {
    @Test
    fun `Given ProductItem is available when we map response then return Product`() {

        val mappedProduct = ProductItemMapper().map(fakeProduct1)

        assertEquals(mappedProduct.id,fakeProduct1.id)
        assertEquals(mappedProduct.description,fakeProduct1.description)
        assertEquals(mappedProduct.image,fakeProduct1.image)
        assertEquals(mappedProduct.title,fakeProduct1.title)
        assertEquals(mappedProduct.price,fakeProduct1.price)
    }
}