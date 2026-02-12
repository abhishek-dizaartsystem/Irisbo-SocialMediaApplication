package com.example.sociamediaapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.repository.ProfileRepository
import com.example.sociamediaapplication.view.screens.EditProfileScreen
import com.example.sociamediaapplication.view.screens.MenuScreen
import com.example.sociamediaapplication.view.screens.ProfileScreen
import com.example.sociamediaapplication.viewmodel.PageViewModel
import com.example.sociamediaapplication.viewmodel.ProfileViewModel
import com.example.sociamediaapplication.viewmodel.factory.ProfileViewModelFactory

@Composable
fun ProfileNavGraph(
    mainNavController: NavController
){
    val navController = rememberNavController()

    val context = LocalContext.current.applicationContext
    val tokenManager = remember { TokenManager(context) }
    val repository = remember { ProfileRepository(tokenManager) }
    val factory = remember { ProfileViewModelFactory(repository) }

    val viewModel: ProfileViewModel = viewModel(factory = factory)

    NavHost(
        navController = navController,
        startDestination = "profileMain"
    ){
        composable("profileMain"){
            ProfileScreen(
                viewModel = viewModel,
                onEditStatus = {
                    mainNavController.navigate(Routes.EditStatus.route)
                },
                onEditProfile = {
                    navController.navigate(ProfileRoutes.EditProfile.route)
                },
                onMenu = {
                    navController.navigate(ProfileRoutes.Menu.route)
                }
            )
        }
        composable(ProfileRoutes.EditProfile.route){
            EditProfileScreen(
                navController = navController
            )
        }
        composable(ProfileRoutes.Menu.route) {
            MenuNavGraph(mainNavController)
        }
    }
}