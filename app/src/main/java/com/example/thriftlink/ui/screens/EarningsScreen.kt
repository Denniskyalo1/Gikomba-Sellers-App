/*ackage com.example.thriftlink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.thriftlink.ui.viewmodel.AnalyticsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EarningsScreen(
    analyticsViewModel: AnalyticsViewModel,
    onBack: () -> Unit,
    onOpenStats: () -> Unit,
    onPaymentHistory: () -> Unit
) {
    val earnings by analyticsViewModel.totalEarnings.collectAsState()
    val pending by analyticsViewModel.pendingPayouts.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Earnings") },
                navigationIcon = { IconButton(onClick = onBack) { Text("<") } }
            )
        }
    ) { padding ->

        Column(
            Modifier.padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Total Earnings: ₱$earnings")
            Text("Pending Payouts: ₱$pending")

            Button(onClick = onOpenStats, modifier = Modifier.fillMaxWidth()) {
                Text("View Statistics")
            }

            Button(onClick = onPaymentHistory, modifier = Modifier.fillMaxWidth()) {
                Text("Payment History")
            }
        }
    }
}*/
// EarningsScreen.kt (No functional change, just formatting the currency display)

package com.example.thriftlink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.thriftlink.ui.viewmodel.AnalyticsViewModel
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EarningsScreen(
    analyticsViewModel: AnalyticsViewModel,
    onBack: () -> Unit,
    onOpenStats: () -> Unit,
    onPaymentHistory: () -> Unit
) {
    val earnings by analyticsViewModel.totalEarnings.collectAsState()
    val pending by analyticsViewModel.pendingPayouts.collectAsState()

    // Helper to format currency
    val formatter = remember { DecimalFormat("0.00") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Earnings") },
                navigationIcon = { IconButton(onClick = onBack) { Text("<") } }
            )
        }
    ) { padding ->

        Column(
            Modifier.padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Display formatted earnings
            Text("Total Earnings: ksh${formatter.format(earnings)}")
            // Display formatted pending payouts
            Text("Pending Payouts: ksh${formatter.format(pending)}")

            Button(onClick = onOpenStats, modifier = Modifier.fillMaxWidth()) {
                Text("View Statistics")
            }

            Button(onClick = onPaymentHistory, modifier = Modifier.fillMaxWidth()) {
                Text("Payment History")
            }
        }
    }
}