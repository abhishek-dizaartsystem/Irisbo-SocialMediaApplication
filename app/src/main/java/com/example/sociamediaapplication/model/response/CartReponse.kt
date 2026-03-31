package com.example.sociamediaapplication.model.response

data class CartResponse(
    val success: Boolean,
    val items: List<CartProduct>,
    val summary: Summary2Response
)


data class CartProduct(
    val id: Int,
    val quantity: Int,
    val user_id: Int,
    val category_id: Int,
    val name: String,
    val price: Int,
    val seller_name: String,
    val seller_username: String,
    val discount: Int,
    val product_image: String,
    val final_price: Int,
    val subtotal: Int
)
