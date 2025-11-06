package com.example.thriftlink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.thriftlink.viewmodel.ProductViewModel
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(productId: Int, viewModel: ProductViewModel, onBack: () -> Unit) {
    val product = viewModel.getById(productId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(product?.name ?: "Product") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (product == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Product not found")
            }
        } else {
            Column(modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxWidth().height(250.dp)
                )
                Spacer(Modifier.height(12.dp))
                Text(product.name, style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(6.dp))
                Text("Ksh ${product.price}", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(6.dp))
                Text("Size: ${product.size}  •  Seller: ${product.seller ?: "Unknown"}", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(12.dp))
                Text(product.description)
                Spacer(Modifier.height(16.dp))
                Button(onClick = { /* handle purchase */ }, modifier = Modifier.fillMaxWidth()) {
                    Text("Buy Now")
                }
            }
        }
    }
}
