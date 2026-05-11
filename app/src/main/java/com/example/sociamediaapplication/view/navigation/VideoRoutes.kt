package com.example.sociamediaapplication.view.navigation

sealed class VideoRoutes(val route: String){
    object VideoCategory: VideoRoutes("video/category")
    object Videos: VideoRoutes("videos")
}
