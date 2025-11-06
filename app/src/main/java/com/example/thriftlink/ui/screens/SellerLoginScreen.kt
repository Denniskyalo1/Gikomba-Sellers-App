package com.example.thriftlink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.thriftlink.data.UserManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerLoginScreen(onLoginSuccess: () -> Unit, onSignUpClick: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(topBar = { TopAppBar(title = { Text("Seller Login") }) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Business Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(20.dp))
            Button(onClick = {
                UserManager.login(UserManager.UserType.SELLER, email)
                onLoginSuccess()
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Login")
            }
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = onSignUpClick) { Text("Don't have a seller account? Sign up") }
        }
    }
}
