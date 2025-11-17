/*
package com.example.thriftlink.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.thriftlink.ui.viewmodel.AnalyticsViewModel
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerStatsScreen(
    analyticsViewModel: AnalyticsViewModel,
    onBack: () -> Unit
) {
    // ⬇️ COLLECT ONLY THE AVAILABLE METRICS ⬇️
    val orders by analyticsViewModel.totalOrders.collectAsState()
    val totalSales by analyticsViewModel.totalEarnings.collectAsState()
    val pendingPayouts by analyticsViewModel.pendingPayouts.collectAsState()
    // Use an existing SalesStats property to fill the 4th card
    val stats by analyticsViewModel.salesStats.collectAsState()
    val avgOrderValue = stats.averageOrderValue // Use this as the 4th metric

    // Mock data for the bar chart (remains the same)
    val monthlyEarnings = remember {
        listOf(10.0, 6.0, 12.0, 14.0, 4.0, 8.0)
    }
    val formatter = remember { DecimalFormat("#,##0.00") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Seller Dashboard") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // 2x2 Grid for Key Metrics using available data
            Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {

                // First Row: Orders and Total Sales
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DashboardStatCard("Orders this month:", orders.toString(), Modifier.weight(1f))
                    // Use Total Earnings as the "Total sales" metric
                    DashboardStatCard("Total sales this month:", "KES ${formatter.format(totalSales)}", Modifier.weight(1f))
                }

                Spacer(Modifier.height(16.dp))

                // Second Row: Pending Payouts and Avg. Order Value (replacements for views)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DashboardStatCard("Pending Payouts:", "KES ${formatter.format(pendingPayouts)}", Modifier.weight(1f))
                    DashboardStatCard("Avg. Order Value:", "KES ${formatter.format(avgOrderValue)}", Modifier.weight(1f))
                }
            }

            Spacer(Modifier.height(24.dp))

            // Monthly Earnings Overview (Bar Chart Section)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Monthly earnings overview:",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Bar Chart Placeholder
                BarChart(earnings = monthlyEarnings)
            }
        }
    }
}

// Helper Composable for the 2x2 Dashboard Cards (Remains the same)
@Composable
fun DashboardStatCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, style = MaterialTheme.typography.bodyMedium)
            Text(
                value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// Simple Bar Chart Placeholder (Remains the same)
@Composable
fun BarChart(earnings: List<Double>) {
    val maxEarning = earnings.maxOrNull() ?: 1.0

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(Color.Transparent),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.Bottom // Align bars to the bottom
    ) {
        earnings.forEach { earning ->
            val barHeight = (earning / maxEarning).toFloat()
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(barHeight) // Scale height relative to max
                    .background(Color.Black, RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
            )
        }
    }
}*/
package com.example.thriftlink.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.thriftlink.ui.viewmodel.AnalyticsViewModel
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerStatsScreen(
    analyticsViewModel: AnalyticsViewModel,
    onBack: () -> Unit
) {
    // ⬇️ COLLECT ONLY THE AVAILABLE METRICS ⬇️
    val orders by analyticsViewModel.totalOrders.collectAsState()
    val totalSales by analyticsViewModel.totalEarnings.collectAsState()
    val pendingPayouts by analyticsViewModel.pendingPayouts.collectAsState()
    val stats by analyticsViewModel.salesStats.collectAsState()
    val avgOrderValue = stats.averageOrderValue // Use this as the 4th metric

    // Mock data for the bar chart (6 months of earnings, normalized to a simple scale)
    // NOTE: In a real app, this data would come from the ViewModel's historical data flow.
    val monthlyEarnings = remember {
        listOf(10.0, 6.0, 12.0, 14.0, 4.0, 8.0)
    }
    val formatter = remember { DecimalFormat("#,##0.00") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Seller Dashboard") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // 2x2 Grid for Key Metrics using available data (Unchanged)
            Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {

                // First Row: Orders and Total Sales
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DashboardStatCard("Orders this month:", orders.toString(), Modifier.weight(1f))
                    DashboardStatCard("Total sales this month:", "KES ${formatter.format(totalSales)}", Modifier.weight(1f))
                }

                Spacer(Modifier.height(16.dp))

                // Second Row: Pending Payouts and Avg. Order Value
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DashboardStatCard("Pending Payouts:", "KES ${formatter.format(pendingPayouts)}", Modifier.weight(1f))
                    DashboardStatCard("Avg. Order Value:", "KES ${formatter.format(avgOrderValue)}", Modifier.weight(1f))
                }
            }

            Spacer(Modifier.height(24.dp))

            // Monthly Earnings Overview (Bar Chart Section)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Monthly earnings overview:",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // ⬇️ USING THE WORKING BAR CHART IMPLEMENTATION ⬇️
                BarChart(earnings = monthlyEarnings)
            }
        }
    }
}

// Helper Composable for the 2x2 Dashboard Cards (Unchanged)
@Composable
fun DashboardStatCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, style = MaterialTheme.typography.bodyMedium)
            Text(
                value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// -----------------------------------------------------------
// 📊 WORKING Bar Chart Implementation
// -----------------------------------------------------------
@Composable
fun BarChart(earnings: List<Double>) {
    val maxEarning = earnings.maxOrNull() ?: 1.0
    val monthLabels = listOf("J", "F", "M", "A", "M", "J","J", "A", "S", "O", "N", "D")

    Column(Modifier.fillMaxWidth()) {

        // 1. Chart Body (Bars)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            earnings.forEach { earning ->
                val barHeight = (earning / maxEarning).toFloat() // Calculates bar height ratio

                Column(
                    modifier = Modifier
                        .weight(1f) // Equal width for all bars
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    // Actual Bar
                    Box(
                        modifier = Modifier
                            .fillMaxHeight(barHeight) // Scale height
                            .fillMaxWidth(0.8f) // Narrower bar width
                            // Use the primary color for the bars
                            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                    )
                }
            }
        }

        // 2. X-Axis Labels (Months)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            monthLabels.forEachIndexed { index, label ->
                if (index < earnings.size) {
                    Text(
                        text = label,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}