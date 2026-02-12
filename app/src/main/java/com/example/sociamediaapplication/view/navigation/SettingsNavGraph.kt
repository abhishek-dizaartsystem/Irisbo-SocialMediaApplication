package com.example.sociamediaapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.repository.AuthRepository
import com.example.sociamediaapplication.view.screens.AppearanceScreen
import com.example.sociamediaapplication.view.screens.EditProfileScreen
import com.example.sociamediaapplication.view.screens.HelpCenterScreen
import com.example.sociamediaapplication.view.screens.LanguageScreen
import com.example.sociamediaapplication.view.screens.NotificationScreen
import com.example.sociamediaapplication.view.screens.PrivacyScreen
import com.example.sociamediaapplication.view.screens.SecurityScreen
import com.example.sociamediaapplication.view.screens.SettingsScreen
import com.example.sociamediaapplication.viewmodel.AuthUiState
import com.example.sociamediaapplication.viewmodel.AuthViewModel
import com.example.sociamediaapplication.viewmodel.AuthViewModelFactory

@Composable
fun SettingsNavGraph(
    bNavController: NavController
){
    val navController = rememberNavController()

    val context = LocalContext.current

    val tokenManager = remember { TokenManager(context) }
    val repository = remember { AuthRepository(tokenManager) }
    val factory = remember { AuthViewModelFactory(repository) }

    NavHost(
        navController = navController,
        startDestination = "settings"
    ){
        composable("settings") {

            val authViewModel: AuthViewModel = viewModel(factory = factory)

            val authState by authViewModel.authState.collectAsState()

            LaunchedEffect(authState) {
                if (authState is AuthUiState.Success) {

                    // Navigate to Auth screen
                    bNavController.navigate(Routes.Auth.route) {
                        popUpTo(Routes.Main.route) { inclusive = true }
                    }

                    authViewModel.resetState()
                }
            }
            SettingsScreen(
                bNavController = bNavController,
                onEditProfile = { navController.navigate(SettingsRoutes.EditProfile.route) },
                onSecurity = { navController.navigate(SettingsRoutes.Security.route) },
                onPrivacy = { navController.navigate(SettingsRoutes.Privacy.route) },
                onNotification = { navController.navigate(SettingsRoutes.Notification.route) },
                onAppearance = { navController.navigate(SettingsRoutes.Appearance.route) },
                onLanguage = { navController.navigate(SettingsRoutes.Language.route) },
                onHelpCenter = { navController.navigate(SettingsRoutes.HelpCenter.route) },
                onLogout = {
                    authViewModel.logout()
                }
            )
        }
        composable(SettingsRoutes.EditProfile.route) {
            EditProfileScreen(navController)
        }
        composable(SettingsRoutes.Security.route) {
            SecurityScreen(navController)
        }
        composable(SettingsRoutes.Privacy.route) {
            PrivacyScreen(navController)
        }
        composable(SettingsRoutes.Notification.route) {
            NotificationScreen(navController)
        }
        composable(SettingsRoutes.Appearance.route) {
            AppearanceScreen(navController)
        }
        composable(SettingsRoutes.Language.route) {
            LanguageScreen(navController)
        }
        composable(SettingsRoutes.HelpCenter.route) {
            HelpCenterScreen(navController)
        }
    }
}