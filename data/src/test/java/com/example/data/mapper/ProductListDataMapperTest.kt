package com.example.data.mapper

import com.example.data.dto.ProductItemData
import com.example.data.utils.FakeDataProvider
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ProductListDataMapperTest {
    @Test
    fun `Given list of ProductItemData is available when we map response then return list of ProductItem`() {
        val product1 = FakeDataProvider.fakeProductItemData1
        val product2 = FakeDataProvider.fakeProductItemData2
        val productList = listOf(product1, product2)

        val mappedProducts = ProductListDataMapper().map(productList)

        assertEquals(mappedProducts.products.size, 2)
        assertEquals(mappedProducts.products[0].description, product1.description)
        assertEquals(mappedProducts.products[0].id, product1.id)
        assertEquals(mappedProducts.products[0].image, product1.image)
        assertEquals(mappedProducts.products[0].price, product1.price)
        assertEquals(mappedProducts.products[0].title, product1.title)
        assertEquals(mappedProducts.products[1].description, product2.description)
        assertEquals(mappedProducts.products[1].id, product2.id)
        assertEquals(mappedProducts.products[1].image, product2.image)
        assertEquals(mappedProducts.products[1].price, product2.price)
        assertEquals(mappedProducts.products[1].title, product2.title)
    }

    @Test
    fun `Given list of ProductItemData is not available when we map response then return empty`() {
        val productList = emptyList<ProductItemData>()

        val mappedProducts = ProductListDataMapper().map(productList)

        assertEquals(mappedProducts.products.size, 0)
    }
}
