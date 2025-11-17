/*package com.example.thriftlink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.thriftlink.ui.viewmodel.AnalyticsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentHistoryScreen(
    analyticsViewModel: AnalyticsViewModel,
    onBack: () -> Unit
) {
    val payments by analyticsViewModel.paymentHistory.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Payment History") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            items(payments.size) { index ->
                val p = payments[index]

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Amount: ksh${p.amount}")
                        Text("Status: ksh${p.status}")
                        Text("Date: ksh${p.date}")
                    }
                }
            }
        }
    }
}
*/
package com.example.thriftlink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.thriftlink.ui.viewmodel.AnalyticsViewModel
import java.text.DecimalFormat // Import for formatting

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentHistoryScreen(
    analyticsViewModel: AnalyticsViewModel,
    onBack: () -> Unit
) {
    val payments by analyticsViewModel.paymentHistory.collectAsState()
    // Define a DecimalFormat for currency display (Kenya Shilling format)
    val currencyFormatter = remember { DecimalFormat("#,##0.00") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Payment History") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp), // Use horizontal for list contents
            contentPadding = PaddingValues(vertical = 8.dp), // Padding above and below the list
            verticalArrangement = Arrangement.spacedBy(8.dp) // Spacing between cards
        ) {
            items(payments.size) { index ->
                val p = payments[index]

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp)) {
                        // 💰 Amount
                        Text(
                            text = "Amount: KES ${currencyFormatter.format(p.amount)}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(4.dp))

                        // ✅ Status
                        Text("Status: ${p.status}", style = MaterialTheme.typography.bodyMedium)

                        // 📅 Date
                        Text("Date: ${p.date}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}