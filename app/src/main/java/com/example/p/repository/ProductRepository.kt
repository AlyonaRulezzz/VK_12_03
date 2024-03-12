package com.example.p.repository

import com.example.p.data.remote.ProductApi
import com.example.p.data.remote.responses.ProductList
import com.example.p.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ProductRepository
@Inject constructor
 (
    private val api: ProductApi
)
{

    suspend fun getProductList(limit: Int, skip: Int): Resource<ProductList> {
        val response = try {
            api.getProductList(limit, skip)
        } catch(e: Exception) {
            return Resource.Error(message = "${e.message}\nAn error occured.")
        }
        return Resource.Success(response)
    }

//    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
//        val response = try {
//            api.getPokemonInfo(pokemonName)
//        } catch (e: Exception) {
//            return Resource.Error(message = "${e.message}\nAn error occured.")
//        }
//        return Resource.Success(response)
//    }
}