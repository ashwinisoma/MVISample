package com.example.presentation.utils

import com.example.domain.model.ProductItem
import com.example.domain.model.Products
import com.example.presentation.model.Product

object FakeDataProvider {
    const val productId_1 = 1
    const val productId_2 = 2
    const val error_msg = "Failed to fetch products"
    val fakeProduct = Product(
        "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
        1,
        "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
        "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
        109.95,
    )

    val fakeProduct2 = Product(
        "Slim-fitting style, contrast raglan long sleeve, three-button henley placket, light weight & soft fabric for breathable and comfortable wearing. And Solid stitched shirts with round neck made for durability and a great fit for casual fashion wear and diehard baseball fans. The Henley style round neckline includes a three-button placket.",
        2,
        "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
        "Mens Casual Premium Slim Fit T-Shirts",
        129.95,
    )

    val fakeProductItem1 = ProductItem(
        id = 1,
        title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
        description = "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
        image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
        price = 109.95,
    )
    val fakeProductItem2 = ProductItem(
        id = 2,
        title = "Mens Casual Premium Slim Fit T-Shirts",
        description = "Slim-fitting style, contrast raglan long sleeve, three-button henley placket, light weight & soft fabric for breathable and comfortable wearing. And Solid stitched shirts with round neck made for durability and a great fit for casual fashion wear and diehard baseball fans. The Henley style round neckline includes a three-button placket.",
        image = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
        price = 129.95,
    )

    val fakeProductResponseList = Products(
        listOf(
            fakeProductItem1,
            fakeProductItem2,
        ),
    )

    val fakeMapProducts = listOf(
        Product(
            "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
            1,
            "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
            "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
            109.95,
        ),
        Product(
            "Slim-fitting style, contrast raglan long sleeve, three-button henley placket, light weight & soft fabric for breathable and comfortable wearing. And Solid stitched shirts with round neck made for durability and a great fit for casual fashion wear and diehard baseball fans. The Henley style round neckline includes a three-button placket.",
            2,
            "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
            "Mens Casual Premium Slim Fit T-Shirts",
            129.95,
        ),
    )
}
