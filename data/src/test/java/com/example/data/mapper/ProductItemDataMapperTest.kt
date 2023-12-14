package com.example.data.mapper

import com.example.data.utils.FakeDataProvider
import junit.framework.TestCase
import org.junit.Test

class ProductItemDataMapperTest {

    @Test
    fun `Given list of ProductItemData is available when we map response then return ProductItem`() {
        val product1 = FakeDataProvider.fakeProductItemData1

        val mappedProductItem = ProductItemDataMapper().map(product1)

        TestCase.assertEquals(mappedProductItem.description, product1.description)
        TestCase.assertEquals(mappedProductItem.id, product1.id)
        TestCase.assertEquals(mappedProductItem.image, product1.image)
        TestCase.assertEquals(mappedProductItem.price, product1.price)
        TestCase.assertEquals(mappedProductItem.title, product1.title)
    }
}
