package com.example.sociamediaapplication.model.request

data class CreateJobRequest(
    val title: String,
    val company_name: String,
    val description: String,
    val responsibilities: String,
    val requirements: String,
    val benefits: String,
    val location: String,
    val workplace_type: String,
    val job_type: String,
    val experience_level: String,
    val min_salary: Int,
    val max_salary: Int,
    val salary_currency: String,
    val vacancies: Int,
    val application_deadline: String
)
