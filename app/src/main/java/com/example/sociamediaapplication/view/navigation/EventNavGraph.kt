package com.example.sociamediaapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.repository.EventRepository
import com.example.sociamediaapplication.view.screens.CreateEventScreen
import com.example.sociamediaapplication.view.screens.EventScreen
import com.example.sociamediaapplication.view.screens.EventsScreen
import com.example.sociamediaapplication.viewmodel.EventViewModel
import com.example.sociamediaapplication.viewmodel.factory.EventViewModelFactory

@Composable
fun EventNavGraph(bNavController: NavHostController, initialEventId: Int? = null) {

    val navController = rememberNavController()

    val context = LocalContext.current.applicationContext

    val tokenManager = remember { TokenManager(context) }

    val eventRepository = remember { EventRepository(tokenManager) }
    val eventViewModelFactory = remember { EventViewModelFactory(eventRepository) }
    val eventViewModel: EventViewModel = viewModel(factory = eventViewModelFactory)

    NavHost(
        navController = navController,
        startDestination = if (initialEventId != null) EventRoutes.Event.createRoute(initialEventId, false) else EventRoutes.Events.route
    ) {
        composable(EventRoutes.Events.route){

            LaunchedEffect(Unit) {
                eventViewModel.loadEvents()
                eventViewModel.loadMyEvents()
            }
            EventsScreen(
                navController = bNavController,
                onEventClick = {eventId, isCreator->
                    navController.navigate(
                        EventRoutes.Event.createRoute(eventId, isCreator)
                    )
                },
                onCreateClick = {
                    navController.navigate(EventRoutes.CreateEvent.route)
                },
                viewModel = eventViewModel
            )
        }
        composable(
            EventRoutes.Event.route,
            arguments = listOf(
                navArgument("eventId"){ type = NavType.IntType },
                navArgument("isCreator"){ type = NavType.BoolType }
            )
        ){backStackEntry->

            val eventId = backStackEntry.arguments?.getInt("eventId")
            val isCreator = backStackEntry.arguments?.getBoolean("isCreator")

            LaunchedEffect(eventId, isCreator) {
                eventViewModel.loadEventDetails(eventId?: 0)
            }
            EventScreen(
                navController = navController,
                viewModel = eventViewModel,
                isCreator = isCreator == true
            )
        }
        composable(
            EventRoutes.CreateEvent.route
        ){

            LaunchedEffect(Unit) {
                eventViewModel.loadCategories()
            }
            CreateEventScreen(
                navController = navController,
                viewModel = eventViewModel
            )
        }

    }
}