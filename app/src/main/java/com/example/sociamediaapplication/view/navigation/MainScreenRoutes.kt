package com.example.sociamediaapplication.view.navigation

sealed class MainRoutes(val route: String) {
    object Home : MainRoutes("home")
    object Search : MainRoutes("search")
    object Reels : MainRoutes("reels")
    object Add : MainRoutes("upload")
    object Chat : MainRoutes("chat")
    object Profile : MainRoutes("profile")
    object Category: MainRoutes("category")
    object Menu: MainRoutes("menu")
    object Notifications: MainRoutes("notifications")
    object Status: MainRoutes("status")
    object Videos: MainRoutes("videos")

}
