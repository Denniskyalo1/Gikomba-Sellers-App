package com.example.thriftlink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.thriftlink.viewmodel.ProductViewModel
import com.example.thriftlink.data.Product
/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Orders") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("No orders yet!", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
*/

@Composable
fun OrdersScreen(
    viewModel: ProductViewModel,
    navController: NavController
) {

    var selectedTab by remember { mutableStateOf("Bought") }
    val bought by viewModel.bought.collectAsState()
    val wishlist by viewModel.wishlist.collectAsState()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var itemToDelete by remember { mutableStateOf<Product?>(null) }

    Column(Modifier.fillMaxSize()) {

        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
        }

        Row(Modifier.padding(16.dp)) {
            TabChip("Bought", selectedTab == "Bought") { selectedTab = "Bought" }
            Spacer(Modifier.width(8.dp))
            TabChip("Wishlist", selectedTab == "Wishlist") { selectedTab = "Wishlist" }
        }

        val list = if (selectedTab == "Bought") bought else wishlist

        LazyColumn {
            items(list) { product ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ProductSummary(product)

                    IconButton(onClick = {
                        itemToDelete = product
                        showDeleteDialog = true
                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Delete")
                    }
                }
            }
        }

        if (showDeleteDialog && itemToDelete != null) {
            DeleteDialog(
                onConfirm = {
                    viewModel.removeFromOrders(itemToDelete!!)
                    showDeleteDialog = false
                },
                onCancel = { showDeleteDialog = false }
            )
        }
    }
}

/* ---------- REQUIRED COMPONENTS ---------- */

@Composable
fun TabChip(text: String, selected: Boolean, onClick: () -> Unit) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(text) }
    )
}

@Composable
fun ProductSummary(product: Product) {
    Column(Modifier.width(200.dp)) {
        Text(product.name, style = MaterialTheme.typography.titleMedium)
        Text("Ksh ${product.price}", style = MaterialTheme.typography.bodyMedium)
    }
}
