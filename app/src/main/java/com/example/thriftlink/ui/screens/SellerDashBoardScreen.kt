/*
package com.example.thriftlink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.thriftlink.ui.viewmodel.AnalyticsViewModel
import com.example.thriftlink.viewmodel.ProductViewModel
import com.example.thriftlink.ui.components.SellerProductCard
import androidx.compose.runtime.collectAsState
import androidx.compose.material3.Text
import androidx.compose.foundation.clickable
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerDashboardScreen(
    productViewModel: ProductViewModel,
    analyticsViewModel: AnalyticsViewModel,
    onEarningsClick: () -> Unit,
    onAddProductClick: () -> Unit,
    onOpenProduct: (Int) -> Unit
) {
    // Collect the product list from the shared ProductViewModel
    val products by productViewModel.productList.collectAsState()

    // optional analytics values (if you want to show the numbers)
    val totalEarnings by analyticsViewModel.totalEarnings.collectAsState()
    val totalOrders by analyticsViewModel.totalOrders.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Dashboard") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {

            // Earnings summary row
            Card(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Earnings", style = MaterialTheme.typography.titleMedium)
                        Text("Total: Ksh $totalEarnings", style = MaterialTheme.typography.headlineSmall)
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text("Orders", style = MaterialTheme.typography.bodyMedium)
                        Text("$totalOrders", style = MaterialTheme.typography.headlineSmall)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Buttons: Earnings details & Add product
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = onEarningsClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Earnings")
                }

                Button(
                    onClick = onAddProductClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Add Product")
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text("My Products", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(8.dp))

            // Product list
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(products) { product ->
                    // Use the SellerProductCard; edit icon triggers navigation via onOpenProduct
                    SellerProductCard(
                        product = product,
                        onEditClick = { id -> onOpenProduct(id) }
                    )
                }
            }
        }
    }
}*/

package com.example.thriftlink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.thriftlink.ui.viewmodel.AnalyticsViewModel
import com.example.thriftlink.viewmodel.ProductViewModel
import com.example.thriftlink.ui.components.SellerProductCard
import androidx.compose.runtime.collectAsState
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerDashboardScreen(
    productViewModel: ProductViewModel,
    analyticsViewModel: AnalyticsViewModel,
    onEarningsClick: () -> Unit,
    onAddProductClick: () -> Unit,
    onOpenProduct: (Int) -> Unit
) {
    val products by productViewModel.productList.collectAsState()
    val totalEarnings by analyticsViewModel.totalEarnings.collectAsState()
    val totalOrders by analyticsViewModel.totalOrders.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My Dashboard") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Earnings", style = MaterialTheme.typography.titleMedium)
                        Text("Total: Ksh %.2f".format(totalEarnings), style = MaterialTheme.typography.headlineSmall)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Orders", style = MaterialTheme.typography.bodyMedium)
                        Text("$totalOrders", style = MaterialTheme.typography.headlineSmall)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = onEarningsClick, modifier = Modifier.weight(1f)) { Text("Earnings") }
                Button(onClick = onAddProductClick, modifier = Modifier.weight(1f)) { Text("Add Product") }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text("My Products", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(products) { product ->
                    SellerProductCard(product = product, onEditClick = { id -> onOpenProduct(id) })
                }
            }
        }
    }
}
