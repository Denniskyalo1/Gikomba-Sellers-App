package com.example.thriftlink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.thriftlink.viewmodel.ProductViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import kotlinx.coroutines.launch

/*
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
*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    viewModel: ProductViewModel,
    navController: NavController
) {
    val product = viewModel.getProduct(productId)

    // Coroutine scope for launching the Snackbar
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(product.name) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(16.dp))

            // ITEM INFO
            Text(product.name, style = MaterialTheme.typography.titleLarge)
            Text("Price: Ksh ${product.price}")
            Text("Size: ${product.size}")
            Text("Seller: ${product.seller}")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // --- BUY NOW BUTTON ---
                Button(
                    onClick = {

                        viewModel.placeOrder(product)

                        // Then navigate to checkout
                        navController.navigate("checkout/${product.id}")
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text("Buy Now")
                }

                // --- ADD TO WISHLIST BUTTON ---
                OutlinedButton(
                    onClick = {
                        viewModel.addToWishlist(product)
                        scope.launch {
                            snackbarHostState.showSnackbar("Added to Wishlist!")
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Add to Wishlist")
                }
            }

            Text("DESCRIPTION", fontWeight = FontWeight.Bold)
            Text(product.description)
        }
    }
}


















































































