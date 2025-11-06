package com.example.thriftlink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.thriftlink.viewmodel.ProductViewModel
// Assuming you moved the new component here or import it from the components package
import com.example.thriftlink.ui.components.SwipeRefreshBox // REQUIRED IMPORT for the new component

@Composable
fun CatalogScreen(viewModel: ProductViewModel, onOpenDetail: (Int) -> Unit) {
    val products by viewModel.products.collectAsState()
    var refreshing by remember { mutableStateOf(false) }

    // --- CORRECTION START ---

    // The whole content is wrapped in the new SwipeRefreshBox
    SwipeRefreshBox(
        isRefreshing = refreshing,
        onRefresh = {
            // Set the flag to true to show the indicator
            refreshing = true
            // Call the ViewModel function
            viewModel.refreshProducts {
                // Callback to set the flag to false when the refresh operation is done
                refreshing = false
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
        // The content (LazyColumn) goes inside the SwipeRefreshBox content lambda
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products) { product ->
                Card(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(product.name, style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(4.dp))
                            Text("Ksh ${product.price}", style = MaterialTheme.typography.bodyMedium)
                        }
                        Button(onClick = { onOpenDetail(product.id) }) {
                            Text("View")
                        }
                    }
                }
            }
            // Optional: Show a message if the list is empty
            if (products.isEmpty() && !refreshing) {
                item {
                    Box(
                        modifier = Modifier.fillParentMaxSize().padding(top = 100.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Text("No products found.", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }

}