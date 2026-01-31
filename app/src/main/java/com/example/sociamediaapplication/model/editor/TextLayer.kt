package com.example.sociamediaapplication.model.editor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize

data class TextLayer(
    override val id: String,
    val text: String,
    val textColor: Long,
    val textSize: Float,
    override val offset: Offset = Offset.Zero,
    override val scale: Float = 1f,
    override val rotation: Float = 0f,
    override val zIndex: Float = 0f,
    override val size: IntSize = IntSize.Zero,
    override val hasBeenCentered: Boolean = false
) : EditorLayer()
