package com.example.thriftlink.data

class ProductRepository {
    // In real app, replace with network / DB calls.
    private val sample = listOf(
        Product(1, "Denim Jacket", "Stylish blue denim jacket", 35.0, "M", imageUrl = null, seller = "Amina"),
        Product(2, "Leather Boots", "Genuine leather boots", 60.0, "42", imageUrl = null, seller = "John"),
        Product(3, "Summer Dress", "Floral print dress", 25.0, "S", imageUrl = null, seller = "Mary")
    )

    fun getAllProducts(): List<Product> = sample.toList()

    fun getById(id: Int): Product? = sample.find { it.id == id }
}


