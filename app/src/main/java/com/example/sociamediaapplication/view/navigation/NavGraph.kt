package com.example.sociamediaapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.repository.AuthRepository
import com.example.sociamediaapplication.data.repository.GroupRepository
import com.example.sociamediaapplication.data.repository.ReelRepository
import com.example.sociamediaapplication.view.screens.AuthScreen
import com.example.sociamediaapplication.view.screens.ComingSoonScreen
import com.example.sociamediaapplication.view.screens.MainScreen
import com.example.sociamediaapplication.view.screens.ReelsScreen
import com.example.sociamediaapplication.view.screens.SplashScreen
import com.example.sociamediaapplication.view.screens.StatusEditorScreen
import com.example.sociamediaapplication.viewmodel.AuthUiState
import com.example.sociamediaapplication.viewmodel.AuthViewModel
import com.example.sociamediaapplication.viewmodel.GroupViewModel
import com.example.sociamediaapplication.viewmodel.ReelsViewModel
import com.example.sociamediaapplication.viewmodel.factory.AuthViewModelFactory
import com.example.sociamediaapplication.viewmodel.factory.GroupViewModelFactory
import com.example.sociamediaapplication.viewmodel.factory.ReelsViewModelFactory

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()

    val context = LocalContext.current.applicationContext

    val tokenManager = remember { TokenManager(context) }
    val repository = remember { AuthRepository(tokenManager) }
    val factory = remember { AuthViewModelFactory(repository) }

    val authViewModel: AuthViewModel = viewModel(factory = factory)
    val authState by authViewModel.authState.collectAsState()

    val reelRepository = remember { ReelRepository(tokenManager) }
    val reelFactory = remember { ReelsViewModelFactory(reelRepository) }
    val reelViewModel: ReelsViewModel = viewModel(factory = reelFactory)

    val groupRepository = remember { GroupRepository(tokenManager) }
    val groupFactory = remember { GroupViewModelFactory(groupRepository) }
    val groupViewModel: GroupViewModel = viewModel(factory = groupFactory)


    val reels by reelViewModel.reels.collectAsState()
    val loading by reelViewModel.loading.collectAsState()

    LaunchedEffect(authState) {
        if (authState is AuthUiState.LoggedOut) {
            navController.navigate(Routes.Auth.route)
            authViewModel.resetState()
        }
    }

    NavHost(
        navController = navController,
        startDestination = Routes.Splash.route
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
                mainNavController = navController,
                authViewModel = authViewModel,   // ✅ pass it,
                groupViewModel = groupViewModel
            )
        }

        composable(Routes.EditStatus.route) {
            StatusEditorScreen()
        }

//        composable(Routes.Reels.route) {
//            LaunchedEffect(Unit) {
//                reelViewModel.loadReels()
//            }
//            ReelsScreen(
//                loading = loading,
//                reels = reels,
//                onLike = {
//                    reelViewModel.toggleLike(it)
//                },
//                onSave = {
//                    reelViewModel.toggleSave(it)
//                }
//            )

//            ComingSoonScreen()
//        }

    }
}
