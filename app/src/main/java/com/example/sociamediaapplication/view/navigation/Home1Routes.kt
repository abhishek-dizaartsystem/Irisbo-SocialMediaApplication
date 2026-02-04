package com.example.sociamediaapplication.view.navigation

sealed class Home1Routes(val route: String) {
    object UserPost: Home1Routes("userPost")
    object UserComment: Home1Routes("userComment")
}