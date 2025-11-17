/*
package com.example.thriftlink.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.thriftlink.data.ProductRepository
import com.example.thriftlink.model.Payment // Ensure this model exists
import com.example.thriftlink.model.SalesStats // Ensure this model exists
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

// Imports needed for mock data derivation
import java.time.LocalDate
import java.util.concurrent.ThreadLocalRandom

// 1. Add ProductRepository as a dependency in the constructor
class AnalyticsViewModel(private val productRepository: ProductRepository) : ViewModel() {

    // ------------------------------
    // 💰 Total Earnings (Calculated)
    // ------------------------------
    private val _totalEarnings = MutableStateFlow(0.0)
    val totalEarnings = _totalEarnings.asStateFlow()

    // ------------------------------
    // 💵 Pending Payouts (Calculated)
    // ------------------------------
    private val _pendingPayouts = MutableStateFlow(0.0)
    val pendingPayouts = _pendingPayouts.asStateFlow()

    // ------------------------------
    // 📦 Total Orders Count (Calculated)
    // ------------------------------
    private val _totalOrders = MutableStateFlow(0)
    val totalOrders = _totalOrders.asStateFlow()

    // ------------------------------
    // 📜 Payment History (Derived from Products)
    // ------------------------------
    private val _paymentHistory = MutableStateFlow(emptyList<Payment>())
    val paymentHistory = _paymentHistory.asStateFlow()

    // ------------------------------
    // 📊 Sales Statistics (Derived from Products)
    // ------------------------------
    // Initialize with a default/empty SalesStats object
    private val _salesStats = MutableStateFlow(SalesStats(totalOrders = 0, topCategory = "N/A", bestSellingProduct = "N/A", averageOrderValue = 0.0))
    val salesStats = _salesStats.asStateFlow()

    // Initialization block to calculate analytics immediately
    init {
        // Load the calculated analytics on startup
        loadCalculatedAnalytics()
    }

    // Helper function to generate a random date for mock data
    private fun generateRandomPastDate(daysAgo: Long): String {
        val today = LocalDate.now()
        // Generates a random date between 1 and 'daysAgo' days in the past
        val randomDate = today.minusDays(ThreadLocalRandom.current().nextLong(daysAgo) + 1)
        return randomDate.toString() // Format: YYYY-MM-DD
    }


    // ----------------------------------------------------------------------
    // 2. Logic to calculate all metrics from the ProductRepository data
    // ----------------------------------------------------------------------
    fun loadCalculatedAnalytics() {
        val products = productRepository.getAllProducts()

        // --- Calculate Earnings Metrics ---
        val total = products.sumOf { it.price }
        val totalOrdersCount = products.size

        _totalEarnings.value = total
        _totalOrders.value = totalOrdersCount
        // Mock pending 50%
        _pendingPayouts.value = total * 0.50

        // --- Calculate Payment History (Payments) ---
        // Assume each product in the list represents a successful sale leading to a payment.
        val derivedPayments = products.mapIndexed { index, product ->
            // Distribute payments into "Completed" and "Pending" for variety
            val status = if (index % 2 == 0) "Completed" else "Pending"
            Payment(
                amount = product.price,
                status = status,
                // Assign a random date in the last 60 days
                date = generateRandomPastDate(60)
            )
        }
        _paymentHistory.value = derivedPayments


        // --- Calculate Sales Statistics (SalesStats) ---

        // Find the product with the highest price for mock "Best Selling"
        val bestSellingProduct = products.maxByOrNull { it.price }?.name ?: "N/A"

        // Simple mock Top Category logic based on names (can be expanded if Product had a category field)
        val topCategory = if (products.any { it.name.contains("Dress") || it.name.contains("Jacket") }) "Apparel" else "Footwear"

        // Calculate Average Order Value
        val averageOrderValue = if (totalOrdersCount > 0) total / totalOrdersCount else 0.0

        _salesStats.value = SalesStats(
            totalOrders = totalOrdersCount,
            topCategory = topCategory,
            bestSellingProduct = bestSellingProduct,
            averageOrderValue = averageOrderValue
        )
    }
}*/
package com.example.thriftlink.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.thriftlink.data.ProductRepository
import com.example.thriftlink.model.Payment // Ensure this model exists
import com.example.thriftlink.model.SalesStats // Ensure this model exists
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

