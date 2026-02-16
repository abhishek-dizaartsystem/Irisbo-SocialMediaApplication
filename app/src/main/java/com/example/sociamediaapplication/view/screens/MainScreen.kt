package com.example.sociamediaapplication.view.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.repository.PostRepository
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.LLBlue
import com.example.sociamediaapplication.view.components.HexagonShape
import com.example.sociamediaapplication.view.navigation.ChatsNavGraph
import com.example.sociamediaapplication.view.navigation.MainRoutes
import com.example.sociamediaapplication.view.navigation.MenuNavGraph
import com.example.sociamediaapplication.view.navigation.ProfileNavGraph
import com.example.sociamediaapplication.view.navigation.Routes
import com.example.sociamediaapplication.viewmodel.AuthViewModel
import com.example.sociamediaapplication.viewmodel.UploadViewModel
import com.example.sociamediaapplication.viewmodel.factory.UploadViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainNavController: NavController,
    authViewModel: AuthViewModel = viewModel()
){




    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val isScrollingUp = scrollBehavior.state.collapsedFraction == 0f


    val context = LocalContext.current.applicationContext
    val tokenManager = remember { TokenManager(context) }
    val repository = remember { PostRepository(tokenManager) }
    val factory = remember { UploadViewModelFactory(repository) }

    val uploadViewModel: UploadViewModel = viewModel(factory = factory)

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
                                navController.navigate(MainRoutes.Profile.route)
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
                            Image(
                                painter = painterResource(id = R.drawable.rectangle_5),
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
                                mainNavController.navigate(Routes.Reels.route)
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
                HomeScreen1()
            }

            composable(MainRoutes.Search.route) {
                SearchScreen()
            }

            composable(MainRoutes.Reels.route) {
                ReelsScreen()
            }

            composable(MainRoutes.Add.route){
                UploadScreen(
                    navController = navController,
                    viewModel = uploadViewModel
                )
            }

            composable(MainRoutes.Chats.route) {
                ChatsNavGraph()
            }

            composable(MainRoutes.Profile.route) {
                HomeScreen2(mainNavController)
            }

            composable(MainRoutes.Category.route){
                CategoryScreen()
            }

            composable(MainRoutes.Menu.route){
                MenuNavGraph(
                    mainNavController,
                    authViewModel = authViewModel)
            }

            composable(MainRoutes.Notifications.route){
                NotificationsScreen()
            }

            composable(MainRoutes.Home2.route){
                HomeScreen2()
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
