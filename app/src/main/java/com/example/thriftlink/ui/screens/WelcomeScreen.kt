package com.example.thriftlink.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun WelcomeScreen(onBuyerClick: () -> Unit, onSellerClick: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary), // Use the primary green color
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Text Color Adjustment: We use onPrimary (White) for text on the green background
            Text(
                text = "WELCOME TO",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onPrimary // Text is white on the green background
            )
            Text(
                text = "ThriftLink",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary // Text is white on the green background
            )

            Spacer(Modifier.height(80.dp))

            // Buttons will be styled to stand out against the primary background

            // Buyer Button - Secondary/White look for contrast
            Button(
                onClick = onBuyerClick,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                // Using a container color that contrasts with the green background (e.g., White)
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary // Text is green
                )
            ) {
                Text("Continue as Buyer", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(Modifier.height(16.dp))

            // Seller Button - Secondary/White look for contrast
            Button(
                onClick = onSellerClick,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                // Using a container color that contrasts with the green background (e.g., White)
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary // Text is green
                )
            ) {
                Text("Continue as Seller", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(Modifier.height(24.dp))

            // Additional Explore/Info Text
            Text(
                text = "Buy & Sell Pre-loved Fashion",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f) // Slightly transparent white
            )
        }
    }
}