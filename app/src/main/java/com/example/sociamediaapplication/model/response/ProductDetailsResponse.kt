package com.example.sociamediaapplication.model.response

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
    val stock: Int,
    val description: String?,
    val vendor_id: Int,
    val username: String,
)
