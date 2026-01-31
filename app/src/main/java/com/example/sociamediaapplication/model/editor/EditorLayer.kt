package com.example.sociamediaapplication.model.editor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize

sealed class EditorLayer {

    abstract val id: String
    abstract val offset: Offset
    abstract val scale: Float
    abstract val rotation: Float
    abstract val zIndex: Float
    abstract val size: IntSize
    abstract val hasBeenCentered: Boolean
}
