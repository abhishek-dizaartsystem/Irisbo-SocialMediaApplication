package com.example.sociamediaapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.view.screens.AdvancedSettingsScreen
import com.example.sociamediaapplication.view.screens.GamesScreen
import com.example.sociamediaapplication.view.screens.MemoriesScreen
import com.example.sociamediaapplication.view.screens.MenuScreen
import com.example.sociamediaapplication.view.screens.MonetizationScreen
import com.example.sociamediaapplication.view.screens.UserVideosScreen
import com.example.sociamediaapplication.view.screens.VideoAnalyticsScreen
import com.example.sociamediaapplication.viewmodel.AuthViewModel
import com.example.sociamediaapplication.viewmodel.ChatViewModel
import com.example.sociamediaapplication.viewmodel.FriendViewModel
import com.example.sociamediaapplication.viewmodel.PostViewModel
import com.example.sociamediaapplication.viewmodel.ProfileViewModel
import com.example.sociamediaapplication.viewmodel.ReelsViewModel
import com.example.sociamediaapplication.viewmodel.UploadViewModel
import com.example.sociamediaapplication.viewmodel.VideoViewModel

@Composable
fun MenuNavGraph(
    mainNavController: NavController,
    authViewModel: AuthViewModel,
    profileViewModel: ProfileViewModel,
    uploadViewModel: UploadViewModel,
    postViewModel: PostViewModel,
    friendViewModel: FriendViewModel,
    reelViewModel: ReelsViewModel,
    mainNavController2: NavHostController,
    chatViewModel: ChatViewModel,
    videoViewModel: VideoViewModel
){
    val navController = rememberNavController()

    val profile by profileViewModel.profile.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "menu"
    ){
        composable("menu"){
            MenuScreen(
                onProfile = {
                    navController.navigate(MenuRoutes.Profile.route)
                },
                onMarketplace = {
                    navController.navigate(MenuRoutes.Marketplace.route)
                },
                onGroups = {
                    navController.navigate(MenuRoutes.Groups.route)
                },
                onUserVideos = {
                    navController.navigate(MenuRoutes.UserVideos.route)
                },
                onMemories = {
                    navController.navigate(MenuRoutes.Memories.route)
                },
                onPages = {
                    navController.navigate(MenuRoutes.Pages.route)
                },
                onFriends = {
                    navController.navigate(MenuRoutes.Friends.route)
                },
                onEvents = {
                    navController.navigate(MenuRoutes.Events.route)
                },
                onJobs = {
                    navController.navigate(MenuRoutes.Jobs.route)
                },
                onGaming = {
                    navController.navigate(MenuRoutes.Gaming.route)
                },
                onVideoAnalytics = {
                    navController.navigate(MenuRoutes.VideoAnalytics.route)
                },
                onAdvancedSettings = {
                    navController.navigate(MenuRoutes.AdvancedSettings.route)
                },
                onVideoMonetization = {
                    navController.navigate(MenuRoutes.VideoMonetization.route)
                },
                onSettings = {
                    navController.navigate(MenuRoutes.Settings.route)
                },
                profileImg = profile?.data?.profile_image,
                name = profile?.data?.name ?: "John Doe"
            )
        }
        composable(MenuRoutes.Marketplace.route) {
            MarketNavGraph(bNavController = navController)
        }
        composable(MenuRoutes.Groups.route) {
            GroupsNavGraph(
                navController,
                uploadViewModel = uploadViewModel,
                postViewModel = postViewModel,
                mainNavController2 = mainNavController2,
                profileViewModel = profileViewModel
            )
        }
        composable(MenuRoutes.UserVideos.route) {

            LaunchedEffect(Unit) {
                videoViewModel.fetchMyVideos()
            }

            UserVideosScreen(
                navController,
                videoViewModel = videoViewModel
            )
        }
        composable(MenuRoutes.Memories.route) {
            MemoriesScreen(navController)
        }
        composable(MenuRoutes.Pages.route) {
            PagesNavGraph(bNavController = navController)
        }
        composable(MenuRoutes.Friends.route) {
            FriendNavGraph(
                navController,
                friendViewModel = friendViewModel,
                mainNavController2 = mainNavController2,
                chatViewModel = chatViewModel,
                profileViewModel = profileViewModel
            )
        }
        composable(MenuRoutes.Events.route) {
            EventNavGraph(bNavController = navController,)
        }
        composable(MenuRoutes.Jobs.route) {
            JobsNavGraph(navController)
        }
        composable(MenuRoutes.Gaming.route) {
            GamesScreen(navController)
        }
        composable(MenuRoutes.VideoAnalytics.route) {
            VideoAnalyticsScreen(navController)
        }
        composable(MenuRoutes.AdvancedSettings.route) {
            AdvancedSettingsScreen(navController)
        }
        composable(MenuRoutes.VideoMonetization.route) {
            MonetizationScreen(navController)
        }
        composable(MenuRoutes.Profile.route) {
            ProfileNavGraph(
                mainNavController = mainNavController,
                authViewModel = authViewModel,
                profileViewModel = profileViewModel,
                uploadViewModel = uploadViewModel,
                postViewModel = postViewModel,
                friendViewModel = friendViewModel,
                reelViewModel = reelViewModel,
                mainNavController2 = mainNavController2,
                chatViewModel = chatViewModel,
                videoViewModel = videoViewModel
            )
        }

        composable(MenuRoutes.Settings.route) {
            SettingsNavGraph(
                mainNavController = mainNavController,
                bNavController = navController,
                authViewModel = authViewModel,
                profileViewModel = profileViewModel
            )
        }

    }
}