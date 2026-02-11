package com.example.sociamediaapplication.model

import androidx.compose.ui.graphics.painter.Painter

data class MarketplaceItem(
    val productId: String,
    val painter: Int,
    val price: Float,
    val productName: String
)
