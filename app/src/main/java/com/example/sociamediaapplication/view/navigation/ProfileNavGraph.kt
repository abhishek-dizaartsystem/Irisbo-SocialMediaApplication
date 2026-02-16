package com.example.sociamediaapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.repository.PostRepository
import com.example.sociamediaapplication.data.repository.ProfileRepository
import com.example.sociamediaapplication.view.screens.EditProfileScreen
import com.example.sociamediaapplication.view.screens.MenuScreen
import com.example.sociamediaapplication.view.screens.ProfileScreen
import com.example.sociamediaapplication.viewmodel.AuthViewModel
import com.example.sociamediaapplication.viewmodel.PageViewModel
import com.example.sociamediaapplication.viewmodel.PostViewModel
import com.example.sociamediaapplication.viewmodel.ProfileViewModel
import com.example.sociamediaapplication.viewmodel.factory.PostViewModelFactory
import com.example.sociamediaapplication.viewmodel.factory.ProfileViewModelFactory

@Composable
fun ProfileNavGraph(
    authViewModel: AuthViewModel,
    mainNavController: NavController
){
    val navController = rememberNavController()

    val context = LocalContext.current.applicationContext
    val tokenManager = remember { TokenManager(context) }
    val repository = remember { ProfileRepository(tokenManager) }
    val factory = remember { ProfileViewModelFactory(repository) }

    val viewModel: ProfileViewModel = viewModel(factory = factory)

    val postRepository = remember { PostRepository(tokenManager) }
    val postFactory = remember { PostViewModelFactory(postRepository) }
    val postViewModel: PostViewModel = viewModel(factory = postFactory)

    val posts by postViewModel.posts.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "profileMain"
    ){
        composable("profileMain"){
            ProfileScreen(
                viewModel = viewModel,
                posts = posts,
                onEditStatus = {
                    mainNavController.navigate(Routes.EditStatus.route)
                },
                onEditProfile = {
                    navController.navigate(ProfileRoutes.EditProfile.route)
                },
                onMenu = {
                    navController.navigate(ProfileRoutes.Menu.route)
                }
            )
        }
        composable(ProfileRoutes.EditProfile.route){
            EditProfileScreen(
                navController = navController
            )
        }
        composable(ProfileRoutes.Menu.route) {
            MenuNavGraph(
                mainNavController = mainNavController,
                authViewModel = authViewModel
            )
        }
    }
}