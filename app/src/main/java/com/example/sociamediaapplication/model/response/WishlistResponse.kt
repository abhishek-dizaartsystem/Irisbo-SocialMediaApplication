package com.example.sociamediaapplication.model.response

import com.example.sociamediaapplication.model.WishlistItem

data class WishlistResponse(
    val success: Boolean,
    val wishlist: List<WishlistItem>,
    val pagination: PaginationResponse
)