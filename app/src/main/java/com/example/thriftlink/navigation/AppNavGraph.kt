package com.example.thriftlink.navigation

import SellerDashboardScreen
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.thriftlink.ui.screens.*
import com.example.thriftlink.viewmodel.ProductViewModel



@Composable
fun AppNavGraph(
    navController: NavHostController,
    viewModel: ProductViewModel,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
)
{

    NavHost(navController = navController, startDestination = "welcome") {

        // --- WELCOME ---
        composable("welcome") {
            WelcomeScreen(
                onBuyerClick = { navController.navigate("buyer_login") },
                onSellerClick = { navController.navigate("seller_login") }
            )
        }

        // --- BUYER FLOW ---
        composable("buyer_login") {
            BuyerLoginScreen(
                onLoginSuccess = { user ->
                    // Navigate to Buyer Dashboard
                    navController.navigate("buyer_dashboard")
                },
                onSignUpClick = { navController.navigate("buyer_signup") }
            )
        }

        composable("buyer_signup") {
            BuyerSignUpScreen(
                onSignUpSuccess = {
                    navController.navigate("buyer_dashboard")
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        // Buyer Dashboard (currently showing Catalog)
        /*composable("buyer_dashboard") {
            CatalogScreen(
                viewModel = viewModel,
                onOpenDetail = { id ->
                    navController.navigate("product_detail/$id")
                }
            )
        }*/

        composable("buyer_dashboard") {
            CatalogScreen(
                viewModel = viewModel,
                onOpenDetail = { id ->
                    navController.navigate("product_detail/$id")
                },
                // ADDED: Navigation to Orders page
                onOpenOrders = { navController.navigate("orders") },
                //  ADDED: Action for Logout
                onLogout = {
                    viewModel.logout()
                    // Clears the back stack and navigates to the welcome screen
                    navController.navigate("welcome") {
                        popUpTo("buyer_dashboard") { inclusive = true }
                    }
                },

                isDarkTheme = isDarkTheme,
                onThemeToggle = onThemeToggle
            )
        }
        composable("orders") {
            OrdersScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable("seller_login") {
            SellerLoginScreen(
                onLoginSuccess = {
                    // Navigate to Seller Dashboard
                    navController.navigate("seller_dashboard")
                },
                onSignUpClick = { navController.navigate("seller_signup") }
            )
        }

        composable("seller_signup") {
            SellerSignUpScreen(
                onSignUpSuccess = {
                    // Navigate to Seller Dashboard
                    navController.navigate("seller_dashboard")
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        // SELLER DASHBOARD ROUTE
        composable("seller_dashboard") {
            // You MUST replace this with your actual screen for sellers
            SellerDashboardScreen()
        }

        //  CATALOG & DETAIL
        composable("catalog") {
            CatalogScreen(
                viewModel = viewModel,
                onOpenDetail = { id ->
                    navController.navigate("product_detail/$id")
                },
                onOpenOrders = { navController.navigate("orders") },
                onLogout = {
                    viewModel.logout()
                    navController.navigate("welcome") {
                        popUpTo("catalog") { inclusive = true }
                    }
                },

                isDarkTheme = isDarkTheme,
                onThemeToggle = onThemeToggle
            )
        }

        composable("product_detail/{productId}") { backStackEntry ->
            val id = backStackEntry.arguments
                ?.getString("productId")
                ?.toIntOrNull() ?: return@composable

            ProductDetailScreen(
                productId = id,
                viewModel = viewModel,
                navController = navController
            )
        }
        composable("checkout/{productId}") { backStackEntry ->
            val id = backStackEntry.arguments
                ?.getString("productId")
                ?.toIntOrNull() ?: return@composable

            CheckoutScreen( // You need to create this composable
                productId = id,
                viewModel = viewModel,
                navController = navController
            )
        }
        composable("orders") {
            OrdersScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}
