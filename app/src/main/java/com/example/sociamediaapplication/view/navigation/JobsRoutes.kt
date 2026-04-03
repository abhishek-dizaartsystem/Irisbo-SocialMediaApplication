package com.example.sociamediaapplication.view.navigation

sealed class JobsRoutes(val route: String){
    object Jobs: JobsRoutes("jobs")
    object Job: JobsRoutes("job")
}
