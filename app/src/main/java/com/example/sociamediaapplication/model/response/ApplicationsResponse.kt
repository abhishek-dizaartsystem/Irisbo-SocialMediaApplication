package com.example.sociamediaapplication.model.response

data class ApplicationsResponse(
    val success: Boolean,
    val pagination: PaginationResponse,
    val data: List<ApplicationResponse>
)

data class ApplicationResponse(
    val id: Int,
    val job_id: Int,
    val applicant_id: Int,
    val full_name: String,
    val email: String,
    val phone: String,
    val resume_url: String,
    val cover_letter: String,
    val experience_years: String,
    val current_company: String,
    val current_ctc: String,
    val expected_ctc: String,
    val notice_period: String,
    val portfolio_url: String,
    val linkedin_url: String,
    val status: String,
    val applied_at: String,
    val updated_at: String,
    val title: String,
    val company_name: String,
    val location: String,
    val workplace_type: String,
    val job_type: String,
    val experience_level: String,
    val min_salary: String,
    val max_salary:String,
    val salary_currency: String,
    val job_status: String,
    val page_id: Int?,
    val employer_name: String,
    val page_name: String?,
    val display_company_name: String,
    val posted_by_type: String
)
