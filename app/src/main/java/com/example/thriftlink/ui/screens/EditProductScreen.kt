/*
package com.example.thriftlink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.thriftlink.viewmodel.ProductViewModel
import com.example.thriftlink.data.Product
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductScreen(
    productId: Int,
    productViewModel: ProductViewModel,
    onBack: () -> Unit
) {
    // get product (nullable)
    val product = productViewModel.getProductById(productId)
    if (product == null) {
        // simple fallback UI
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Product not found")
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = onBack) { Text("Back") }
        }
        return
    }

    // local editable state variables initialized from product
    var name by remember { mutableStateOf(product.name) }
    var description by remember { mutableStateOf(product.description) }
    var priceText by remember { mutableStateOf(product.price.toString()) }
    var size by remember { mutableStateOf(product.size) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Product") },
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
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Item Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = priceText,
                onValueChange = { priceText = it },
                label = { Text("Price") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = size,
                onValueChange = { size = it },
                label = { Text("Size") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    // validate and update
                    val price = priceText.toDoubleOrNull() ?: product.price
                    val updated = product.copy(
                        name = name,
                        description = description,
                        price = price,
                        size = size
                    )
                    productViewModel.updateProduct(updated)
                    onBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}
*/
package com.example.thriftlink.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.thriftlink.viewmodel.ProductViewModel
import com.example.thriftlink.data.Product
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.draw.clip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductScreen(
    productId: Int,
    productViewModel: ProductViewModel,
    onBack: () -> Unit
) {
    val product = productViewModel.getProductById(productId)
    if (product == null) {
        // product not found UI
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Product not found")
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = onBack) { Text("Back") }
        }
        return
    }

    var name by remember { mutableStateOf(product.name) }
    var description by remember { mutableStateOf(product.description) }
    var priceText by remember { mutableStateOf(product.price.toString()) }
    var size by remember { mutableStateOf(product.size) }
    var imageUri by remember { mutableStateOf<String?>(product.imageUrl) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri?.toString()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Product") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Item Name") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = priceText, onValueChange = { priceText = it }, label = { Text("Price (Ksh)") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = size, onValueChange = { size = it }, label = { Text("Size") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth().height(140.dp))
            Spacer(Modifier.height(12.dp))

            Button(onClick = { launcher.launch("image/*") }, modifier = Modifier.fillMaxWidth()) {
                Text("Change Image")
            }

            imageUri?.let {
                Spacer(Modifier.height(8.dp))
                AsyncImage(
                    model = it,
                    contentDescription = "Product image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            Spacer(Modifier.height(20.dp))

            Button(onClick = {
                val price = priceText.toDoubleOrNull() ?: product.price
                val updated = product.copy(
                    name = name,
                    description = description,
                    price = price,
                    size = size,
                    imageUrl = imageUri
                )
                productViewModel.updateProduct(updated)
                onBack()
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Save")
            }
        }
    }
}
