package com.tracky.tracker.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tracky.tracker.presentation.navigation.NavRoutes
import com.tracky.tracker.utils.ensureLocationEnabled
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    locationPermissionGranted: Boolean,
    viewModel: LoginViewModel = koinViewModel()
) {
    var email by remember { mutableStateOf("candidate@trucky.ro") }
    var password by remember { mutableStateOf("3}qyI:96dRW1") }

    val loginState by viewModel.loginState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!locationPermissionGranted) {
            Text(
                text = "Location permission is required to login",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Button(
            onClick = { viewModel.login(email, password) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = locationPermissionGranted && loginState !is LoginState.Loading
        ) {
            Text("Login")
        }

        when (loginState) {
            is LoginState.Loading -> CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
            is LoginState.Error -> Text(
                text = (loginState as LoginState.Error).message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 16.dp)
            )
            is LoginState.Success -> LaunchedEffect(Unit) {
                ensureLocationEnabled(context) {
                    navHostController.navigate(NavRoutes.Main.route) {
                        popUpTo(NavRoutes.Login.route) { inclusive = true }
                    }
                }
            }
            else -> {}
        }
    }
}

