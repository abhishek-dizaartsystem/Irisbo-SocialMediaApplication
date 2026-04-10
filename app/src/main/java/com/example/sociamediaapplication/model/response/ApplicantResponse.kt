package com.example.sociamediaapplication.model.response

data class ApplicantsResponse(
    val success: Boolean,
    val data: List<ApplicantResponse>
)

data class ApplicantResponse(
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
    val applicant_name: String,
    val user_email: String
)