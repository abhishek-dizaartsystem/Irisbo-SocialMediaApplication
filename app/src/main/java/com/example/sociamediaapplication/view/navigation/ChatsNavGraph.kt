package com.example.sociamediaapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

            val profile by profileViewModel.profile.collectAsState()

            LaunchedEffect(Unit) {
                val uid = profile?.data?.id ?: return@LaunchedEffect
                chatViewModel.fetchConversations()
                chatViewModel.observeConversationUpdates(uid)
                chatViewModel.observeReadUpdates(uid, 0) // 0 = no active conversation
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
            val profile by profileViewModel.profile.collectAsState()
            LaunchedEffect(conversationId, profile?.data?.id) {
                val uid = profile?.data?.id ?: return@LaunchedEffect
                val convId = conversationId ?: return@LaunchedEffect

                chatViewModel.fetchMessages(convId)
                chatViewModel.fetchConversationDetails(convId)

                val socket = SocketManager.getSocket()
                val json = JSONObject()
                json.put("conversationId", convId)
                socket?.emit("conversation:join", json)

                chatViewModel.observeSocketMessages(convId, uid)
                chatViewModel.observeDeleteMessages(convId)
                chatViewModel.observeReadUpdates(uid, convId) // 👈 re-register with conversation context
            }

            ChatScreen(
                navController = navController,
                chatViewModel = chatViewModel,
                profileViewModel = profileViewModel,
                conversationId = conversationId
            )
        }
    }
}