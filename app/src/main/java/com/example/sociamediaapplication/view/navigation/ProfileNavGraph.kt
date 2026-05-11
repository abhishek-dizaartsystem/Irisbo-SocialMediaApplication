package com.example.sociamediaapplication.view.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.view.screens.EditProfileScreen
import com.example.sociamediaapplication.view.screens.ProfileScreen
import com.example.sociamediaapplication.viewmodel.AuthViewModel
import com.example.sociamediaapplication.viewmodel.ChatViewModel
import com.example.sociamediaapplication.viewmodel.FriendViewModel
import com.example.sociamediaapplication.viewmodel.PostViewModel
import com.example.sociamediaapplication.viewmodel.ProfileViewModel
import com.example.sociamediaapplication.viewmodel.ReelsViewModel
import com.example.sociamediaapplication.viewmodel.UploadViewModel
import com.example.sociamediaapplication.viewmodel.VideoViewModel

@Composable
fun ProfileNavGraph(
    authViewModel: AuthViewModel,
    mainNavController: NavController,
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

    val context = LocalContext.current.applicationContext
    val tokenManager = remember { TokenManager(context) }

    val posts by postViewModel.posts.collectAsState()


    val myReels by reelViewModel.myReels.collectAsState()

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
                reelViewModel.loadMyReels()
            }

            ProfileScreen(
                viewModel = profileViewModel,
                posts = posts,
                onEditProfile = {
                    navController.navigate(ProfileRoutes.EditProfile.route)
                },
                onMenu = {
                    navController.navigate(ProfileRoutes.Menu.route)
                },
                onEditStatus = {
                    mainNavController.navigate(Routes.EditStatus.route)
                },
                onReelLike = {
                    reelViewModel.toggleLikeMyReels(it)
                },
                onReelSave = {
                    reelViewModel.toggleSaveMyReels(it)
                },
                onPostLike = {
                    postViewModel.toggleLike(it, profile?.data?.id ?: 0)
                },
                onPostSave = {
                    postViewModel.toggleSave(it, profile?.data?.id ?: 0)
                },
                myReels = myReels
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
                uploadViewModel = uploadViewModel,
                postViewModel = postViewModel,
                friendViewModel = friendViewModel,
                reelViewModel = reelViewModel,
                mainNavController2 = mainNavController2,
                chatViewModel = chatViewModel,
                videoViewModel = videoViewModel
            )
        }
    }
}