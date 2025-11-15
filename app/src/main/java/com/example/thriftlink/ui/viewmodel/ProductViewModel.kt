package com.example.thriftlink.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thriftlink.data.Product
import com.example.thriftlink.data.ProductRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
/*
open class ProductViewModel : ViewModel() {
    private val repository = ProductRepository()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    open val products: StateFlow<List<Product>> = _products

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
}*/


class ProductViewModel(
    private val repository: ProductRepository = ProductRepository()
) : ViewModel() {


    //SOURCE OF TRUTH — All products from repository

    private var allProducts: List<Product> = repository.getMockProducts()


    //FILTERED PRODUCT LIST (Observed by UI)

    private val _productList = MutableStateFlow(allProducts)
    val productList: StateFlow<List<Product>> = _productList.asStateFlow()


    //SEARCH + CATEGORY STATE

    private val _searchQuery = MutableStateFlow("")
    // Expose the read-only StateFlow for observation by the UI
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow() // Using asStateFlow() is cleaner

    var selectedFilter: String = "All"
        private set

    fun onSearchQueryChanged(query: String) {
        // Update the value property of the MutableStateFlow
        _searchQuery.value = query
        applyFilters()
    }

    fun selectFilter(filter: String) {
        selectedFilter = filter
        applyFilters()
    }

    //APPLY SEARCH + CATEGORY FILTERS

    private fun applyFilters() {
        var filtered = allProducts


        val currentQuery = _searchQuery.value

        // SEARCH FILTER

        if (currentQuery.isNotBlank()) {
            filtered = filtered.filter {
                it.name.contains(currentQuery, ignoreCase = true) ||
                        it.description.contains(currentQuery, ignoreCase = true)
            }
        }

        // CATEGORY FILTER
        filtered = when (selectedFilter) {
            "Women" -> filtered.filter { it.size in listOf("S", "M", "L") }
            "Men"   -> filtered.filter { it.size == "10" }  // example logic
            else    -> filtered
        }

        _productList.value = filtered
    }


    //REFRESH PRODUCTS (Used by SwipeRefreshBox)

    fun refreshProducts(onDone: () -> Unit) {
        viewModelScope.launch {
            delay(1200) // Simulated loading
            allProducts = repository.getMockProducts()
            applyFilters()
            onDone()
        }
    }

    //GET PRODUCT BY ID

    fun getProductById(id: Int): Product? {
        return allProducts.find { it.id == id }
    }

    //WISHLIST SYSTEM

    private val _bought = MutableStateFlow<List<Product>>(emptyList())
    val bought = _bought.asStateFlow()

    private val _wishlist = MutableStateFlow<List<Product>>(emptyList())
    val wishlist = _wishlist.asStateFlow()

    fun removeFromOrders(product: Product) {
        _bought.value = _bought.value.filter { it.id != product.id }
    }



    //ORDERS SYSTEM
    private val _orders = MutableStateFlow<List<Product>>(emptyList())
    val orders: StateFlow<List<Product>> = _orders.asStateFlow()

    fun placeOrder(product: Product?) {
        _orders.value = (_orders.value + product) as List<*> as List<Product>
    }


    fun removeOrder(product: Product) {
        _orders.value = _orders.value - product
    }


    fun getProduct(id: Int): Product {
        return getProductById(id) ?: error("Product with ID $id not found")
    }

    fun addToWishlist(product: Product) {
        _wishlist.value = _wishlist.value + product
    }
    fun logout() {
        // TODO: Implement actual user session clearing logic here
        println("User logged out.")
    }

}
