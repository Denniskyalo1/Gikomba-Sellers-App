package com.example.thriftlink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.thriftlink.data.UserDataManager
import com.example.thriftlink.data.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyerSignUpScreen(
    onSignUpSuccess: (User) -> Unit,
    onBackClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
    val userManager = UserDataManager(context)

    Scaffold(
        topBar = { TopAppBar(title = { Text("Buyer Sign Up") }) }
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
                    // 1. Clear any previous error message
                    errorMessage = ""

                    // 2. Perform the registration logic
                    val registeredUser = userManager.register(
                        UserDataManager.UserType.BUYER,
                        email,
                        password
                    )

                    // 3. Update state and navigate based on the result
                    if (registeredUser != null) {
                        onSignUpSuccess(registeredUser)
                    } else {
                        // Set the error message here
                        errorMessage = "Sign up failed. Please check your inputs."
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign Up")
            }
            //DISPLAY THE ERROR TEXT HERE (in the composable context)
            if (errorMessage.isNotEmpty()) {
                Spacer(Modifier.height(16.dp))
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = onBackClick) {
                Text("Back to Login")
            }
        }
    }
}


