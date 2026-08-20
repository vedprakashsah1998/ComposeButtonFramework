package com.infinity8.minicompose

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.infinity8.minicompose.ui.theme.LoginScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {


        composable(Screen.Home.route) {
            HomeScreen(
                onLoginClick = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen()
        }

    }
}

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Home : Screen("home")
}