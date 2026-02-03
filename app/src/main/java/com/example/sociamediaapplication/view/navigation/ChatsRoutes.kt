package com.example.sociamediaapplication.view.navigation

sealed class ChatsRoutes(val route:String){
    object ChatsList : ChatsRoutes("chats_list")

    object Chat : ChatsRoutes("chat/{userId}") {
        fun createRoute(userId: String) = "chat/$userId"
    }
}