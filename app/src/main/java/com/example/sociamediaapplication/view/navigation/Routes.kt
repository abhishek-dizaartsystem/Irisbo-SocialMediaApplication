package com.example.sociamediaapplication.view.navigation

sealed class Routes(val route: String) {
    object Splash : Routes("splash")
    object Auth : Routes("auth")
    object Main : Routes("main")
    object EditStatus: Routes("editStatus")
    object Chat: Routes("chat")
}

