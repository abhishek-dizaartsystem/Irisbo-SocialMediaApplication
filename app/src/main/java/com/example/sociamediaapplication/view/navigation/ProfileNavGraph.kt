package com.example.sociamediaapplication.view.navigation

import android.util.Log
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
import com.example.sociamediaapplication.data.repository.ReelRepository
import com.example.sociamediaapplication.view.screens.EditProfileScreen
import com.example.sociamediaapplication.view.screens.ProfileScreen
import com.example.sociamediaapplication.viewmodel.AuthViewModel
import com.example.sociamediaapplication.viewmodel.PostViewModel
import com.example.sociamediaapplication.viewmodel.ProfileViewModel
import com.example.sociamediaapplication.viewmodel.ReelsViewModel
import com.example.sociamediaapplication.viewmodel.UploadViewModel
import com.example.sociamediaapplication.viewmodel.factory.PostViewModelFactory
import com.example.sociamediaapplication.viewmodel.factory.ReelsViewModelFactory

@Composable
fun ProfileNavGraph(
    authViewModel: AuthViewModel,
    mainNavController: NavController,
    profileViewModel: ProfileViewModel,
    uploadViewModel: UploadViewModel
){
    val navController = rememberNavController()

    val context = LocalContext.current.applicationContext
    val tokenManager = remember { TokenManager(context) }

    val postRepository = remember { PostRepository(tokenManager) }
    val postFactory = remember { PostViewModelFactory(postRepository) }
    val postViewModel: PostViewModel = viewModel(factory = postFactory)

    val posts by postViewModel.posts.collectAsState()

    val reelRepository = remember { ReelRepository(tokenManager) }
    val reelFactory = remember { ReelsViewModelFactory(reelRepository) }
    val reelViewModel: ReelsViewModel = viewModel(factory = reelFactory)

    val reels by reelViewModel.reels.collectAsState()

    val profile by profileViewModel.profile.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "profileMain"
    ){
        composable("profileMain"){
            LaunchedEffect(Unit) {
                Log.d("POST_DEBUG", "loadPosts() called")
                postViewModel.loadPosts(
                    id = profile?.data?.id ?: 0
                )
//                reelViewModel.loadReels()
            }

            ProfileScreen(
                viewModel = profileViewModel,
                posts = posts,
                reels = reels,
                onReelLike = {
                    reelViewModel.toggleLike(it)
                },
                onEditStatus = {
                    mainNavController.navigate(Routes.EditStatus.route)
                },
                onEditProfile = {
                    navController.navigate(ProfileRoutes.EditProfile.route)
                },
                onMenu = {
                    navController.navigate(ProfileRoutes.Menu.route)
                },
                onReelSave = {
                    reelViewModel.toggleSave(it)
                },
                onPostLike = {
                    postViewModel.toggleLike(it, profile?.data?.id ?: 0)
                },
                onPostSave = {
                    postViewModel.toggleSave(it, profile?.data?.id ?: 0)
                }
            )
        }
        composable(ProfileRoutes.EditProfile.route){
            EditProfileScreen(
                navController = navController,
                onSave = { request ->

                    profileViewModel.editProfile(
                        request = request,
                        context = context
                    ){
                        navController.popBackStack()   // go back after save
                    }
                },
                profileViewModel = profileViewModel
            )
        }
        composable(ProfileRoutes.Menu.route) {
            MenuNavGraph(
                mainNavController = mainNavController,
                authViewModel = authViewModel,
                profileViewModel = profileViewModel,
                uploadViewModel = uploadViewModel
            )
        }
    }
}