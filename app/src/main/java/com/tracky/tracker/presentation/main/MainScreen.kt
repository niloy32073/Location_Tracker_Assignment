package com.tracky.tracker.presentation.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tracky.tracker.data.service.LocationService
import com.tracky.tracker.MainActivity
import com.tracky.tracker.presentation.navigation.NavRoutes
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    val location by viewModel.location.collectAsState()

    LaunchedEffect(isLoggedIn) {
        if (!isLoggedIn) {
            context.stopService(Intent(context, LocationService::class.java))
            navController.navigate("login") {
                popUpTo("main") { inclusive = true }
            }
            Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
        } else {
            (context as? MainActivity)?.checkAndStartLocationService()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Main Screen - Tracking location every 10s", modifier = Modifier.padding(bottom = 24.dp))

        if (location != null) {
            Text("Latitude: ${location!!.latitude}")
            Text("Longitude: ${location!!.longitude}")
        } else {
            Text("Waiting for location...")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            viewModel.logout()
            navController.navigate(NavRoutes.Login.route) {
                popUpTo(NavRoutes.Main.route) { inclusive = true }
            }}) {
            Text("Logout")
        }
    }
}

