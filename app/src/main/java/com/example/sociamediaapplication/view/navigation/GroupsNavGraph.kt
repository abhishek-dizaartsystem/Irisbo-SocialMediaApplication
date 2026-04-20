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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.repository.GroupRepository
import com.example.sociamediaapplication.view.screens.CreateGroupScreen
import com.example.sociamediaapplication.view.screens.EditGroupScreen
import com.example.sociamediaapplication.view.screens.GroupDetailsScreen
import com.example.sociamediaapplication.view.screens.GroupsScreen
import com.example.sociamediaapplication.view.screens.UploadScreen
import com.example.sociamediaapplication.viewmodel.GroupViewModel
import com.example.sociamediaapplication.viewmodel.PostViewModel
import com.example.sociamediaapplication.viewmodel.ProfileViewModel
import com.example.sociamediaapplication.viewmodel.UploadViewModel
import com.example.sociamediaapplication.viewmodel.factory.GroupViewModelFactory

@Composable
fun GroupsNavGraph(
    bNavController: NavController = rememberNavController(),
    uploadViewModel: UploadViewModel,
    postViewModel: PostViewModel,
    mainNavController2: NavController,
    profileViewModel: ProfileViewModel
){
    val navController = rememberNavController()

    val context = LocalContext.current.applicationContext
    val tokenManager = remember { TokenManager(context) }

    val groupRepository = remember { GroupRepository(tokenManager) }
    val groupViewModelFactory = remember { GroupViewModelFactory(groupRepository) }
    val groupViewModel: GroupViewModel = viewModel(factory = groupViewModelFactory)


    NavHost(
        navController = navController,
        startDestination = GroupsRoutes.Groups.route
    ){
        composable(GroupsRoutes.Groups.route) {
            LaunchedEffect(Unit) {
                groupViewModel.loadGroups()
                groupViewModel.loadMyGroups()
            }
            GroupsScreen(
                bNavController,
                navController,
                onGroupClick = {groupId, isCreator->
                    navController.navigate(
                        GroupsRoutes.Group.createRoute(groupId, isCreator)
                    )
                },
//                onEditClick = {groupId->
//                    navController.navigate(
//                        GroupsRoutes.EditGroup.createRoute(groupId)
//                    )
//                },
                viewModel = groupViewModel
            )
        }
        composable(
            route = GroupsRoutes.Group.route,
            arguments = listOf(
                navArgument("groupId"){ type = NavType.IntType},
                navArgument("isCreator"){ type = NavType.BoolType}
            )
        ) {backStackEntry->

            val groupId = backStackEntry.arguments?.getInt("groupId")
            val isCreator = backStackEntry.arguments?.getBoolean("isCreator")

            val profile by profileViewModel.profile.collectAsState()

            LaunchedEffect(groupId){
                groupId?.let {
                    groupViewModel.loadGroupDetails(it)
                    groupViewModel.loadGroupMembers(it)
                    groupViewModel.loadGroupPosts(it, 1, 10)
                }
            }

            GroupDetailsScreen(
                navController = navController,
                viewModel = groupViewModel,
                isCreator = isCreator == true,
                onLike = {post, id->
                    postViewModel.toggleLike(post, id)

                    Log.d("GroupVM RELOAD", "groupId = $groupId")
                    groupViewModel.loadGroupPosts(groupId?:0,1, 10)
                },
                onOtherProfileClick = {userId->
                    if(profile?.data?.id == userId){
                        mainNavController2.navigate(MainRoutes.Menu.route)
                    }else{
                        mainNavController2.navigate(
                            MainRoutes.OtherProfile.createRoute(userId)
                        )


                    }

                }
            )
        }

        composable(GroupsRoutes.CreateGroup.route) {

            LaunchedEffect(Unit) {
                groupViewModel.loadGroupCategoryTypes()
            }

            CreateGroupScreen(
                viewModel = groupViewModel,
                navController = navController,
                onGroupCreate = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = GroupsRoutes.CreateGroupPost.route,
            arguments = listOf(
                navArgument("groupId") { type = NavType.IntType }
            )
        ) {backStackEntry->

            val groupId = backStackEntry.arguments?.getInt("groupId")

            UploadScreen(
                navController = navController,
                viewModel = uploadViewModel,
                type = "group post",
                groupId = groupId
            )
        }

        composable(
            route = GroupsRoutes.EditGroup.route,
            arguments = listOf(
                navArgument("groupId"){ type = NavType.StringType }
            )
        ){backStackEntry->
            val groupId = backStackEntry.arguments?.getString("groupId")

            EditGroupScreen(
                navController = navController,
                groupId = groupId?:"",
                viewModel = groupViewModel
            )
        }
    }
}