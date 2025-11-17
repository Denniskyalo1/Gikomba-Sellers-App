package com.example.thriftlink.data

import com.example.thriftlink.data.Product

class ProductRepository {


    private val sampleProducts = mutableListOf(
        Product(1, "Denim Jacket", "A stylish denim jacket", 25.99, "L", null, "Cool Threads", "user-seller-1"),
        Product(2, "Floral Dress", "Summer maxi dress", 45.50, "S", null, "Boutique-A", "user-seller-2"),
        Product(3, "Vintage Sneakers", "Classic 90s style", 80.00, "10", null, "Sneaker Seller", "user-seller-1"),
        Product(4, "Red Scarf", "Cozy wool scarf", 15.00, "OS", null, "Another Seller", "user-seller-2")
    )

    fun getAllProducts(): List<Product> = sampleProducts.toList()

    fun getMockProducts(): List<Product> = sampleProducts.toList()

    fun getById(id: Int): Product? =
        sampleProducts.find { it.id == id }

    fun addProduct(product: Product) {
        val newId = (sampleProducts.maxOfOrNull { it.id } ?: 0) + 1
        val finalProduct = if (product.id <= 0) product.copy(id = newId) else product
        sampleProducts.add(finalProduct)
    }

    fun updateProduct(updated: Product) {
        val index = sampleProducts.indexOfFirst { it.id == updated.id }
        if (index != -1) {
            sampleProducts[index] = updated
        }
    }

    fun clearAll() {
        sampleProducts.clear()
    }
}
