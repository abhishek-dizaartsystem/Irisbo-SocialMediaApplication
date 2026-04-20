package com.example.sociamediaapplication.view.navigation

sealed class MainRoutes(val route: String) {
    object Home1 : MainRoutes("home1")
    object Search : MainRoutes("search")
    object Reels : MainRoutes("reels")
    object Add : MainRoutes("upload")
    object Chats : MainRoutes("chats")
    object Profile : MainRoutes("profile")
    object Category: MainRoutes("category")
    object Menu: MainRoutes("menu")
    object Notifications: MainRoutes("notifications")
    object Home2: MainRoutes("home2")
    object Videos: MainRoutes("videos")
    object OtherProfile: MainRoutes("profile/{userId}"){
        fun createRoute(userId: Int) = "profile/$userId"
    }
    object StoryView: MainRoutes("story/{userId}"){
        fun createRoute(userId: Int) = "story/$userId"
    }

}
