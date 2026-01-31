package com.example.sociamediaapplication.viewmodel

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.ViewModel
import com.example.sociamediaapplication.model.editor.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class StatusEditorViewModel : ViewModel() {
    private val _layers = MutableStateFlow<List<EditorLayer>>(
        emptyList()
    )
    val layers: StateFlow<List<EditorLayer>> = _layers
    private val _selectedLayerId = MutableStateFlow<String?>(null)
    val selectedLayerId: StateFlow<String?> = _selectedLayerId
    private var canvasSize: IntSize = IntSize.Zero
    fun setCanvasSize(size: IntSize) {
        canvasSize = size
    }

    private fun centerOffset(layer: EditorLayer): Offset {
        val width = layer.size.width * layer.scale
        val height = layer.size.height * layer.scale
        return Offset(
            x = (canvasSize.width - width) / 2f,
            y = (canvasSize.height - height) / 2f
        )
    }

    fun addCenteredImage(uri: String) {
        val layer = ImageLayer(
            id = generateId(),
            imageUri = uri
        )
        addLayer(layer)
    }

    fun addCenteredText(text: String) {
        val layer = TextLayer(
            id = generateId(),
            text = text,
            textColor = 0xFFFFFFFF,
            textSize = 24f
        )
        addLayer(layer)
    }

    fun addLayer(layer: EditorLayer) {
        _layers.update { current ->
            val highestZ = current.maxOfOrNull { it.zIndex } ?: 0f
            current + when (layer) {
                is ImageLayer ->
                    layer.copy(zIndex = highestZ + 1)
                is TextLayer ->
                    layer.copy(zIndex = highestZ + 1)
                else ->
                    layer
            }
        }
        selectLayer(layer.id)
    }

    fun updateLayerSize(id: String, size: IntSize) {
        _layers.update { current ->
            current.map { layer ->
                if (layer.id != id)
                    return@map layer
                if (
                    layer.hasBeenCentered ||
                    size.width == 0 ||
                    size.height == 0 ||
                    layer.offset != Offset.Zero
                ) return@map layer

                val updated = when (layer) {
                    is ImageLayer ->
                        layer.copy(size = size)
                    is TextLayer ->
                        layer.copy(size = size)
                    else -> layer
                }
                updated.let {
                    when (it) {
                        is ImageLayer ->
                            it.copy(
                                offset = centerOffset(it),
                                hasBeenCentered = true
                            )
                        is TextLayer ->
                            it.copy(
                                offset = centerOffset(it),
                                hasBeenCentered = true
                            )
                        else -> it
                    }
                }
            }
        }
    }

    fun updateTransform(
        id: String,
        offset: Offset? = null,
        scale: Float? = null,
        rotation: Float? = null
    ) {
        _layers.update { current ->
            current.map { layer ->
                if (layer.id != id)
                    return@map layer
                when (layer) {
                    is ImageLayer -> layer.copy(
                        offset = offset ?: layer.offset,
                        scale = scale ?: layer.scale,
                        rotation = rotation ?: layer.rotation
                    )
                    is TextLayer -> layer.copy(
                        offset = offset ?: layer.offset,
                        scale = scale ?: layer.scale,
                        rotation = rotation ?: layer.rotation
                    )
                    else -> layer
                }
            }
        }
    }

    fun selectLayer(id: String?) {
        _selectedLayerId.value = id
    }

    fun bringToFront(id: String) {
        _layers.update { current ->
            val highestZ = current.maxOfOrNull { it.zIndex } ?: 0f
            current.map {
                if (it.id == id) {
                    when (it) {
                        is ImageLayer ->
                            it.copy(zIndex = highestZ + 1)
                        is TextLayer -> it.copy(zIndex = highestZ + 1)
                        else -> it
                    }
                }
                else it
            }
        }
    }

    fun generateId(): String = UUID.randomUUID().toString()
}