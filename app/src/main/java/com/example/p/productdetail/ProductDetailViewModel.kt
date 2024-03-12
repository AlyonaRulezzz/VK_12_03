package com.example.p.productdetail

import androidx.lifecycle.ViewModel
import com.example.p.data.remote.responses.Product
import com.example.p.repository.ProductRepository
import com.example.p.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {
//    suspend fun getPokemonInfo(pokemonName: String): Resource<Product> {
//        return repository.getPokemonInfo(pokemonName)
//    }
}