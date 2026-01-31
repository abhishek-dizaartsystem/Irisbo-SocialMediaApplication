package com.example.sociamediaapplication.model.editor

import androidx.compose.ui.geometry.Offset

data class TextLayer(
    override val id: String,

    var text: String,
    var textColor: Long,
    var textSize: Float,
    var fontFamilyName: String? = null,

    override var offset: Offset = Offset.Zero,
    override var scale: Float = 1f,
    override var rotation: Float = 0f,
    override var zIndex: Float = 0f
) : EditorLayer()
