package com.example.sociamediaapplication.view.navigation

sealed class MenuRoutes(val route: String) {
    object Marketplace: MenuRoutes("marketplace")
    object Groups: MenuRoutes("groups")
    object UserVideos: MenuRoutes("userVideos")
    object UploadVideo: MenuRoutes("uploadVideo")
    object Memories: MenuRoutes("memories")
    object Pages: MenuRoutes("pages")
    object Friends: MenuRoutes("friends")
    object Events: MenuRoutes("events")
    object Jobs: MenuRoutes("jobs")
    object Gaming: MenuRoutes("gaming")
    object VideoAnalytics: MenuRoutes("videoAnalytics")
    object AdvancedSettings: MenuRoutes("advancedSettings")
    object VideoMonetization: MenuRoutes("videoMonetization")
    object Profile: MenuRoutes("profile")
    object Settings: MenuRoutes("settings")

}