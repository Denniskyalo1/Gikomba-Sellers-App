package com.example.thriftlink.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.thriftlink.ui.screens.*

@Composable
fun AppNavGraph(navController: NavHostController, viewModel: com.example.thriftlink.viewmodel.ProductViewModel) {
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(
                onBuyerClick = { navController.navigate("buyer_login") },
                onSellerClick = { navController.navigate("seller_login") }
            )
        }

        composable("buyer_login") {
            BuyerLoginScreen(
                onLoginSuccess = { navController.navigate("buyer_dashboard") },
                onSignUpClick = { navController.navigate("buyer_signup") }
            )
        }

        composable("seller_login") {
            SellerLoginScreen(
                onLoginSuccess = { navController.navigate("seller_dashboard") },
                onSignUpClick = { navController.navigate("seller_signup") }
            )
        }

        composable("buyer_signup") {
            BuyerSignUpScreen(
                // Success action: Navigate to dashboard after signing up
                onSignUpSuccess = { navController.navigate("buyer_dashboard") },
                // Back action: Go back to login screen
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("seller_signup") {
            SellerSignUpScreen(
                // Success action: Navigate to dashboard after signing up
                onSignUpSuccess = { navController.navigate("seller_dashboard") },
                // Back action: Go back to login screen
                onBackClick = { navController.popBackStack() }
            )
        }



        composable("catalog") {
            CatalogScreen(viewModel = viewModel, onOpenDetail = { id ->
                navController.navigate("product_detail/$id")
            })
        }


        composable("product_detail/{productId}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("productId")?.toIntOrNull() ?: 0
            ProductDetailScreen(productId = id, viewModel = viewModel, onBack = { navController.popBackStack() })
        }
    }
}
