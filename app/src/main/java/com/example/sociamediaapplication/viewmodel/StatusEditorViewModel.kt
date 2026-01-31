package com.example.sociamediaapplication.viewmodel

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.example.sociamediaapplication.model.editor.EditorLayer
import com.example.sociamediaapplication.model.editor.StickerLayer
import com.example.sociamediaapplication.model.editor.TextLayer
import com.example.sociamediaapplication.model.editor.ImageLayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class StatusEditorViewModel: ViewModel() {

    private val _layers = MutableStateFlow<List<EditorLayer>>(emptyList())
    val layers: StateFlow<List<EditorLayer>> = _layers

    private val _selectedLayerId = MutableStateFlow<String?>(null)
    val selectedLayerId: StateFlow<String?> = _selectedLayerId

    // -----------------------------
    // Layer Management
    // -----------------------------

    fun addLayer(layer: EditorLayer) {
        _layers.update { current ->
            val highestZ = current.maxOfOrNull { it.zIndex } ?: 0f
            current + layer.apply { zIndex = highestZ + 1 }
        }
        selectLayer(layer.id)
    }

    fun removeLayer(id: String) {
        _layers.update { it.filterNot { layer -> layer.id == id } }
        _selectedLayerId.value = null
    }

    fun selectLayer(id: String?) {
        _selectedLayerId.value = id
    }

    fun bringToFront(id: String) {
        _layers.update { current ->
            val highestZ = current.maxOfOrNull { it.zIndex } ?: 0f
            current.map {
                when (it) {
                    is ImageLayer -> it.copy(zIndex = highestZ + 1)
                    is TextLayer -> it.copy(zIndex = highestZ + 1)
                    is StickerLayer -> it.copy(zIndex = highestZ + 1)
                    else -> it
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

                if (layer.id != id) return@map layer

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

                    is StickerLayer -> layer.copy(
                        offset = offset ?: layer.offset,
                        scale = scale ?: layer.scale,
                        rotation = rotation ?: layer.rotation
                    )

                    else -> layer
                }
            }
        }
    }


    fun generateId(): String = UUID.randomUUID().toString()
}