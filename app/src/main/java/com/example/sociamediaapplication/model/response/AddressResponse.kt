package com.example.sociamediaapplication.model.response

import com.example.sociamediaapplication.view.navigation.MainRoutes


data class AddressResponse(
    val success: Boolean,
    val data: List<Address>
)

data class Address(
    val id: Int,
    val user_id: Int,
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
    val is_default: Boolean
)
