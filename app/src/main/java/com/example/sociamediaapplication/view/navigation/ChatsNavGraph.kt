package com.example.sociamediaapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sociamediaapplication.view.screens.ChatScreen
import com.example.sociamediaapplication.view.screens.ChatsScreen

@Composable
fun ChatsNavGraph(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ChatsRoutes.ChatsList.route
    ){
        composable(ChatsRoutes.ChatsList.route) {
            ChatsScreen(
                onChatClick = { userId ->
                    navController.navigate(
                        ChatsRoutes.Chat.createRoute(userId)
                    )
                }
            )
        }

        composable(
            route = ChatsRoutes.Chat.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val userId = backStackEntry.arguments?.getString("userId")

            ChatScreen(
                userId = userId ?: "",
                navController
            )
        }
    }
}