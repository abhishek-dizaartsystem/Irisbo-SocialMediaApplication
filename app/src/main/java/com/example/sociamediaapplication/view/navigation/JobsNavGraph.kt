package com.example.sociamediaapplication.view.navigation

import android.util.Log
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
import com.example.sociamediaapplication.data.repository.JobRepository
import com.example.sociamediaapplication.view.screens.ApplicantScreen
import com.example.sociamediaapplication.view.screens.ApplicantsScreen
import com.example.sociamediaapplication.view.screens.CreateJobScreen
import com.example.sociamediaapplication.view.screens.JobApplyScreen
import com.example.sociamediaapplication.view.screens.JobRecruiterScreen
import com.example.sociamediaapplication.view.screens.JobScreen
import com.example.sociamediaapplication.view.screens.JobsScreen
import com.example.sociamediaapplication.viewmodel.JobViewModel
import com.example.sociamediaapplication.viewmodel.factory.JobViewModelFactory

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
        startDestination = JobsRoutes.Jobs.route
    ){
        composable(JobsRoutes.Jobs.route){

            LaunchedEffect(Unit) {
                viewModel.loadPublicJobs()
                viewModel.loadSavedJobs()
                viewModel.loadMyApplications()
            }

            JobsScreen(
                bNavController,
                viewModel,
                onJobClick = {id->
                    navController.navigate(
                        JobsRoutes.Job.createRoute(id))
                },
                onRecruiterClick = {
                    navController.navigate(JobsRoutes.Recruiter.route)
                },
                onApplyClick = {id->
                    navController.navigate(
                        JobsRoutes.ApplyForJob.createRoute(id)
                    )
                }
            )
        }
        composable(
            route = JobsRoutes.Job.route,
            arguments = listOf(
                navArgument("id"){ type = NavType.IntType}
            )
        ){backStackEntry->

            val id = backStackEntry.arguments?.getInt("id")

            LaunchedEffect(id) {
                viewModel.loadJobDetails(id ?: 0)
            }

            JobScreen(
                navController,
                viewModel = viewModel,
                onApplyClick = {
                    navController.navigate(
                        JobsRoutes.ApplyForJob.createRoute(id?: 0)
                    )
                }
            )


        }
        composable(JobsRoutes.Recruiter.route) {

            LaunchedEffect(Unit) {
                viewModel.loadMyJobs()
            }
            JobRecruiterScreen(
                navController,
                viewModel,
                onCreateClick = {
                    navController.navigate(JobsRoutes.CreateJob.route)
                },
                onJobClick = {id->
                    Log.d("JOBNAVGRAPH", "jobId = $id")
                    navController.navigate(
                        JobsRoutes.Applicants.createRoute(id)
                    )
                }
            )
        }

        composable(JobsRoutes.CreateJob.route) {

            LaunchedEffect(Unit) {
                viewModel.loadJobMetadata()
            }
            CreateJobScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(
            route = JobsRoutes.ApplyForJob.route,
            arguments = listOf(
                navArgument("id"){ type = NavType.IntType }
            )
        ){backStackEntry->

            val id = backStackEntry.arguments?.getInt("id")

            JobApplyScreen(
                navController = navController,
                viewModel = viewModel,
                id = id
            )

        }

        composable(
            route = JobsRoutes.Applicants.route,
            arguments = listOf(
                navArgument("jobId"){ type = NavType.IntType}
            )
        ) { backStackEntry->

            val jobId = backStackEntry.arguments?.getInt("jobId")

            LaunchedEffect(Unit) {
                viewModel.loadJobApplicants(jobId?:0)
            }

            ApplicantsScreen(
                navController = navController,
                viewModel = viewModel,
                jobId = jobId,
                onApplicantClick = {applicantId ->

                    Log.d("JobNAVGRAPH", "applicantId = $applicantId")
                    navController.navigate(
                        JobsRoutes.Applicant.createRoute(applicantId, jobId?:0)
                    )
                }
            )
        }

        composable(
            route = JobsRoutes.Applicant.route,
            arguments = listOf(
                navArgument("applicantId"){type = NavType.IntType},
                navArgument("jobId"){type = NavType.IntType}
            )
        ) {backStackEntry->

            val applicantId = backStackEntry.arguments?.getInt("applicantId")
            val jobId = backStackEntry.arguments?.getInt("jobId")


            Log.d("JobNAVGRAPH", "applicantId = $applicantId\njobId = $jobId")

            LaunchedEffect(Unit) {
                viewModel.loadApplicantDetails(jobId?:0, applicantId?:0)
                viewModel.loadApplicationMetadata()
            }

            ApplicantScreen(
                navController,
                viewModel
            )

        }

    }
}