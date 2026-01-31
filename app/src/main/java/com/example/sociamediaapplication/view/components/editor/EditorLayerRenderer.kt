package com.example.sociamediaapplication.view.components.editor

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.compose.material3.Text
import com.example.sociamediaapplication.model.editor.*

@Composable
fun EditorLayerRenderer(
    layer: EditorLayer,
    isSelected: Boolean,
    onTap: () -> Unit,
    onTransform: (Offset, Float, Float) -> Unit,
    onSizeMeasured: (IntSize) -> Unit
) {
    val currentLayer by rememberUpdatedState(layer)
    Box(
        modifier = Modifier
            .wrapContentSize()
            .onSizeChanged {
                onSizeMeasured(it)
            }
            .graphicsLayer(
                translationX = currentLayer.offset.x,
                translationY = currentLayer.offset.y,
                scaleX = currentLayer.scale,
                scaleY = currentLayer.scale,
                rotationZ = currentLayer.rotation
            )
            .pointerInput(currentLayer.id) {
                detectTransformGestures { _, pan, zoom, rotation ->
                    onTransform(
                        currentLayer.offset + pan,
                        currentLayer.scale * zoom,
                        currentLayer.rotation + rotation
                    )
                }
            }
                .pointerInput(currentLayer.id + "_tap") {
                    detectTapGestures {
                        onTap()
                    }
                }
            .then(
                if (isSelected) Modifier.border(2.dp, Color.White) else Modifier
            )
    ) {
        when (layer) {
            is ImageLayer -> {
                AsyncImage(
                    model = layer.imageUri,
                    contentDescription = null,
                )
            }
            is TextLayer -> {
                Text(
                    text = layer.text,
                    color = Color(layer.textColor),
                    fontSize = layer.textSize.sp
                )
            } else -> {}
        }
    }
}