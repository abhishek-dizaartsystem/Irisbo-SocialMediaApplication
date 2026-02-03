package com.example.sociamediaapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.view.screens.AdvancedSettingsScreen
import com.example.sociamediaapplication.view.screens.EventsScreen
import com.example.sociamediaapplication.view.screens.FriendsScreen
import com.example.sociamediaapplication.view.screens.GamesScreen
import com.example.sociamediaapplication.view.screens.GroupsScreen
import com.example.sociamediaapplication.view.screens.JobsScreen
import com.example.sociamediaapplication.view.screens.MarketplaceScreen
import com.example.sociamediaapplication.view.screens.MemoriesScreen
import com.example.sociamediaapplication.view.screens.MenuScreen
import com.example.sociamediaapplication.view.screens.MonetizationScreen
import com.example.sociamediaapplication.view.screens.PagesScreen
import com.example.sociamediaapplication.view.screens.UserVideosScreen
import com.example.sociamediaapplication.view.screens.VideoAnalyticsScreen

@Composable
fun MenuNavGraph(mainNavController: NavController){
    val navController = rememberNavController()

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
                }
            )
        }
        composable(MenuRoutes.Marketplace.route) {
            MarketplaceScreen()
        }
        composable(MenuRoutes.Groups.route) {
            GroupsScreen()
        }
        composable(MenuRoutes.UserVideos.route) {
            UserVideosScreen()
        }
        composable(MenuRoutes.Memories.route) {
            MemoriesScreen()
        }
        composable(MenuRoutes.Pages.route) {
            PagesScreen()
        }
        composable(MenuRoutes.Friends.route) {
            FriendsScreen()
        }
        composable(MenuRoutes.Events.route) {
            EventsScreen()
        }
        composable(MenuRoutes.Jobs.route) {
            JobsScreen()
        }
        composable(MenuRoutes.Gaming.route) {
            GamesScreen()
        }
        composable(MenuRoutes.VideoAnalytics.route) {
            VideoAnalyticsScreen()
        }
        composable(MenuRoutes.AdvancedSettings.route) {
            AdvancedSettingsScreen()
        }
        composable(MenuRoutes.VideoMonetization.route) {
            MonetizationScreen()
        }
        composable(MenuRoutes.Profile.route) {
            ProfileNavGraph(mainNavController)
        }

    }
}