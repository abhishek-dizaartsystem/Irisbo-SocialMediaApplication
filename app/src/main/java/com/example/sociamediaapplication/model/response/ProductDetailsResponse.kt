package com.example.sociamediaapplication.model.response

import com.example.sociamediaapplication.model.Specification

data class ProductDetailsResponse(
    val success: Boolean,
    val data: ProductDetails
)

data class ProductDetails(
    val id: Int,
    val name: String,
    val category: String,

    val product_image: String,

    val price: Int,
    val discount: Int,
    val final_price: Int,

    val stock: Int,
    val description: String?,

    val user_id: Int,
    val seller_username: String,

    val specifications: List<Specification>,
//    val average_rating: String,
//    val total_reviews: Int,
    val images: List<ImageResponse>
)

data class ImageResponse(
    val id: Int,
    val image_url: String
)

