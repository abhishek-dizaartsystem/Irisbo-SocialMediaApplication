package com.example.sociamediaapplication.view.navigation

sealed class JobsRoutes(val route: String){
    object Jobs: JobsRoutes("jobs")
    object Job: JobsRoutes("job/{id}"){
        fun createRoute(id:Int) = "job/$id"
    }
    object Recruiter: JobsRoutes("recruiter")
    object CreateJob: JobsRoutes("create")

    object ApplyForJob: JobsRoutes("apply/{id}"){
        fun createRoute(id: Int) = "apply/$id"
    }
}
