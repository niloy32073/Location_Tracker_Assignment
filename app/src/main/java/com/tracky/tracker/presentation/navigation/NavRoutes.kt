package com.tracky.tracker.presentation.navigation

sealed class NavRoutes(val route: String) {
    object Login : NavRoutes("login")
    object Main : NavRoutes("main")
}