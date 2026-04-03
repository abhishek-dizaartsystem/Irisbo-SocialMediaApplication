package com.example.sociamediaapplication.model.response

data class PublicJobsResponse(
    val success: Boolean,
    val pagination: PaginationResponse,
    val data: List<PublicJobResponse>
)

data class PublicJobResponse(
    val id: Int,
    val employer_id: Int,
    val page_id: Int?,
    val title: String,
    val company_name: String?,
    val description: String,
    val responsibilities: String,
    val requirements: String,
    val benefits: String?,
    val location: String,
    val workplace_type: String,
    val job_type: String,
    val experience_level: String,
    val min_salary: String,
    val max_salary:String,
    val salary_currency: String,
    val vacancies: Int,
    val application_deadline: String,
    val status: String,
    val views_count: Int,
    val applications_count: Int,
    val created_at: String,
    val updated_at: String,
    val employer_name: String,
    val page_name: String?,
    val display_company_name: String,
    val posted_by_type: String,
    val is_saved: Int,
    val has_applied: Int
)