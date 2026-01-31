package com.example.sociamediaapplication.model.editor

import androidx.compose.ui.geometry.Offset

sealed class EditorLayer {

    abstract val id: String

    abstract var offset: Offset
    abstract var scale: Float
    abstract var rotation: Float
    abstract var zIndex: Float

    var isSelected: Boolean = false
}
