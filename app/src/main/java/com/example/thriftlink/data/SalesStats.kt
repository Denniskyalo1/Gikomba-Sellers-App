package com.example.thriftlink.model

data class SalesStats(
    val totalOrders: Int,
    val topCategory: String,
    val bestSellingProduct: String,
    val averageOrderValue: Double
)
