package com.example.sociamediaapplication.model.editor

import androidx.compose.ui.geometry.Offset

data class ImageLayer(
    override val id: String,
    val imageUri: String,

    override var offset: Offset = Offset.Zero,
    override var scale: Float = 1f,
    override var rotation: Float = 0f,
    override var zIndex: Float = 0f
) : EditorLayer()
