package com.example.thriftlink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thriftlink.navigation.AppNavGraph
import com.example.thriftlink.ui.theme.ThriftLinkTheme
import com.example.thriftlink.viewmodel.ProductViewModel
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            // ⭐ 1. Define the Dark Theme state ⭐
            var darkTheme by remember { mutableStateOf(false) }

            // ⭐ 2. Pass the darkTheme state to your custom theme, and REMOVE dynamicColor ⭐
            ThriftLinkTheme(darkTheme = darkTheme) {

                val navController = rememberNavController()
                val vm: ProductViewModel = viewModel()

                // 3. Define the theme toggle function to pass to the NavGraph
                val onThemeToggle: () -> Unit = {
                    darkTheme = !darkTheme
                }

                // 4. Pass the theme control functions to AppNavGraph
                AppNavGraph(
                    navController = navController,
                    viewModel = vm,
                    isDarkTheme = darkTheme,
                    onThemeToggle = onThemeToggle
                )
            }
        }
    }
}