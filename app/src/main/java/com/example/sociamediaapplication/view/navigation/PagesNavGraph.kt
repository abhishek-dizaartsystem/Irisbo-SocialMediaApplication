package com.example.sociamediaapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sociamediaapplication.view.screens.GroupScreen
import com.example.sociamediaapplication.view.screens.PageScreen
import com.example.sociamediaapplication.view.screens.PagesScreen

@Composable
fun PagesNavGraph(
    bNavController: NavController = rememberNavController()
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "pages"
    ){
        composable("pages") {
            PagesScreen(
                bNavController,
                navController,
                onPageClick = {pageId->
                    navController.navigate(
                        GroupsRoutes.Group.createRoute(pageId)
                    )
                }
            )
        }
        composable(
            route = PagesRoutes.Page.route,
            arguments = listOf(
                navArgument("pageId"){ type = NavType.StringType}
            )
        ) {backStackEntry->

            val pageId = backStackEntry.arguments?.getString("pageId")

            PageScreen(
                pageId = pageId ?: "",
                navController = navController
            )
        }
    }
}