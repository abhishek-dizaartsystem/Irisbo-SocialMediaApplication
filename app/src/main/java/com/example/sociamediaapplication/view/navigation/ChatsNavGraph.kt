package com.example.sociamediaapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sociamediaapplication.data.preferences.SocketManager
import com.example.sociamediaapplication.view.screens.ChatScreen
import com.example.sociamediaapplication.view.screens.ChatsScreen
import com.example.sociamediaapplication.viewmodel.ChatViewModel
import com.example.sociamediaapplication.viewmodel.ProfileViewModel
import org.json.JSONObject

@Composable
fun ChatsNavGraph(
    chatViewModel: ChatViewModel,
    profileViewModel: ProfileViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ChatsRoutes.ChatsList.route
    ){
        composable(ChatsRoutes.ChatsList.route) {

            LaunchedEffect(Unit) {
                chatViewModel.fetchConversations()
            }

            ChatsScreen(
                onChatClick = { conversationId ->
                    navController.navigate(
                        ChatsRoutes.Chat.createRoute(conversationId)
                    )
                },
                chatViewModel = chatViewModel
            )
        }

        composable(
            route = ChatsRoutes.Chat.route,
            arguments = listOf(
                navArgument("conversationId") { type = NavType.IntType }
            )
        ) { backStackEntry ->

            val conversationId = backStackEntry.arguments?.getInt("conversationId")

            LaunchedEffect(conversationId) {
                chatViewModel.fetchMessages(conversationId?:0)

                chatViewModel.fetchConversationDetails(conversationId?:0)

                val socket = SocketManager.getSocket()

                val json = JSONObject()
                json.put("conversationId", conversationId)

                socket?.emit("conversation:join", json)

                // 🔥 ADD THIS LINE
                chatViewModel.observeSocketMessages(conversationId ?: 0)

                chatViewModel.observeDeleteMessages()
            }

            ChatScreen(
                navController = navController,
                chatViewModel = chatViewModel,
                profileViewModel = profileViewModel
            )
        }
    }
}