package com.example.sociamediaapplication.view.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.preferences.NavigationManager
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.data.repository.FriendRepository
import com.example.sociamediaapplication.data.repository.PostRepository
import com.example.sociamediaapplication.data.repository.ProfileRepository
import com.example.sociamediaapplication.data.repository.ReelRepository
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.LLBlue
import com.example.sociamediaapplication.view.components.HexagonShape
import com.example.sociamediaapplication.view.navigation.AppDestination
import com.example.sociamediaapplication.view.navigation.ChatsNavGraph
import com.example.sociamediaapplication.view.navigation.MainRoutes
import com.example.sociamediaapplication.view.navigation.MenuNavGraph
import com.example.sociamediaapplication.view.navigation.VideoNavGraph
import com.example.sociamediaapplication.viewmodel.AnalyticsViewModel
import com.example.sociamediaapplication.viewmodel.AuthViewModel
import com.example.sociamediaapplication.viewmodel.ChatViewModel
import com.example.sociamediaapplication.viewmodel.FriendViewModel
import com.example.sociamediaapplication.viewmodel.GroupViewModel
import com.example.sociamediaapplication.viewmodel.MonetizationViewModel
import com.example.sociamediaapplication.viewmodel.NotificationViewModel
import com.example.sociamediaapplication.viewmodel.PostViewModel
import com.example.sociamediaapplication.viewmodel.ProfileViewModel
import com.example.sociamediaapplication.viewmodel.ReelsViewModel
import com.example.sociamediaapplication.viewmodel.StoryViewModel
import com.example.sociamediaapplication.viewmodel.UploadViewModel
import com.example.sociamediaapplication.viewmodel.VideoViewModel
import com.example.sociamediaapplication.viewmodel.factory.FriendsViewModelFactory
import com.example.sociamediaapplication.viewmodel.factory.PostViewModelFactory
import com.example.sociamediaapplication.viewmodel.factory.ProfileViewModelFactory
import com.example.sociamediaapplication.viewmodel.factory.ReelsViewModelFactory
import com.example.sociamediaapplication.viewmodel.factory.UploadViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainNavController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    groupViewModel: GroupViewModel = viewModel(),
    storyViewModel: StoryViewModel = viewModel(),
    chatViewModel: ChatViewModel = viewModel(),
    videoViewModel: VideoViewModel = viewModel(),
    notificationViewModel: NotificationViewModel = viewModel(),
    analyticsViewModel: AnalyticsViewModel = viewModel(),
    monetizationViewModel: MonetizationViewModel = viewModel()
){

    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val isScrollingUp = scrollBehavior.state.collapsedFraction == 0f


    val context = LocalContext.current.applicationContext
    val tokenManager = remember { TokenManager(context) }
    val repository = remember { PostRepository(tokenManager) }

    val reelRepository = remember { ReelRepository(tokenManager) }
    val reelFactory = remember { ReelsViewModelFactory(reelRepository) }
    val reelViewModel: ReelsViewModel = viewModel(factory = reelFactory)

    val factory = remember { UploadViewModelFactory(repository, reelRepository) }

    val uploadViewModel: UploadViewModel = viewModel(factory = factory)

    val profileRepository = remember { ProfileRepository(tokenManager) }
    val profileFactory = remember { ProfileViewModelFactory(profileRepository) }
    val profileViewModel: ProfileViewModel = viewModel(factory = profileFactory)

    val postRepository = remember { PostRepository(tokenManager) }
    val postFactory = remember { PostViewModelFactory(postRepository) }
    val postViewModel: PostViewModel = viewModel(factory = postFactory)

    val friendRepository = remember { FriendRepository(tokenManager) }
    val friendViewModelFactory = remember { FriendsViewModelFactory(friendRepository) }
    val friendViewModel: FriendViewModel = viewModel(factory = friendViewModelFactory)

//    val chatRepository = remember { ChatRepository(tokenManager) }
//    val chatViewModelFactory = remember { ChatViewModelFactory(chatRepository) }
//    val chatViewModel: ChatViewModel = viewModel(factory = chatViewModelFactory)

    val profile by profileViewModel.profile.collectAsState()

    val isFullScreen by videoViewModel.isFullscreen.collectAsState()

    LaunchedEffect(NavigationManager.pendingDestination) {
        val destination = NavigationManager.pendingDestination ?: return@LaunchedEffect

        when(destination) {

            is AppDestination.Reel -> {

                navController.navigate(
                    MainRoutes.Reels.createRoute(destination.reelId)
                )
                NavigationManager.pendingDestination = null
            }

            is AppDestination.Video -> {

                navController.navigate(
                    MainRoutes.Category.createRoute(destination.videoId)
                )
                NavigationManager.pendingDestination = null
            }

            is AppDestination.Profile -> {

                navController.navigate(
                    MainRoutes.OtherProfile
                        .createRoute(destination.userId)
                )

                NavigationManager.pendingDestination =
                    null
            }

            is AppDestination.Post -> {

                navController.navigate(
                    MainRoutes.Home1.createRoute(destination.postId)
                )
                NavigationManager.pendingDestination = null
            }

            is AppDestination.Event -> {

                navController.navigate(
                    MainRoutes.Menu.createRoute(destination.eventId)
                )
                NavigationManager.pendingDestination = null
            }

            else -> {}
        }
    }

    LaunchedEffect(Unit) {
        profileViewModel.loadProfile()

        chatViewModel.observePresence()
    }


    Scaffold(
        topBar = {
            if(!isFullScreen){
                TopAppBar(
                    scrollBehavior = scrollBehavior,
                    title = {
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    navController.navigate(MainRoutes.Menu.route)
                                },
                                modifier = Modifier
                                    .size(40.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.menu_svgrepo_com),
                                    contentDescription = "",
                                    modifier = Modifier.size(36.dp)
                                )
                            }

                            Text(
                                text = "@Irisbo",
                                color = Blue,
                                fontSize = 28.sp
                            )


                            Row(
                                modifier = Modifier.width(130.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = {
                                        navController.navigate(MainRoutes.Add.route)
                                    },
                                    modifier = Modifier.size(40.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.add_square_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier.height(36.dp)
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        navController.navigate(MainRoutes.Search.route)
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.search_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier.height(32.dp)
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        navController.navigate(MainRoutes.Notifications.route)
                                    },
                                    modifier = Modifier.size(48.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.notification_13_svgrepo_com),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(40.dp)
                                    )
                                }
                            }



                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = LLBlue),
                    modifier = Modifier
                )
            }

        },
        bottomBar = {
            if(!isFullScreen){
                AnimatedVisibility(visible = isScrollingUp) {
                    BottomAppBar(
                        containerColor = LLBlue,
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            IconButton(
                                onClick = {
                                    navController.navigate(MainRoutes.Home2.route)
                                },
                                modifier = Modifier
                                    .border(
                                        width = 1.dp,
                                        color = Black,
                                        shape = HexagonShape
                                    )
                                    .size(40.dp)
                                // Set the size of the clickable area
                            ) {
                                AsyncImage(
                                    model = if(profile?.data?.profile_image == null) R.drawable.profile_image_placeholder else "${RetrofitClient.BASE_URL}${profile?.data?.profile_image?.removePrefix("/")}",
                                    contentDescription = "Profile Image",
                                    // This crops the image into a square before clipping to a circle

                                    modifier = Modifier
                                        .size(50.dp) // Ensure the image fills the button
                                        .clip(HexagonShape), // Makes it perfectly circular
                                    contentScale = ContentScale.Crop,
                                )
                            }
                            IconButton(
                                onClick = {
                                    navController.navigate(MainRoutes.Category.route)
                                },
                                modifier = Modifier
                                    .clip(RoundedCornerShape(0.dp))
                                    .size(40.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.video_frame_play_horizontal_svgrepo_com),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .size(40.dp)
                                )
                            }
                            IconButton(
                                onClick = {
                                    navController.navigate(MainRoutes.Home1.route)
                                },
                                modifier = Modifier
                                    .clip(RoundedCornerShape(0.dp))
                                    .size(40.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.home_svgrepo_com),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .size(40.dp)
                                )
                            }
                            IconButton(
                                onClick = {
//                                mainNavController.navigate(Routes.Reels.route)
                                    navController.navigate(MainRoutes.Reels.route)
                                },
                                modifier = Modifier
                                    .clip(RoundedCornerShape(0.dp))
                                    .size(50.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.video_plus_svgrepo_com),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .size(70.dp)
                                )
                            }
                            IconButton(
                                onClick = {
                                    navController.navigate(MainRoutes.Chats.route)
                                },
                                modifier = Modifier
                                    .clip(RoundedCornerShape(0.dp))
                                    .size(40.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.chat_dots_svgrepo_com),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .size(40.dp)
                                )
                            }
                        }
                    }
                }
            }


        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        NavHost(
        navController = navController,
        startDestination = MainRoutes.Home1.route,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(
                color = BackgroundColor
            )
        ) {

            composable(
                route = MainRoutes.Home1.routePattern,
                arguments = listOf(
                    navArgument("postId") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )
            ) { backStackEntry ->
                val postId = backStackEntry.arguments?.getInt("postId") ?: -1
                val profile by profileViewModel.profile.collectAsState()
                HomeScreen1(
                    postViewModel = postViewModel,
                    targetPostId = postId,
                    onOtherProfileClick = {userId->
                        if(profile?.data?.id == userId){
                            navController.navigate("menu")
                        }else{
                            navController.navigate(
                                MainRoutes.OtherProfile.createRoute(userId)
                            )
                        }

                    }
                )
            }

            composable(MainRoutes.Search.route) {
                SearchScreen()
            }

            composable(
                route = MainRoutes.Reels.routePattern,
                arguments = listOf(
                    navArgument("reelId") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )
            ) { backStackEntry ->
                val targetReelId = backStackEntry.arguments?.getInt("reelId") ?: -1

                LaunchedEffect(Unit) {
                    reelViewModel.loadReels()
                }

                val reels by reelViewModel.reels.collectAsState()

                val startIndex = remember(reels, targetReelId) {
                    if (targetReelId != -1) {
                        val index = reels.indexOfFirst { it.id == targetReelId }
                        if (index != -1) index else 0
                    } else {
                        0
                    }
                }

                ReelsScreen(
                    reels = reels,
                    startIndex = startIndex,
                    onLike = {
                        reelViewModel.toggleLike(it)
                    },
                    onSave = {
                        reelViewModel.toggleSave(it)
                    }
                )
            }

            composable(MainRoutes.Add.route){
                UploadScreen(
                    viewModel = uploadViewModel,
                    navController = navController,
                )
            }

            composable(MainRoutes.Chats.route) {
                ChatsNavGraph(
                    chatViewModel = chatViewModel,
                    profileViewModel = profileViewModel
                )
            }

            composable(
                route = MainRoutes.OtherProfile.route,
                arguments = listOf(
                    navArgument("userId"){type = NavType.IntType}
                )
            ) {backStackEntry->

                val userId = backStackEntry.arguments?.getInt("userId")

                LaunchedEffect(userId) {
                    Log.d("UID", "uid = $userId")
                    friendViewModel.getFriendshipStatus(userId?:0)
                    postViewModel.loadOthersPosts(userId?:0)
                    profileViewModel.loadPublicProfile(userId?:0)
                    reelViewModel.loadUserReels(userId?:0)
                }

                val posts by postViewModel.otherProfilePosts.collectAsState()
                val reels by reelViewModel.otherProfileReels.collectAsState()

                OtherProfileScreen(
                    onChatClick = {
                        navController.navigate(MainRoutes.Chats.route)
                    },
                    friendViewModel = friendViewModel,
                    profileViewModel = profileViewModel,
                    posts = posts,
                    reels = reels,
                    onReelLike = {
                        reelViewModel.toggleLikeUserReels(it)
                    },
                    onReelSave = {
                        reelViewModel.toggleSaveUserReels(it)
                    },
                    onPostLike = {
                        postViewModel.toggleOtherProfilePostLike(it, profile?.data?.id ?: 0)
                    },
                    onPostSave = {
                        postViewModel.toggleOtherProfileSave(it, profile?.data?.id ?: 0)
                    },
                    userId = userId?:0
                )
            }

            composable(
                route = MainRoutes.Category.routePattern,
                arguments = listOf(
                    navArgument("videoId") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )
            ) { backStackEntry ->
                val targetVideoId = backStackEntry.arguments?.getInt("videoId") ?: -1

                LaunchedEffect(Unit) {
                    videoViewModel.fetchVideoCategories()
                }

                VideoNavGraph(
                    videoViewModel = videoViewModel,
                    initialVideoId = if (targetVideoId != -1) targetVideoId else null
                )
            }

            composable(
                route = MainRoutes.Menu.routePattern,
                arguments = listOf(
                    navArgument("eventId") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )
            ) { backStackEntry ->
                val targetEventId = backStackEntry.arguments?.getInt("eventId") ?: -1

                MenuNavGraph(
                    mainNavController,
                    mainNavController2 = navController,
                    authViewModel = authViewModel,
                    profileViewModel = profileViewModel,
                    uploadViewModel = uploadViewModel,
                    postViewModel = postViewModel,
                    friendViewModel = friendViewModel,
                    reelViewModel = reelViewModel,
                    chatViewModel = chatViewModel,
                    videoViewModel = videoViewModel,
                    notificationViewModel = notificationViewModel,
                    analyticsViewModel = analyticsViewModel,
                    monetizationViewModel = monetizationViewModel,
                    initialEventId = if (targetEventId != -1) targetEventId else null
                )
            }

            composable(MainRoutes.Notifications.route){
                NotificationsScreen()
            }

            composable(MainRoutes.Home2.route){

                LaunchedEffect(Unit) {
                    storyViewModel.getFriendsStories()
                }

                HomeScreen2(
                    mainNavController,
                    postViewModel = postViewModel,
                    onOtherProfileClick = {userId->
                        if(profile?.data?.id == userId){
                            navController.navigate(MainRoutes.Menu.route)
                        }else{
                            navController.navigate(
                                MainRoutes.OtherProfile.createRoute(userId)
                            )
                        }
                    },
                    storyViewModel = storyViewModel,
                    onStoryClick = {userId->
                        navController.navigate(
                            MainRoutes.StoryView.createRoute(userId)
                        )
                    },
                    onUserStoryClick = {
                        navController.navigate(MainRoutes.StoryViewUser.route)
                    },
                    profileViewModel = profileViewModel
                )
            }

            composable(
                route = MainRoutes.StoryView.route,
                arguments = listOf(
                    navArgument("userId"){type = NavType.IntType}
                )
            ) {backStackEntry ->
                val userId = backStackEntry.arguments?.getInt("userId")

                LaunchedEffect(userId) {
                    storyViewModel.getSingleUserStories(userId?:0)
                }

                ViewStoryScreen(
                    storyViewModel = storyViewModel,
                    onFinished = {
                        Log.d("MainScreen", "finished video")
                        navController.popBackStack()
                    },
                    profileViewModel = profileViewModel,
                    navController = navController
                )
            }
            composable(
                route = MainRoutes.StoryViewUser.route,
            ) {

                LaunchedEffect(Unit) {
                    storyViewModel.getMyStories()
                }

                ViewStoryScreen(
                    storyViewModel = storyViewModel,
                    isUserStory = true,
                    onFinished = {
                        Log.d("MainScreen", "finished video")
                        navController.popBackStack()
                    },
                    profileViewModel = profileViewModel,
                    navController = navController
                )
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview(){
    MainScreen(
        mainNavController = rememberNavController(),
    )
}
