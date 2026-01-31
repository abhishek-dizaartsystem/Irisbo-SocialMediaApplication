package com.example.sociamediaapplication.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

data class VideoAnalyticsCardModel(
    val icon: Int,
    val iconTint: Color,
    val value: String,
    val name: String
)