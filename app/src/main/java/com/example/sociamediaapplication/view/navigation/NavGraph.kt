package com.example.sociamediaapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.repository.AuthRepository
import com.example.sociamediaapplication.view.screens.AuthScreen
import com.example.sociamediaapplication.view.screens.MainScreen
import com.example.sociamediaapplication.view.screens.ReelsScreen
import com.example.sociamediaapplication.view.screens.SplashScreen
import com.example.sociamediaapplication.view.screens.StatusEditorScreen
import com.example.sociamediaapplication.viewmodel.AuthViewModel
import com.example.sociamediaapplication.viewmodel.AuthViewModelFactory

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()

    val context = LocalContext.current

    val tokenManager = remember { TokenManager(context) }
    val repository = remember { AuthRepository(tokenManager) }
    val factory = remember { AuthViewModelFactory(repository) }


    NavHost(
        navController = navController,
        startDestination = Routes.Main.route
    ) {

        composable(Routes.Splash.route) {
            SplashScreen(
                isLoggedIn = repository.isLoggedIn(),
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

            val authViewModel: AuthViewModel = viewModel(factory = factory)

            val authState by authViewModel.authState.collectAsState()

            AuthScreen(
                authState = authState,
                onLogin = {email, password->
                    authViewModel.login(email, password)
                },
                onSignup = {name, username, email, password->
                    authViewModel.signup(name, username, email, password)
                },
                onAuthSuccess = {
                    navController.navigate(Routes.Splash.route) {
                        popUpTo(Routes.Auth.route) { inclusive = true }
                    }
                },
                onResetState = {
                    authViewModel.resetState()
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
