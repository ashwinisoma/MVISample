package com.example.domain.di

import com.example.domain.repository.ProductRepository
import com.example.domain.usecase.GetProductItemUseCase
import com.example.domain.usecase.GetProductItemUseCaseImpl
import com.example.domain.usecase.GetProductListUseCaseImpl
import com.example.domain.usecase.GetProductListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * This module provides use cases for retrieving and managing product data in the application.
 */
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun providesProductsUseCase(
        productRepository: ProductRepository
    ): GetProductListUseCase {
        return GetProductListUseCaseImpl(productRepository)
    }

    @Singleton
    @Provides
    fun providesProductItemUseCase(
        productRepository: ProductRepository
    ): GetProductItemUseCase {
        return GetProductItemUseCaseImpl(productRepository)
    }

}