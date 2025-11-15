package com.example.thriftlink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.thriftlink.data.UserDataManager
import com.example.thriftlink.data.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyerLoginScreen(
    onLoginSuccess: (User) -> Unit, // returns User object
    onSignUpClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current
    val userManager = UserDataManager(context) // instantiate manager

    Scaffold(
        topBar = { TopAppBar(title = { Text("Buyer Login") }) }
    ) { padding ->
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
                label = { Text("Email") },
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

            Button(
                onClick = {
                    val user = userManager.login(UserDataManager.UserType.BUYER, email, password)
                    onLoginSuccess(user) // pass User object to parent
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            Spacer(Modifier.height(8.dp))

            TextButton(onClick = onSignUpClick) {
                Text("Don't have an account? Sign up")
            }
        }
    }
}
