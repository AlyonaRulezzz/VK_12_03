package com.example.p.repository

import android.util.Log
import com.example.p.data.remote.ProductApi
import com.example.p.data.remote.responses.Product
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

    suspend fun getProductInfo(id: String): Resource<Product> {
        val response = try {
            Log.d("gggg", "id = $id")
            api.getProductInfo(id)
        } catch (e: Exception) {
            return Resource.Error(message = "${e.message}\nAn error occured.")
        }
        return Resource.Success(response)
    }
}