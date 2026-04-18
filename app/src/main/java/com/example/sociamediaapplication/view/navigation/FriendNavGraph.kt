package com.example.sociamediaapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.view.screens.FriendsScreen
import com.example.sociamediaapplication.viewmodel.FriendViewModel

@Composable
fun FriendNavGraph(
    bNavController: NavHostController,
    friendViewModel: FriendViewModel,
    mainNavController2: NavController
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
                navController = bNavController,
                viewModel = friendViewModel,
                onOtherProfileClick = {userId->
                    mainNavController2.navigate(MainRoutes.OtherProfile.createRoute(userId))
                }
            )
        }
    }
}