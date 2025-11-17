/*package com.example.thriftlink

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
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.thriftlink.data.ProductRepository
import com.example.thriftlink.ui.viewmodel.AnalyticsViewModel

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
                val analyticsViewModelFactory = viewModelFactory {
                    initializer {
                        AnalyticsViewModel(productRepository = ProductRepository)
                    }
                }
                // 3. Define the theme toggle function to pass to the NavGraph
                val onThemeToggle: () -> Unit = {
                    darkTheme = !darkTheme
                }

                // 4. Pass the theme control functions to AppNavGraph
                AppNavGraph(
                    navController = navController,
                    viewModel = vm,
                    analyticsViewModel = analyticsViewModel,
                    isDarkTheme = darkTheme,
                    onThemeToggle = onThemeToggle

                )
            }
        }
    }
}*/
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
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.thriftlink.data.ProductRepository
import com.example.thriftlink.ui.viewmodel.AnalyticsViewModel

class MainActivity : ComponentActivity() {

    // Define the single instance of the repository outside the Composable scope
    //private val productRepository = ProductRepository()

    private val productRepositoryInstance = ProductRepository()
    // Define the factory that uses the repository instance
    private val analyticsViewModelFactory = viewModelFactory {
        initializer {
            // FIX: Pass the repository instance, not the class name
            AnalyticsViewModel(productRepository = productRepositoryInstance)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            //  1. Define the Dark Theme state
            var darkTheme by remember { mutableStateOf(false) }

            //  2. Pass the darkTheme state to your custom theme, and REMOVE dynamicColor
            ThriftLinkTheme(darkTheme = darkTheme) {

                val navController = rememberNavController()
                val vm: ProductViewModel = viewModel()

                // FIX 2: Use the defined factory to create the analyticsViewModel instance
                val analyticsViewModel: AnalyticsViewModel = viewModel(factory = analyticsViewModelFactory)

                // 3. Define the theme toggle function to pass to the NavGraph
                val onThemeToggle: () -> Unit = {
                    darkTheme = !darkTheme
                }

                // 4. Pass the theme control functions to AppNavGraph
                AppNavGraph(
                    navController = navController,
                    viewModel = vm,
                    analyticsViewModel = analyticsViewModel, // Now this reference is resolved
                    isDarkTheme = darkTheme,
                    onThemeToggle = onThemeToggle
                )
            }
        }
    }
}