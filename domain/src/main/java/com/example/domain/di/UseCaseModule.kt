package com.example.domain.di

import com.example.domain.repository.ProductRepository
import com.example.domain.usecase.GetProductItemUseCase
import com.example.domain.usecase.GetProductItemUseCaseImpl
import com.example.domain.usecase.GetProductListUseCase
import com.example.domain.usecase.GetProductListUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * This module provides use cases for retrieving and managing product data in the application.
 */
@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun providesProductsUseCase(
        productRepository: ProductRepository,
        dispatcher: CoroutineDispatcher,
    ): GetProductListUseCase {
        return GetProductListUseCaseImpl(productRepository, dispatcher)
    }

    @Provides
    fun providesProductItemUseCase(
        productRepository: ProductRepository,
        dispatcher: CoroutineDispatcher,
    ): GetProductItemUseCase {
        return GetProductItemUseCaseImpl(productRepository, dispatcher)
    }
}
