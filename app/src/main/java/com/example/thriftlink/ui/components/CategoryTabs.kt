// CategoryTabs.kt
package com.example.thriftlink.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Composable
fun CategoryTabs(selected: String, onSelect: (String) -> Unit) {
    val categories = listOf("All", "Women", "Men")
    var current by remember { mutableStateOf(selected) }

    ScrollableTabRow(selectedTabIndex = categories.indexOf(current)) {
        categories.forEachIndexed { index, category ->
            Tab(selected = current == category,
                onClick = {
                    current = category
                    onSelect(category)
                },
                text = { Text(category) }
            )
        }
    }
}
