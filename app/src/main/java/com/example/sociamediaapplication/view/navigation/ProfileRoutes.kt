package com.example.sociamediaapplication.view.navigation

sealed class ProfileRoutes(val route: String){
    object AddStatus: ProfileRoutes("statusEdit")
    object EditProfile: ProfileRoutes("editProfile")
    object Menu: ProfileRoutes("menu")
}