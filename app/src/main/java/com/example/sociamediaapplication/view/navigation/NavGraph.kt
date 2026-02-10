package com.example.sociamediaapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.data.repository.AuthRepository
import com.example.sociamediaapplication.view.screens.AuthScreen
import com.example.sociamediaapplication.view.screens.MainScreen
import com.example.sociamediaapplication.view.screens.ReelsScreen
import com.example.sociamediaapplication.view.screens.SplashScreen
import com.example.sociamediaapplication.view.screens.StatusEditorScreen
import com.example.sociamediaapplication.viewmodel.AuthViewModel

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Main.route
    ) {

        composable(Routes.Splash.route) {
            SplashScreen(
                onNavigateToAuth = {
                    navController.navigate(Routes.Auth.route) {
                        popUpTo(Routes.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToMain = {
                    navController.navigate(Routes.Main.route) {
                        popUpTo(Routes.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.Auth.route) {

            val authViewModel = remember {
                AuthViewModel(AuthRepository())
            }

            AuthScreen(
                authViewModel = authViewModel,
                onAuthSuccess = {
                    navController.navigate(Routes.Splash.route) {
                        popUpTo(Routes.Auth.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.Main.route) {
            MainScreen(
                navController
            )
        }

        composable(Routes.EditStatus.route) {
            StatusEditorScreen()
        }

        composable(Routes.Reels.route) {
            ReelsScreen()
        }

    }
}
