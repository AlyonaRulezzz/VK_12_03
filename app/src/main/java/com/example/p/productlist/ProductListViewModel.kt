package com.example.p.productlist

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.p.data.models.ProductListEntry
import com.example.p.repository.ProductRepository
import com.example.p.util.Constants.PAGE_SIZE
import com.example.p.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository
): ViewModel() {

    private var curPage = 0

    var productList = mutableStateOf<List<ProductListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    private var cachedProductList = listOf<ProductListEntry>()
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)

    init {
        loadProductPaginated()
    }

    fun searchProductList(query: String) {
        val listToSearch = if (isSearchStarting) {
            productList.value
        } else {
            cachedProductList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.trim().isEmpty()) {
                productList.value = cachedProductList
                isSearching.value = false
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.productTitle.contains(query.trim(), ignoreCase = true)
            }
            if (isSearchStarting) {
                cachedProductList = productList.value
                isSearchStarting = false
            }
            productList.value = results
            isSearching.value = true
        }
    }

    fun loadProductPaginated() {
        viewModelScope.launch {
            isLoading.value = true
            when(val result = repository.getProductList(PAGE_SIZE, curPage * PAGE_SIZE)) {
                is Resource.Success -> {
                    endReached.value = curPage * PAGE_SIZE >= result.data!!.total
                    val productEntries = result.data.products.mapIndexed { _, entry ->
                        ProductListEntry(entry.title, entry.thumbnail, entry.id, entry.description)
                    }
                    curPage++

                    loadError.value = ""
                    isLoading.value = false
                    productList.value += productEntries
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }

                else -> {}  //  ????????????
            }
        }
    }

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}