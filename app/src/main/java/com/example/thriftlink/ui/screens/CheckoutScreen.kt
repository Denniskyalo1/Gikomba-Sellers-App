package com.example.thriftlink.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.thriftlink.viewmodel.ProductViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    productId: Int,
    viewModel: ProductViewModel,
    navController: NavController
) {
    // 1. Fetch Product
    val product = viewModel.getProduct(productId)

    // 2. State Management for Shipping and Payment
    var selectedShippingMethod by remember { mutableStateOf("Delivery") } // Options: "Delivery", "Pickup"
    var selectedPaymentMethod by remember { mutableStateOf("Cash on Delivery") } // Default payment

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Checkout") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    //  TODO: Implement final purchase logic here
                    // e.g., viewModel.confirmOrder(product.id, selectedShippingMethod, selectedPaymentMethod)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                // TODO: Add logic to disable this button if required fields are empty
                enabled = true
            ) {
                Text("Confirm Purchase (ksh ${product.price})")
            }
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Product Summary
            item {
                Text("Item: ${product.name}", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Divider()
            }

            // --- SHIPPING METHOD SELECTION (Radio Buttons) ---
            item {
                ShippingMethodSelector(
                    selectedMethod = selectedShippingMethod,
                    onMethodSelected = { selectedShippingMethod = it }
                )
            }

            // --- DYNAMIC FORM (Changes based on selection) ---
            item {
                when (selectedShippingMethod) {
                    "Delivery" -> DeliveryForm()
                    "Pickup" -> PickupLocationSelection()
                }
            }

            // --- PAYMENT METHOD SELECTION ---
            item {
                PaymentMethodSelector(
                    selectedMethod = selectedPaymentMethod,
                    onMethodSelected = { selectedPaymentMethod = it }
                )
            }

            // Filler space
            item { Spacer(modifier = Modifier.height(64.dp)) }
        }
    }
}

// --- Helper Composable: Shipping Radio Buttons ---
@Composable
fun ShippingMethodSelector(selectedMethod: String, onMethodSelected: (String) -> Unit) {
    Column {
        Text("Shipping Method", style = MaterialTheme.typography.titleSmall)
        Row(Modifier.fillMaxWidth()) {
            // Delivery Option
            ShippingRadioButton(
                label = "Delivery",
                selected = selectedMethod == "Delivery",
                onClick = { onMethodSelected("Delivery") }
            )
            Spacer(Modifier.width(16.dp))
            // Pickup Option
            ShippingRadioButton(
                label = "Pickup",
                selected = selectedMethod == "Pickup",
                onClick = { onMethodSelected("Pickup") }
            )
        }
    }
}

@Composable
fun ShippingRadioButton(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier.clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Text(label, style = MaterialTheme.typography.bodyLarge)
    }
}


// ---------------------------------------------
// DYNAMIC FORMS
// ---------------------------------------------

/*@Composable
fun DeliveryForm() {
    // Note: You would normally define state for these text fields here
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Delivery Details", style = MaterialTheme.typography.titleSmall)
        OutlinedTextField(value = "", onValueChange = { /* state update */ }, label = { Text("Street Address") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = "", onValueChange = { /* state update */ }, label = { Text("City / Town") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = "", onValueChange = { /* state update */ }, label = { Text("Phone Number") }, modifier = Modifier.fillMaxWidth())
    }
}*/
@Composable
fun DeliveryForm() {

    var streetAddress by remember { mutableStateOf("") }
    var cityTown by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Delivery Details", style = MaterialTheme.typography.titleSmall)

        // --- Street Address Field ---
        OutlinedTextField(
            value = streetAddress,
            onValueChange = { streetAddress = it }, // Update state on change
            label = { Text("Street Address") },
            modifier = Modifier.fillMaxWidth()
        )

        // --- City / Town Field ---
        OutlinedTextField(
            value = cityTown,
            onValueChange = { cityTown = it }, // Update state on change
            label = { Text("City / Town") },
            modifier = Modifier.fillMaxWidth()
        )

        // --- Phone Number Field ---
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it }, // Update state on change
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
@Composable
fun PickupLocationSelection() {
    val pickupLocations = listOf("Main Store - Central", "Warehouse - East Wing", "Kiosk - North Gate")
    // State to hold the selected pickup location
    var selectedLocation by remember { mutableStateOf(pickupLocations.first()) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Select Pickup Location", style = MaterialTheme.typography.titleSmall)

        pickupLocations.forEach { location ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedLocation = location }
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(location, style = MaterialTheme.typography.bodyLarge)
                RadioButton(selected = selectedLocation == location, onClick = { selectedLocation = location })
            }
        }
    }
}

// ---------------------------------------------
// PAYMENT METHOD
// ---------------------------------------------

// Ensure this function is defined at the top level of its file
// (e.g., in CheckoutScreen.kt or a components file),
// not nested within another Composable function.

@Composable
fun PaymentMethodSelector(selectedMethod: String, onMethodSelected: (String) -> Unit) {
    // The payment method selection is shown in the UI suggestions
    val paymentOptions = listOf("M-Pesa (Mobile Money)", "Cash on Delivery", "Credit/Debit Card")

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Payment Method", style = MaterialTheme.typography.titleSmall)

        paymentOptions.forEach { method ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onMethodSelected(method) }
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(method, style = MaterialTheme.typography.bodyLarge)
                RadioButton(selected = selectedMethod == method, onClick = { onMethodSelected(method) })
            }
        }

        // M-Pesa Placeholder message
        if (selectedMethod == "M-Pesa (Mobile Money)") {
            Text(
                "Note: M-Pesa API integration will be completed post-launch. You will be prompted for payment details after confirming the order.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}