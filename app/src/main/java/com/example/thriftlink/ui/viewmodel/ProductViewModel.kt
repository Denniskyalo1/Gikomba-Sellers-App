package com.example.thriftlink.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thriftlink.data.Product
import com.example.thriftlink.data.ProductRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch




/*class ProductViewModel(
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
    // -------------------------------------------------------------------------
    // SELLER PRODUCT MANAGEMENT
    // -------------------------------------------------------------------------

    fun addProduct(
        name: String,
        description: String,
        price: Double,
        size: String,
        sellerName: String,
        sellerId: String
    ) {
        // Auto-generate ID
        val newId = (allProducts.maxOfOrNull { it.id } ?: 0) + 1

        val newProduct = Product(
            id = newId,
            name = name,
            description = description,
            price = price,
            size = size,
            imageUrl = null,
            seller = sellerName,
            sellerId = sellerId
        )

        // Save locally
        allProducts = allProducts + newProduct
        applyFilters()
    }

    fun updateProduct(updated: Product) {
        val index = allProducts.indexOfFirst { it.id == updated.id }
        if (index != -1) {
            allProducts[index] = updated       // ✔ NOW WORKS (MutableList)
            applyFilters()
        }
    }




}*/
class ProductViewModel(
    private val repository: ProductRepository = ProductRepository()
) : ViewModel() {

    // -------------------------------------------------------------------------------------
    // SOURCE OF TRUTH — MutableList so we can edit products
    // -------------------------------------------------------------------------------------
    private var allProducts = repository.getMockProducts().toMutableList()

    // -------------------------------------------------------------------------------------
    // FILTERED LIST EXPOSED TO UI
    // -------------------------------------------------------------------------------------
    private val _productList = MutableStateFlow<List<Product>>(allProducts)
    val productList: StateFlow<List<Product>> = _productList.asStateFlow()

    // -------------------------------------------------------------------------------------
    // SEARCH + CATEGORY STATE
    // -------------------------------------------------------------------------------------
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    var selectedFilter: String = "All"
        private set

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        applyFilters()
    }

    fun selectFilter(filter: String) {
        selectedFilter = filter
        applyFilters()
    }

    // -------------------------------------------------------------------------------------
    // APPLY SEARCH + CATEGORY FILTERS
    // -------------------------------------------------------------------------------------
    private fun applyFilters() {
        var filtered: List<Product> = allProducts

        val q = _searchQuery.value

        if (q.isNotBlank()) {
            filtered = filtered.filter {
                it.name.contains(q, true) ||
                        it.description.contains(q, true)
            }
        }

        filtered = when (selectedFilter) {
            "Women" -> filtered.filter { it.size in listOf("S", "M", "L") }
            "Men"   -> filtered.filter { it.size == "10" }
            else    -> filtered
        }

        _productList.value = filtered
    }

    // -------------------------------------------------------------------------------------
    // REFRESH PRODUCTS
    // -------------------------------------------------------------------------------------
    fun refreshProducts(onDone: () -> Unit) {
        viewModelScope.launch {
            delay(1200)
            allProducts = repository.getMockProducts().toMutableList()
            applyFilters()
            onDone()
        }
    }

    // -------------------------------------------------------------------------------------
    // GET SINGLE PRODUCT
    // -------------------------------------------------------------------------------------
    fun getProductById(id: Int): Product? {
        return allProducts.find { it.id == id }
    }

    fun getProduct(id: Int): Product {
        return requireNotNull(getProductById(id)) { "Product $id not found" }
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



    private val _wishlist = MutableStateFlow<List<Product>>(emptyList())
    val wishlist = _wishlist.asStateFlow()

    fun addToWishlist(product: Product) {
        _wishlist.value = _wishlist.value + product
    }

    fun removeFromWishlist(product: Product) {
        _wishlist.value = _wishlist.value - product
    }
    private val _bought = MutableStateFlow<List<Product>>(emptyList())
    val bought = _bought.asStateFlow()
    // Add to Bought List
    fun addToBought(product: Product) {
        _bought.value = _bought.value + product
    }

    // Remove from Bought List
    fun removeFromBought(product: Product) {
        _bought.value = _bought.value - product
    }
    // -------------------------------------------------------------------------------------
    // SELLER ACTIONS — ADD PRODUCT
    // -------------------------------------------------------------------------------------
    fun addProduct(
        name: String,
        description: String,
        price: Double,
        size: String,
        imageUrl: String?,
        sellerName: String,
        sellerId: String
    ) {
        val newId = (allProducts.maxOfOrNull { it.id } ?: 0) + 1

        val newProduct = Product(
            id = newId,
            name = name,
            description = description,
            price = price,
            size = size,
            imageUrl = imageUrl,   // ← Now the URL is passed correctly
            seller = sellerName,
            sellerId = sellerId
        )

        allProducts.add(newProduct)
        applyFilters()
    }


    // -------------------------------------------------------------------------------------
    // SELLER — UPDATE PRODUCT
    // -------------------------------------------------------------------------------------
    fun updateProduct(updated: Product) {
        val index = allProducts.indexOfFirst { it.id == updated.id }
        if (index != -1) {
            allProducts[index] = updated       // ✔ NOW WORKS (MutableList)
            applyFilters()
        }
    }
    fun logout() {
        println("Logout triggered.")
    }


}


