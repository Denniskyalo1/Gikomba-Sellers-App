package com.example.thriftlink.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.thriftlink.ui.screens.*
import com.example.thriftlink.ui.screens.AddProductScreen
import com.example.thriftlink.ui.screens.EarningsScreen
import com.example.thriftlink.ui.screens.EditProductScreen
import com.example.thriftlink.ui.screens.PaymentHistoryScreen
import com.example.thriftlink.ui.screens.SellerDashboardScreen
import com.example.thriftlink.ui.screens.SellerStatsScreen
import com.example.thriftlink.ui.viewmodel.AnalyticsViewModel
import com.example.thriftlink.viewmodel.ProductViewModel



@Composable
fun AppNavGraph(
    navController: NavHostController,
    viewModel: ProductViewModel,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit,
    analyticsViewModel: AnalyticsViewModel
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
// --------------------------------------------------
        // SELLER DASHBOARD
        // --------------------------------------------------
        composable("seller_dashboard") {
            SellerDashboardScreen(
                productViewModel = viewModel,
                analyticsViewModel = analyticsViewModel,
                onEarningsClick = { navController.navigate("seller_earnings") },
                onAddProductClick = { navController.navigate("seller_add_product") },
                onOpenProduct = { id -> navController.navigate("seller_edit_product/$id") }
            )
        }

        // --------------------------------------------------
        // SELLER EARNINGS
        // --------------------------------------------------
        composable("seller_earnings") {
            EarningsScreen(
                analyticsViewModel = analyticsViewModel,
                onBack = { navController.popBackStack() },
                onOpenStats = { navController.navigate("seller_stats") },
                onPaymentHistory = { navController.navigate("seller_payment_history") }
            )
        }

        // --------------------------------------------------
        // SELLER STATS
        // --------------------------------------------------
        composable("seller_stats") {
            SellerStatsScreen(
                analyticsViewModel = analyticsViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        // --------------------------------------------------
        // ADD PRODUCT
        // --------------------------------------------------
        // ADD PRODUCT (ensure it uses productViewModel)
        composable("seller_add_product") {
            AddProductScreen(
                productViewModel = viewModel,
                onFinish = { navController.popBackStack() },
                currentSellerName = "Seller",              // replace with actual logged-in seller name if available
                currentSellerId = "user-seller-1"
            )
        }

        // --------------------------------------------------
        // PAYMENT HISTORY
        // --------------------------------------------------
        composable("seller_payment_history") {
            PaymentHistoryScreen(
                analyticsViewModel = analyticsViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        // --------------------------------------------------
        // EDIT PRODUCT
        // --------------------------------------------------
        composable(
            "seller_edit_product/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments!!.getInt("productId")
            EditProductScreen(
                productId = id,
                productViewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}