// Imports needed for mock data derivation
import java.time.LocalDate
import java.util.concurrent.ThreadLocalRandom

// 1. Add ProductRepository as a dependency in the constructor
class AnalyticsViewModel(private val productRepository: ProductRepository) : ViewModel() {

    // ------------------------------
    // 💰 Total Earnings (Calculated) - Used as "Total sales this month"
    // ------------------------------
    private val _totalEarnings = MutableStateFlow(0.0)
    val totalEarnings = _totalEarnings.asStateFlow()

    // ------------------------------
    // 💵 Pending Payouts (Calculated) - Used as a replacement metric in UI
    // ------------------------------
    private val _pendingPayouts = MutableStateFlow(0.0)
    val pendingPayouts = _pendingPayouts.asStateFlow()

    // ------------------------------
    // 📦 Total Orders Count (Calculated) - Used as "Orders this month"
    // ------------------------------
    private val _totalOrders = MutableStateFlow(0)
    val totalOrders = _totalOrders.asStateFlow()

    // ------------------------------
    // 📜 Payment History (Derived from Products)
    // ------------------------------
    private val _paymentHistory = MutableStateFlow(emptyList<Payment>())
    val paymentHistory = _paymentHistory.asStateFlow()

    // ------------------------------
    // 📊 Sales Statistics (Derived from Products)
    // ------------------------------
    // Initialize with a default/empty SalesStats object
    private val _salesStats = MutableStateFlow(SalesStats(totalOrders = 0, topCategory = "N/A", bestSellingProduct = "N/A", averageOrderValue = 0.0))
    val salesStats = _salesStats.asStateFlow()

    // Initialization block to calculate analytics immediately
    init {
        // Load the calculated analytics on startup
        loadCalculatedAnalytics()
    }

    // Helper function to generate a random date for mock data
    private fun generateRandomPastDate(daysAgo: Long): String {
        val today = LocalDate.now()
        // Generates a random date between 1 and 'daysAgo' days in the past
        val randomDate = today.minusDays(ThreadLocalRandom.current().nextLong(daysAgo) + 1)
        return randomDate.toString() // Format: YYYY-MM-DD
    }


    // ----------------------------------------------------------------------
    // 2. Logic to calculate all metrics from the ProductRepository data
    // ----------------------------------------------------------------------
    fun loadCalculatedAnalytics() {
        val products = productRepository.getAllProducts()

        // --- Calculate Earnings Metrics ---
        val total = products.sumOf { it.price }
        val totalOrdersCount = products.size

        _totalEarnings.value = total
        _totalOrders.value = totalOrdersCount
        // Mock pending 50%
        _pendingPayouts.value = total * 0.50

        // ⚠️ NOTE: We intentionally skip adding _pageViews and _productViews here.

        // --- Calculate Payment History (Payments) ---
        val derivedPayments = products.mapIndexed { index, product ->
            val status = if (index % 2 == 0) "Completed" else "Pending"
            Payment(
                amount = product.price,
                status = status,
                date = generateRandomPastDate(60)
            )
        }
        _paymentHistory.value = derivedPayments


        // --- Calculate Sales Statistics (SalesStats) ---
        val bestSellingProduct = products.maxByOrNull { it.price }?.name ?: "N/A"
        val topCategory = if (products.any { it.name.contains("Dress") || it.name.contains("Jacket") }) "Apparel" else "Footwear"
        val averageOrderValue = if (totalOrdersCount > 0) total / totalOrdersCount else 0.0

        _salesStats.value = SalesStats(
            totalOrders = totalOrdersCount,
            topCategory = topCategory,
            bestSellingProduct = bestSellingProduct,
            averageOrderValue = averageOrderValue
        )
    }
}