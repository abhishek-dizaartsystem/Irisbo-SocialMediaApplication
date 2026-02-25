package com.example.sociamediaapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sociamediaapplication.view.screens.CreateGroupScreen
import com.example.sociamediaapplication.view.screens.EditGroupScreen
import com.example.sociamediaapplication.view.screens.GroupDetailsScreen
import com.example.sociamediaapplication.view.screens.GroupsScreen

@Composable
fun GroupsNavGraph(
    bNavController: NavController = rememberNavController()
){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "groups"
    ){
        composable("groups") {
            GroupsScreen(
                bNavController,
                navController,
                onGroupClick = {groupId->
                    navController.navigate(
                        GroupsRoutes.Group.createRoute(groupId)
                    )
                },
                onEditClick = {groupId->
                    navController.navigate(
                        GroupsRoutes.EditGroup.createRoute(groupId)
                    )
                }
            )
        }
        composable(
            route = GroupsRoutes.Group.route,
            arguments = listOf(
                navArgument("groupId"){ type = NavType.StringType}
            )
        ) {backStackEntry->

            val groupId = backStackEntry.arguments?.getString("groupId")

            GroupDetailsScreen(
                groupId = groupId ?: "",
                navController = navController
            )
        }

        composable(GroupsRoutes.CreateGroup.route) {
            CreateGroupScreen(navController = navController)
        }

        composable(
            route = GroupsRoutes.EditGroup.route,
            arguments = listOf(
                navArgument("groupId"){ type = NavType.StringType }
            )
        ){backStackEntry->
            val groupId = backStackEntry.arguments?.getString("groupId")

            EditGroupScreen(navController = navController, groupId = groupId?:"")
        }
    }
}