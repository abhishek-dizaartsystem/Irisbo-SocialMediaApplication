package com.example.sociamediaapplication.view.components.editor

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.sociamediaapplication.model.editor.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import androidx.compose.ui.unit.sp



@Composable
fun EditorLayerRenderer(
    layer: EditorLayer,
    isSelected: Boolean,
    onTap: () -> Unit,
    onTransform: (Offset, Float, Float) -> Unit
) {

    val currentLayer by rememberUpdatedState(layer)

    Box(
        modifier = Modifier
            .graphicsLayer(
                translationX = currentLayer.offset.x,
                translationY = currentLayer.offset.y,
                scaleX = currentLayer.scale,
                scaleY = currentLayer.scale,
                rotationZ = currentLayer.rotation
            )
            .pointerInput(currentLayer.id) {
                detectTransformGestures(
                    onGesture = { _, pan, zoom, rotation ->
                        val newOffset = currentLayer.offset + pan
                        val newScale = currentLayer.scale * zoom
                        val newRotation = currentLayer.rotation + rotation

                        onTransform(newOffset, newScale, newRotation)
                    }
                )
            }
            .pointerInput(layer.id + "_tap") {
                detectTapGestures(
                    onTap = {
                        onTap()
                    }
                )
            }

            .then(
                if (isSelected)
                    Modifier.border(2.dp, Color.White, RoundedCornerShape(6.dp))
                else Modifier
            )
    ) {

        when (layer) {

            is ImageLayer -> {
                AsyncImage(
                    model = layer.imageUri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

            is StickerLayer -> {
                Image(
                    painter = painterResource(layer.stickerResId),
                    contentDescription = null
                )
            }

            is TextLayer -> {
                androidx.compose.material3.Text(
                    text = layer.text,
                    color = Color(layer.textColor),
                    fontSize = layer.textSize.sp
                )
            }

            is VideoLayer -> {
                // We'll implement ExoPlayer later
            }

            is BackgroundLayer -> {
                // Usually rendered separately
            }
        }
    }
}


