package com.example.sociamediaapplication.model.response

import com.example.sociamediaapplication.model.Specification

data class VendorProductsResponse(
    val success: Boolean,
    val total_products: Int,
    val products: List<VendorProductResponse>
)

data class VendorProductResponse(
    val id: Int,
    val name: String,
    val category: String?,
    val price: String,
    val discount: String,
    val discounted_price: String,
    val stock: Int,
    val created_at: String,
    val username: String,
    val specifications: List<Specification>,
    val product_images: List<String>,
    val is_liked: Boolean,
    val description: String? = "No description fetched",
)
