package com.example.sociamediaapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sociamediaapplication.view.screens.CategoryScreen
import com.example.sociamediaapplication.view.screens.VideoPlayScreen
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
                onNavigate = {
                    navController.navigate(VideoRoutes.Videos.route)
                }
            )
        }

        composable(VideoRoutes.Videos.route) {
            VideosScreen(
                videoViewModel = videoViewModel,
                onVideoClick = {videoId->
                    navController.navigate(VideoRoutes.Video.createRoute(videoId))
                }
            )
        }

        composable(
            route = VideoRoutes.Video.route,
            arguments = listOf(
                navArgument("videoId"){type = NavType.IntType}
            )) {backStackEntry->

            val videoId = backStackEntry.arguments?.getInt("videoId")

            LaunchedEffect(Unit) {
                videoViewModel.fetchVideo(videoId?:0)
                videoViewModel.fetchRelatedVideos(videoId?:0)
            }

            DisposableEffect(Unit) {
                onDispose {
                    videoViewModel.setFullscreen(false)
                }
            }

            VideoPlayScreen(
                videoViewModel = videoViewModel
            )
        }
    }
}