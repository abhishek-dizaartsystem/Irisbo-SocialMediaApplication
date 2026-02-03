package com.example.sociamediaapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.view.screens.EditProfileScreen
import com.example.sociamediaapplication.view.screens.MenuScreen
import com.example.sociamediaapplication.view.screens.ProfileScreen

@Composable
fun ProfileNavGraph(
    mainNavController: NavController
){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "profileMain"
    ){
        composable("profileMain"){
            ProfileScreen(
                navController = navController,
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