package com.example.sociamediaapplication.view.navigation

sealed class MainRoutes(val route: String, val routePattern: String = route) {
    object Home1 : MainRoutes("home1", "home1?postId={postId}") {
        fun createRoute(postId: Int) = "home1?postId=$postId"
    }
    object Search : MainRoutes("search")
    object Reels : MainRoutes("reels", "reels?reelId={reelId}") {
        fun createRoute(reelId: Int) = "reels?reelId=$reelId"
    }
    object Add : MainRoutes("upload")
    object Chats : MainRoutes("chats")
    object Profile : MainRoutes("profile")
    object Category: MainRoutes("category", "category?videoId={videoId}") {
        fun createRoute(videoId: Int) = "category?videoId=$videoId"
    }
    object Menu: MainRoutes("menu", "menu?eventId={eventId}") {
        fun createRoute(eventId: Int) = "menu?eventId=$eventId"
    }
    object Notifications: MainRoutes("notifications")
    object Home2: MainRoutes("home2")
    object Videos: MainRoutes("videos")
    object OtherProfile: MainRoutes("profile/{userId}"){
        fun createRoute(userId: Int) = "profile/$userId"
    }
    object StoryView: MainRoutes("story/{userId}"){
        fun createRoute(userId: Int) = "story/$userId"
    }

    object StoryViewUser: MainRoutes("story/user")

}
