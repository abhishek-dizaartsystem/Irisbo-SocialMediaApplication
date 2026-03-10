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
import com.example.sociamediaapplication.data.repository.PageRepository
import com.example.sociamediaapplication.view.screens.CreatePageScreen
import com.example.sociamediaapplication.view.screens.EditPageScreen
import com.example.sociamediaapplication.view.screens.PageScreen
import com.example.sociamediaapplication.view.screens.PagesScreen
import com.example.sociamediaapplication.viewmodel.GroupViewModel
import com.example.sociamediaapplication.viewmodel.PageViewModel
import com.example.sociamediaapplication.viewmodel.factory.GroupViewModelFactory
import com.example.sociamediaapplication.viewmodel.factory.PageViewModelFactory

@Composable
fun PagesNavGraph(
    bNavController: NavController = rememberNavController()
) {
    val navController = rememberNavController()

    val context = LocalContext.current.applicationContext
    val tokenManager = remember { TokenManager(context) }

    val pageRepository = remember { PageRepository(tokenManager) }
    val pageViewModelFactory = remember { PageViewModelFactory(pageRepository) }
    val pageViewModel: PageViewModel = viewModel(factory = pageViewModelFactory)

    NavHost(
        navController = navController,
        startDestination = "pages"
    ){
        composable("pages") {

            LaunchedEffect(Unit) {
                pageViewModel.loadPages()
            }

            PagesScreen(
                bNavController,
                navController,
                onPageClick = {pageId->
                    navController.navigate(
                        PagesRoutes.Page.createRoute(pageId)
                    )
                },
                viewModel =  pageViewModel
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
                navController = navController,
                viewModel = pageViewModel
            )
        }

        composable(PagesRoutes.CreatePage.route) {
            LaunchedEffect(Unit) {
                pageViewModel.loadCatgories()
            }
            CreatePageScreen(
                navController = navController,
                viewModel = pageViewModel
            )
        }

        composable(
            route = PagesRoutes.EditPage.route,
            arguments = listOf(
                navArgument("pageId"){
                    type = NavType.StringType
                }
            )) { backStackEntry->

            val pageId = backStackEntry.arguments?.getString("pageId")

            EditPageScreen(
                pageId = pageId?:"",
                navController = navController,
                viewModel = pageViewModel
            )
        }
    }
}