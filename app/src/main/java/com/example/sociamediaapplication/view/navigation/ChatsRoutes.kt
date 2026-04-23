package com.example.sociamediaapplication.view.navigation

sealed class ChatsRoutes(val route:String){
    object ChatsList : ChatsRoutes("chats_list")

    object Chat : ChatsRoutes("chat/{conversationId}") {
        fun createRoute(conversationId: Int) = "chat/$conversationId"
    }
}