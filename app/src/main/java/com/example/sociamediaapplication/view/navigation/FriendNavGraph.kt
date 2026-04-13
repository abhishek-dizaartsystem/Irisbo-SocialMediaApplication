package com.example.sociamediaapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.repository.FriendRepository
import com.example.sociamediaapplication.view.screens.FriendsScreen
import com.example.sociamediaapplication.viewmodel.FriendViewModel
import com.example.sociamediaapplication.viewmodel.factory.FriendsViewModelFactory

@Composable
fun FriendNavGraph(bNavController: NavHostController) {
    val navController = rememberNavController()

    val context = LocalContext.current.applicationContext

    val tokenManager = remember { TokenManager(context) }

    val friendRepository = remember { FriendRepository(tokenManager) }
    val friendViewModelFactory = remember { FriendsViewModelFactory(friendRepository) }
    val friendViewModel: FriendViewModel = viewModel(factory = friendViewModelFactory)

    NavHost(
        navController = navController,
        startDestination = FriendRoutes.Friends.route
    ) {
        composable(FriendRoutes.Friends.route) {

            LaunchedEffect(Unit) {
                friendViewModel.loadSuggestedUsers()
            }

            FriendsScreen(
                navController = bNavController,
                viewModel = friendViewModel
            )
        }
    }
}