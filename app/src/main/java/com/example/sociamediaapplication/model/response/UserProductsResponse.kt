package com.example.sociamediaapplication.model.response

import com.example.sociamediaapplication.model.Specification

data class UserProductsResponse(
    val success: Boolean,
    val total_products: Int,
    val products: List<UserProductResponse>
)

data class UserProductResponse(
    val id: Int,
    val name: String,
    val category: String?,
    val product_image: String?,
    val price: String,
    val discount: String,
    val discounted_price: String,
    val stock: Int,
    val created_at: String,
    val username: String,
    val specifications: List<Specification>,
    val is_liked: Boolean,
    val product_images: List<String>
)
