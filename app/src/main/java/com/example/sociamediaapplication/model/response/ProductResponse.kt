package com.example.sociamediaapplication.model.response

import com.example.sociamediaapplication.model.Specification

data class VendorProductsResponse(
    val success: Boolean,
    val products: List<VendorProductResponse>,
    val pagination: PaginationResponse
)

data class VendorProductResponse(
    val id: Int,
    val name: String,
    val category_name: String?,
    val price: String,
    val discount: String,
    val final_price: String,
    val stock: Int,
    val sold: Int,
    val status: String,
    val created_at: String,
    val specifications: List<Specification>,
    val username: String,
    val product_image: String,
    val description: String? = "No description fetched",
)
