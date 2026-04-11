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

    object Applicants: JobsRoutes("job/applicants/{jobId}"){
        fun createRoute(jobId: Int) = "job/applicants/$jobId"
    }

    object Applicant: JobsRoutes("applicant/{jobId}/{applicantId}"){
        fun createRoute(applicantId: Int, jobId: Int) = "applicant/$jobId/$applicantId"
    }
}
