package com.example.sociamediaapplication.model.editor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize

data class BackgroundLayer(
    override val id: String,
    val backgroundColor: Long? = null,
    val imageUri: String? = null,

    override var offset: Offset = Offset.Zero,
    override var scale: Float = 1f,
    override var rotation: Float = 0f,
    override var zIndex: Float = 0f,
    override var size: IntSize = IntSize.Zero,
    override val hasBeenCentered: Boolean
) : EditorLayer()
