package com.example.thriftlink.data

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val size: String,
    val imageUrl: String? = null,
    val seller: String? = "Unknown"
)
