package com.example.p.data.remote

import com.example.p.data.remote.responses.Product
import com.example.p.data.remote.responses.ProductList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {

    @GET("products")
    suspend fun getProductList(
        @Query("limit")
        limit: Int,
        @Query("skip")
        skip: Int
    ): ProductList

    @GET("products/{id}")
    suspend fun getProductInfo(
        @Path("id") id: String
    ) : Product
}