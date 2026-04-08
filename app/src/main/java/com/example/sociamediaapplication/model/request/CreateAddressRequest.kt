package com.example.sociamediaapplication.model.request

data class CreateAddressRequest(
    val full_name: String,
    val phone: String,
    val address_line1: String,
    val address_line2: String,
    val landmark: String,
    val city: String,
    val state: String,
    val postal_code: String,
    val country: String,
    val address_type: String,
    val is_default: Int
)
