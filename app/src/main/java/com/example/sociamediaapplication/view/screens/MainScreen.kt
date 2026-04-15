package com.example.sociamediaapplication.view.screens

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
import com.example.sociamediaapplication.view.navigation.ChatsNavGraph
import com.example.sociamediaapplication.view.navigation.MainRoutes
import com.example.sociamediaapplication.view.navigation.MenuNavGraph
import com.example.sociamediaapplication.viewmodel.AuthViewModel
import com.example.sociamediaapplication.viewmodel.FriendViewModel
import com.example.sociamediaapplication.viewmodel.GroupViewModel
import com.example.sociamediaapplication.viewmodel.PostViewModel
import com.example.sociamediaapplication.viewmodel.ProfileViewModel
import com.example.sociamediaapplication.viewmodel.ReelsViewModel
import com.example.sociamediaapplication.viewmodel.UploadViewModel
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
    groupViewModel: GroupViewModel = viewModel()
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

    val profile by profileViewModel.profile.collectAsState()

    LaunchedEffect(Unit) {
        profileViewModel.loadProfile()
    }


    Scaffold(
        topBar = {
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
        },
        bottomBar = {
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

            composable(MainRoutes.Home1.route) {
                HomeScreen1(
                    postViewModel = postViewModel
                )
            }

            composable(MainRoutes.Search.route) {
                SearchScreen()
            }

            composable(MainRoutes.Reels.route) {
//                ReelsScreen()
                ComingSoonScreen()
            }

            composable(MainRoutes.Add.route){
                UploadScreen(
                    viewModel = uploadViewModel,
                    navController = navController,
                )
            }

            composable(MainRoutes.Chats.route) {
                ChatsNavGraph()
            }

            composable(
                route = MainRoutes.PublicProfile.route,
                arguments = listOf(
                    navArgument("userId"){type = NavType.IntType}
                )
            ) {backStackEntry->

                val userId = backStackEntry.arguments?.getInt("userId")

                LaunchedEffect(Unit) {
                    friendViewModel.getFriendshipStatus(userId?:0)
                    postViewModel.loadPosts(userId?:0)
                }

                val posts by postViewModel.posts.collectAsState()
                val reels by reelViewModel.reels.collectAsState()

                ProfileScreen(
                    isUser = false,
                    viewModel = profileViewModel,
                    onChatClick = {
                        navController.navigate(MainRoutes.Chats.route)
                    },
                    friendViewModel = friendViewModel,
                    posts = posts
                )
            }

            composable(MainRoutes.Category.route){
                CategoryScreen()
            }

            composable(MainRoutes.Menu.route){
                MenuNavGraph(
                    mainNavController,
                    authViewModel = authViewModel,
                    profileViewModel = profileViewModel,
                    uploadViewModel = uploadViewModel,
                    postViewModel = postViewModel,
                    friendViewModel = friendViewModel,
                    reelViewModel = reelViewModel
                )
            }

            composable(MainRoutes.Notifications.route){
                NotificationsScreen()
            }

            composable(MainRoutes.Home2.route){
                HomeScreen2(
                    mainNavController,
                    postViewModel = postViewModel
                )
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview(){
    MainScreen(
        mainNavController = rememberNavController()
    )
}
