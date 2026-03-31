package com.example.sociamediaapplication.model.response

import com.example.sociamediaapplication.model.Specification

data class UserProductsResponse(
    val success: Boolean,
    val products: List<UserProductResponse>,
    val pagination: PaginationResponse
)

data class UserProductResponse(
    val id: Int,
    val name: String,
    val category_name: String?,
    val product_image: String?,
    val price: String,
    val discount: String,
    val final_price: String,
    val stock: Int,
    val specifications: List<Specification>,
    val created_at: String,
    val seller_username: String,
    val is_wishlisted: Boolean
)
