/*
package com.example.thriftlink.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.thriftlink.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    navController: NavController,
    productViewModel: ProductViewModel,
    currentSellerName: String = "Cool Threads",
    currentSellerId: String = "user-seller-1"
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var size by remember { mutableStateOf("") }
    var priceText by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<String?>(null) }

    // Image picker launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri?.toString()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Add Product") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            // NAME
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Item Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            // PRICE
            OutlinedTextField(
                value = priceText,
                onValueChange = { priceText = it },
                label = { Text("Price (Ksh)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            // SIZE
            OutlinedTextField(
                value = size,
                onValueChange = { size = it },
                label = { Text("Size") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            // DESCRIPTION
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description / Category") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            Spacer(Modifier.height(12.dp))

            // IMAGE PICK BUTTON
            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Upload Image")
            }

            // IMAGE PREVIEW
            imageUri?.let {
                Spacer(Modifier.height(8.dp))
                AsyncImage(
                    model = it,
                    contentDescription = "Picked Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            Spacer(Modifier.height(20.dp))

            // SAVE BUTTON
            Button(
                onClick = {
                    val priceDouble = priceText.toDoubleOrNull() ?: 0.0

                    productViewModel.addProduct(
                        name = name,
                        description = description,
                        price = priceDouble,
                        size = size,
                        imageUrl = imageUri,      // <-- correct value
                        sellerName = currentSellerName,
                        sellerId = currentSellerId
                    )

                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Product")
            }

        }
    }
}*/

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    productViewModel: ProductViewModel,
    onFinish: () -> Unit,
    currentSellerName: String = "Seller",
    currentSellerId: String = "user-seller-1"
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var size by remember { mutableStateOf("") }
    var priceText by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri?.toString()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Add Product") }) }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
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
                label = { Text("Price (Ksh)") },
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
                label = { Text("Description / Category") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Upload Image")
            }

            imageUri?.let {
                Spacer(Modifier.height(8.dp))
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    val price = priceText.toDoubleOrNull() ?: 0.0

                    productViewModel.addProduct(
                        name = name,
                        description = description,
                        price = price,
                        size = size,
                        imageUrl = imageUri,       // <-- FIXED
                        sellerName = currentSellerName,
                        sellerId = currentSellerId
                    )

                    onFinish()   // <-- NAVIGATION DONE SAFELY
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Product")
            }
        }
    }
}


