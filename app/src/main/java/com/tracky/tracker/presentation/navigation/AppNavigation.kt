package com.tracky.tracker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tracky.tracker.presentation.login.LoginScreen
import com.tracky.tracker.presentation.main.MainScreen


@Composable
fun AppNavigation(
    navController: NavHostController,
    isLoggedIn: Boolean,
    locationPermissionGranted: Boolean,
    modifier: Modifier = Modifier
) {
    val startDestination = if (isLoggedIn) NavRoutes.Main.route else NavRoutes.Login.route

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(NavRoutes.Login.route) {
            LoginScreen(
                navHostController = navController,
                locationPermissionGranted = locationPermissionGranted
            )
        }
        composable(NavRoutes.Main.route) {
            MainScreen(navController = navController)
        }
    }
}