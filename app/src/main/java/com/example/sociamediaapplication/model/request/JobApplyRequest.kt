package com.example.sociamediaapplication.model.request

data class JobApplyRequest(
    val full_name: String,
    val email: String,
    val phone: String,
    val resume_url: String,
    val cover_letter: String,
    val experience_years: Float,
    val current_company: String,
    val current_ctc: Int,
    val expected_ctc: Int,
    val notice_period: String,
    val portfolio_url: String,
    val linkedin_url: String
)
