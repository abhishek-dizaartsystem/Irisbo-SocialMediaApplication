package com.example.sociamediaapplication.view.components

import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.geometry.Size

val HexagonShape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: androidx.compose.ui.unit.Density
    ): Outline {
        val path = Path().apply {
            val radius = size.minDimension / 2f
            val centerX = size.width / 2f
            val centerY = size.height / 2f
            val angle = Math.PI / 3 // 60 degrees

            for (i in 0..5) {
                val x = centerX + radius * Math.cos(angle * i - Math.PI / 2).toFloat()
                val y = centerY + radius * Math.sin(angle * i - Math.PI / 2).toFloat()
                if (i == 0) moveTo(x, y) else lineTo(x, y)
            }
            close()
        }
        return Outline.Generic(path)
    }
}