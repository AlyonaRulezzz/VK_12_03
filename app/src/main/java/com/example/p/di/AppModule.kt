package com.example.p.di

import com.example.p.data.remote.ProductApi
import com.example.p.repository.ProductRepository
import com.example.p.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokemonRepository(
        api: ProductApi
    ) = ProductRepository(api)

    @Singleton
    @Provides
    fun providePokeApi(): ProductApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(ProductApi::class.java)
}