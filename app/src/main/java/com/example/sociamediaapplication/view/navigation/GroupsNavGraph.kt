package com.example.sociamediaapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.sociamediaapplication.viewmodel.GroupViewModel
import com.example.sociamediaapplication.viewmodel.factory.GroupViewModelFactory

@Composable
fun GroupsNavGraph(
    bNavController: NavController = rememberNavController()
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
                onGroupClick = {groupId->
                    navController.navigate(
                        GroupsRoutes.Group.createRoute(groupId)
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
                navArgument("groupId"){ type = NavType.IntType}
            )
        ) {backStackEntry->

            val groupId = backStackEntry.arguments?.getInt("groupId")

            LaunchedEffect(groupId){
                groupId?.let {
                    groupViewModel.loadGroupDetails(it)
                    groupViewModel.loadGroupMembers(it)
                }
            }

            GroupDetailsScreen(
                navController = navController,
                viewModel = groupViewModel
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