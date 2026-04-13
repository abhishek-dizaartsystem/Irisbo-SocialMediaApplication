package com.example.sociamediaapplication.view.navigation

sealed class FriendRoutes(val route: String){
    object Friends: FriendRoutes("friends")

    object FriendRequests: FriendRoutes("friendRequest")
}
