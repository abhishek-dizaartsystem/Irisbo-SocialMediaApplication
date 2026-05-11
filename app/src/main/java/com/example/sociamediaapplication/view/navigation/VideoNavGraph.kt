package com.example.sociamediaapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.view.screens.CategoryScreen
import com.example.sociamediaapplication.view.screens.VideosScreen
import com.example.sociamediaapplication.viewmodel.VideoViewModel

@Composable
fun VideoNavGraph(videoViewModel: VideoViewModel) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = VideoRoutes.VideoCategory.route
    ) {
        composable(VideoRoutes.VideoCategory.route){
            CategoryScreen(
                videoViewModel = videoViewModel,
                onSearch = {
                    navController.navigate(VideoRoutes.Videos.route)
                }
            )
        }

        composable(VideoRoutes.Videos.route) {
            VideosScreen(
                videoViewModel = videoViewModel
            )
        }
    }
}