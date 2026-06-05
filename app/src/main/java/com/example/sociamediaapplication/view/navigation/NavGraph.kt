package com.example.sociamediaapplication.view.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.data.preferences.SocketManager
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.repository.AnalyticsRepository
import com.example.sociamediaapplication.data.repository.AuthRepository
import com.example.sociamediaapplication.data.repository.ChatRepository
import com.example.sociamediaapplication.data.repository.GroupRepository
import com.example.sociamediaapplication.data.repository.NotificationRepository
import com.example.sociamediaapplication.data.repository.ReelRepository
import com.example.sociamediaapplication.data.repository.StoryRepository
import com.example.sociamediaapplication.data.repository.VideoRepository
import com.example.sociamediaapplication.data.utils.getDeviceId
import com.example.sociamediaapplication.view.screens.AuthScreen
import com.example.sociamediaapplication.view.screens.MainScreen
import com.example.sociamediaapplication.view.screens.SplashScreen
import com.example.sociamediaapplication.view.screens.StatusEditorScreen
import com.example.sociamediaapplication.viewmodel.AnalyticsViewModel
import com.example.sociamediaapplication.viewmodel.AuthUiState
import com.example.sociamediaapplication.viewmodel.AuthViewModel
import com.example.sociamediaapplication.viewmodel.ChatViewModel
import com.example.sociamediaapplication.viewmodel.GroupViewModel
import com.example.sociamediaapplication.viewmodel.NotificationViewModel
import com.example.sociamediaapplication.viewmodel.ReelsViewModel
import com.example.sociamediaapplication.viewmodel.StoryViewModel
import com.example.sociamediaapplication.viewmodel.VideoViewModel
import com.example.sociamediaapplication.viewmodel.factory.AnalyticsViewModelFactory
import com.example.sociamediaapplication.viewmodel.factory.AuthViewModelFactory
import com.example.sociamediaapplication.viewmodel.factory.ChatViewModelFactory
import com.example.sociamediaapplication.viewmodel.factory.GroupViewModelFactory
import com.example.sociamediaapplication.viewmodel.factory.NotificationViewModelFactory
import com.example.sociamediaapplication.viewmodel.factory.ReelsViewModelFactory
import com.example.sociamediaapplication.viewmodel.factory.StoryViewModelFactory
import com.example.sociamediaapplication.viewmodel.factory.VideoViewModelFactory
import com.google.firebase.messaging.FirebaseMessaging

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()

    val context = LocalContext.current.applicationContext

    val tokenManager = remember { TokenManager(context) }
    val repository = remember { AuthRepository(tokenManager) }
    val factory = remember { AuthViewModelFactory(repository) }

    val authViewModel: AuthViewModel = viewModel(factory = factory)
    val authState by authViewModel.authState.collectAsState()

    val reelRepository = remember { ReelRepository(tokenManager) }
    val reelFactory = remember { ReelsViewModelFactory(reelRepository) }
    val reelViewModel: ReelsViewModel = viewModel(factory = reelFactory)

    val groupRepository = remember { GroupRepository(tokenManager) }
    val groupFactory = remember { GroupViewModelFactory(groupRepository) }
    val groupViewModel: GroupViewModel = viewModel(factory = groupFactory)


    val storyRepository = remember { StoryRepository(tokenManager) }
    val storyViewModelFactory = remember { StoryViewModelFactory(storyRepository) }
    val storyViewModel: StoryViewModel = viewModel(factory = storyViewModelFactory)

    val chatRepository = remember { ChatRepository(tokenManager) }
    val chatViewModelFactory = remember { ChatViewModelFactory(chatRepository) }
    val chatViewModel: ChatViewModel = viewModel(factory = chatViewModelFactory)

    val videoRepository = remember { VideoRepository(tokenManager, context) }
    val videoViewModelFactory = remember { VideoViewModelFactory(videoRepository) }
    val videoViewModel: VideoViewModel = viewModel(factory = videoViewModelFactory)

    val notificationRepository = remember { NotificationRepository(tokenManager) }
    val notificationViewModelFactory = remember { NotificationViewModelFactory(notificationRepository) }
    val notificationViewModel: NotificationViewModel = viewModel(factory = notificationViewModelFactory)

    val analyticsRepository = remember { AnalyticsRepository(tokenManager) }
    val analyticsViewModelFactory = remember { AnalyticsViewModelFactory(analyticsRepository) }
    val analyticsViewModel: AnalyticsViewModel = viewModel(factory = analyticsViewModelFactory)


    val reels by reelViewModel.reels.collectAsState()
    val loading by reelViewModel.loading.collectAsState()

    val token = remember { tokenManager.getToken() }

    LaunchedEffect(authState) {
        if (authState is AuthUiState.LoggedOut) {
            navController.navigate(Routes.Auth.route)
            authViewModel.resetState()
        }
    }

    LaunchedEffect(Unit) {

        if (repository.isLoggedIn()) {

            val token = tokenManager.getToken()

            if (!token.isNullOrEmpty()) {

                SocketManager.connect(token)

                chatViewModel.observePresence()

                // 🔥 GET FCM TOKEN
                FirebaseMessaging.getInstance().token
                    .addOnSuccessListener { fcmToken ->

                        Log.d("FCM_TOKEN", fcmToken)

                        val deviceId =
                            getDeviceId(context)

                        notificationViewModel.saveFcmToken(deviceId, fcmToken)

                    }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Routes.Splash.route
    ) {

        composable(Routes.Splash.route) {
            SplashScreen(
                isLoggedIn = repository.isLoggedIn(),
                onNavigateToAuth = {
                    navController.navigate(Routes.Auth.route) {
                        popUpTo(Routes.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToMain = {
                    navController.navigate(Routes.Main.route) {
                        popUpTo(Routes.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.Auth.route) {

            AuthScreen(
                authState = authState,
                onLogin = {email, password->
                    authViewModel.login(email, password)
                },
                onSignup = {name, username, email, password->
                    authViewModel.signup(name, username, email, password)
                },
                onAuthSuccess = {
                    navController.navigate(Routes.Splash.route) {
                        popUpTo(Routes.Auth.route) { inclusive = true }
                    }
                },
                onResetState = {
                    authViewModel.resetState()
                }
            )
        }

        composable(Routes.Main.route) {


            MainScreen(
                mainNavController = navController,
                authViewModel = authViewModel,   // ✅ pass it,
                groupViewModel = groupViewModel,
                storyViewModel = storyViewModel,
                chatViewModel = chatViewModel,
                videoViewModel = videoViewModel,
                notificationViewModel = notificationViewModel,
                analyticsViewModel = analyticsViewModel
            )
        }

        composable(Routes.EditStatus.route) {
            StatusEditorScreen(
                storyViewModel = storyViewModel,
                onUploadSuccess = {
                    navController.popBackStack()
                }
            )
        }

//        composable(Routes.Reels.route) {
//            LaunchedEffect(Unit) {
//                reelViewModel.loadReels()
//            }
//            ReelsScreen(
//                loading = loading,
//                reels = reels,
//                onLike = {
//                    reelViewModel.toggleLike(it)
//                },
//                onSave = {
//                    reelViewModel.toggleSave(it)
//                }
//            )

//            ComingSoonScreen()
//        }

    }
}
