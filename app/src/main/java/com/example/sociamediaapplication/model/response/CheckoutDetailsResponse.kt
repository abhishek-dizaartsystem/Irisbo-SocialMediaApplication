package com.example.sociamediaapplication.model.response

import com.example.sociamediaapplication.model.ShippingPrice

data class CheckoutDetailsResponse(
    val tax: Double,
    val shipping: List<ShippingPrice>,
)
