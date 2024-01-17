package com.example.domain.utils

import com.example.domain.model.ProductItem

object FakeDataProvider {

    const val productId_1 = 1
    const val productId_2 = 2
    const val productId_3 = 3
    const val error_msg = "Failed to fetch products"


    val fakeProduct1 = ProductItem(
        id = 1,
        title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
        description = "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
        image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
        price = 109.95,
    )
    val fakeProduct2 = ProductItem(
        id = 2,
        title = "Mens Casual Premium Slim Fit T-Shirts",
        description = "Slim-fitting style, contrast raglan long sleeve, three-button henley placket, light weight & soft fabric for breathable and comfortable wearing. And Solid stitched shirts with round neck made for durability and a great fit for casual fashion wear and diehard baseball fans. The Henley style round neckline includes a three-button placket.",
        image = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
        price = 129.95,
    )

    val fakeProductResponseList =
        listOf(
            fakeProduct1,
            fakeProduct2,
        )
}
