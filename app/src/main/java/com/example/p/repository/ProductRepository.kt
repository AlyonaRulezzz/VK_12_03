package com.example.p.repository

import com.example.p.data.remote.ProductApi
import com.example.p.data.remote.responses.Pokemon
import com.example.p.data.remote.responses.PokemonList
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

    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch(e: Exception) {
            return Resource.Error(message = "An unknown error occured.")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(pokemonName)
        } catch (e: Exception) {
            return Resource.Error(message = "An unknown error occured.")
        }
        return Resource.Success(response)
    }
}