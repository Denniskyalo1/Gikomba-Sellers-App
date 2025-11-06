package com.example.thriftlink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thriftlink.navigation.AppNavGraph
import com.example.thriftlink.ui.theme.ThriftLinkTheme
import com.example.thriftlink.viewmodel.ProductViewModel
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThriftLinkTheme (dynamicColor = false){
                val navController = rememberNavController()
                val vm: ProductViewModel = viewModel()
                AppNavGraph(navController = navController, viewModel = vm)
            }
        }
    }
}
