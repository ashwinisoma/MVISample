package com.example.data.di

import com.example.data.network.ProductApiService
import com.example.data.repository.ProductRepositoryImpl
import com.example.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*** This module provides data access layer dependencies for the application.
It relies on the `ProductApiService` to fetch product data from a remote API.*/
@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    // Provides an instance of [ProductRepository]
    // @return A new instance of [ProductRepositoryImpl].
    @Singleton
    @Provides
    fun provideProductRepository(
        productApiService: ProductApiService,
    ): ProductRepository = ProductRepositoryImpl(productApiService)
}
