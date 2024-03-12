package com.example.p.data.remote

import com.example.p.data.remote.responses.ProductList
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {

    @GET("products")
    suspend fun getProductList(
        @Query("limit")
        limit: Int,
        @Query("skip")
        skip: Int
    ): ProductList

//    @GET("pokemon/{name}")
//    suspend fun getPokemonInfo(
//        @Path("name")
//        name: String
//    ) : Product
}