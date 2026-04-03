package com.example.sociamediaapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.repository.JobRepository
import com.example.sociamediaapplication.data.repository.MarketplaceRepository
import com.example.sociamediaapplication.view.screens.JobScreen
import com.example.sociamediaapplication.view.screens.JobsScreen
import com.example.sociamediaapplication.viewmodel.JobViewModel
import com.example.sociamediaapplication.viewmodel.MarketplaceViewModel
import com.example.sociamediaapplication.viewmodel.factory.JobViewModelFactory
import com.example.sociamediaapplication.viewmodel.factory.MarketplaceViewModelFactory

@Composable
fun JobsNavGraph(bNavController: NavHostController) {

    val navController = rememberNavController()

    val context = LocalContext.current.applicationContext
    val tokenManager = remember { TokenManager(context) }
    val repository = remember { JobRepository(tokenManager) }
    val factory = remember { JobViewModelFactory(repository) }


    val viewModel: JobViewModel = viewModel(factory = factory)

    NavHost(
        navController = navController,
        startDestination = "jobs"
    ){
        composable("jobs"){

            LaunchedEffect(Unit) {
                viewModel.loadMyJobs()
                viewModel.loadPublicJobs()
                viewModel.loadSavedJobs()
                viewModel.loadMyApplications()
            }

            JobsScreen(
                bNavController,
                viewModel
            )
        }
        composable("job"){
            JobScreen(
                navController
            )
        }
    }
}