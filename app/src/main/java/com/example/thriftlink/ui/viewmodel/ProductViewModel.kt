package com.example.thriftlink.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thriftlink.data.Product
import com.example.thriftlink.data.ProductRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val repository = ProductRepository()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            // simulate load
            _products.value = repository.getAllProducts()
        }
    }

    fun getById(id: Int): Product? {
        return _products.value.find { it.id == id } ?: repository.getById(id)
    }

    // Expose non-suspend refresh function used by UI
    fun refreshProducts(onComplete: (() -> Unit)? = null) {
        viewModelScope.launch {
            // simulate network delay
            delay(800)
            // simple reload / shuffle
            _products.value = repository.getAllProducts().shuffled()
            onComplete?.invoke()
        }
    }

    fun addProduct(name: String, description: String, price: Double, size: String, imageUrl: String? = null, seller: String? = null) {
        val newId = (_products.value.maxOfOrNull { it.id } ?: 0) + 1
        val newProduct = Product(newId, name, description, price, size, imageUrl = imageUrl, seller = seller)
        _products.value = _products.value + newProduct
    }
}
