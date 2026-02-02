package com.example.sociamediaapplication.viewmodel

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.toColorLong
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.sociamediaapplication.model.editor.*
import com.example.sociamediaapplication.ui.theme.Black
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


        val offset = Offset(
            x = (canvasSize.width - width) / 2f,
            y = (canvasSize.height - height) / 2f
        )

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

    fun addCenteredSticker(uri:String){

    }

    fun addMusic(uri:String){

    }

    fun addCenteredText(text: String) {
        val layer = TextLayer(
            id = generateId(),
            text = text,
            textColor = Black,
            fontSize = 24,
            fontWeight = FontWeight.Bold
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

    fun updateText(id: String, newText: String){
        _layers.update { current ->
            current.map { layer ->
                if (layer.id != id) return@map layer

                when (layer) {
                    is TextLayer -> layer.copy(text = newText)
                    else -> layer
                }
            }
        }
    }
    fun updateTextColor(id: String, color: Color) {
        _layers.update { current ->
            current.map { layer ->
                if (layer.id != id) return@map layer

                when (layer) {
                    is TextLayer -> layer.copy(
                        textColor = color
                    )
                    else -> layer
                }
            }
        }
    }

    fun updateTextSize(id: String, fontSize: Int)
    {
        _layers.update{current->
            current.map{layer->
                if(layer.id!=id) return@map layer

                when(layer){
                    is TextLayer -> layer.copy(
                        fontSize = fontSize
                    )
                    else-> layer
                }
            }
        }
    }
    fun updateFontFamily(id: String, fontFamily: FontFamily) {
        _layers.update { current ->
            current.map { layer ->
                if (layer.id == id && layer is TextLayer) {
                    layer.copy(fontFamily = fontFamily)
                } else layer
            }
        }
    }


    fun updateTextStyle(id:String, fontWeight: FontWeight){
        _layers.update { current->
            current.map { layer->
                if(layer.id!=id) return@map layer

                when(layer){
                    is TextLayer-> layer.copy(
                        fontWeight = fontWeight
                    )
                    else->layer
                }
            }
        }
    }

    fun updateLayerSize(id: String, size: IntSize) {

        if (size == IntSize.Zero) return

        _layers.update { current ->

            current.map { layer ->

                if (layer.id != id) return@map layer

                // Only center if it hasn't been moved yet
                if (layer.offset != Offset.Zero) {
                    return@map when (layer) {
                        is ImageLayer -> layer.copy(size = size)
                        is TextLayer -> layer.copy(size = size)
                        is StickerLayer -> layer.copy(size = size)
                        else -> layer
                    }
                }

                when (layer) {

                    is ImageLayer -> {
                        val updated = layer.copy(size = size)
                        updated.copy(offset = centerOffset(updated))
                    }

                    is TextLayer -> {
                        val updated = layer.copy(size = size)
                        updated.copy(offset = centerOffset(updated))
                    }

                    is StickerLayer -> {
                        val updated = layer.copy(size = size)
                        updated.copy(offset = centerOffset(updated))
                    }

                    else -> layer
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