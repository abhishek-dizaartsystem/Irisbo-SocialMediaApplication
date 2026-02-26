package com.example.sociamediaapplication.model.response

import com.example.sociamediaapplication.model.Specification

data class ProductDetailsResponse(
    val success: Boolean,
    val product: ProductDetails
)

data class ProductDetails(
    val id: Int,
    val name: String,
    val category: String,

    val product_image: String,

    val price: String,
    val discount: String,
    val discounted_price: String,

    val stock: Int,
    val description: String?,

    val vendor_id: Int,
    val username: String,

    val specifications: List<Specification>,
    val product_images: List<String>
)


