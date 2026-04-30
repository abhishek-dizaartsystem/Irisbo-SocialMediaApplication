package com.example.sociamediaapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sociamediaapplication.data.preferences.SocketManager
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.view.screens.ChatScreen
import com.example.sociamediaapplication.view.screens.FriendsScreen
import com.example.sociamediaapplication.viewmodel.ChatViewModel
import com.example.sociamediaapplication.viewmodel.FriendViewModel
import com.example.sociamediaapplication.viewmodel.ProfileViewModel
import org.json.JSONObject

@Composable
fun FriendNavGraph(
    bNavController: NavHostController,
    friendViewModel: FriendViewModel,
    mainNavController2: NavController,
    chatViewModel: ChatViewModel,
    profileViewModel: ProfileViewModel
) {
    val navController = rememberNavController()

    val context = LocalContext.current.applicationContext

    val tokenManager = remember { TokenManager(context) }

    NavHost(
        navController = navController,
        startDestination = FriendRoutes.Friends.route
    ) {
        composable(FriendRoutes.Friends.route) {

            LaunchedEffect(Unit) {
                friendViewModel.loadSuggestedUsers()
                friendViewModel.getMyFriends()
                friendViewModel.getReceivedRequests()
                friendViewModel.getSentRequests()
            }

            FriendsScreen(
                bNavController = bNavController,
                viewModel = friendViewModel,
                onOtherProfileClick = {userId->
                    mainNavController2.navigate(MainRoutes.OtherProfile.createRoute(userId))
                },
                chatViewModel = chatViewModel,
                navController = navController
            )
        }

        composable(
            route = FriendRoutes.Chat.route,
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