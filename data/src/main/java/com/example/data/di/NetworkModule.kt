package com.example.data.di

import com.example.data.network.ProductApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/** This module provides network-related dependencies for the application.
 * It uses the `fakestoreapi.com` API as the base URL for fetching product data.
 * It utilizes the `OkHttpClient` and `Retrofit` libraries for network communication.*/
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://fakestoreapi.com/"

    // Customized OkHttpClient builder
    // example to add interceptors or logging,
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    // Provides a singleton instance instance of [Retrofit]
    // return A singleton instance of the Retrofit client configured with the provided base URL and Gson converter
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Provides a singleton instance of the network service
    // returns a singleton instance of the network service interface (`ProductApiService`)
    @Provides
    @Singleton
    fun provideNetworkService(retrofit: Retrofit): ProductApiService {
        return retrofit.create(ProductApiService::class.java)
    }
}
