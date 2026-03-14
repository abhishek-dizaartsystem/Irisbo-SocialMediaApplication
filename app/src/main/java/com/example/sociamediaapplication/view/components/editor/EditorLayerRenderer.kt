package com.example.sociamediaapplication.view.components.editor

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sociamediaapplication.model.editor.EditorLayer
import com.example.sociamediaapplication.model.editor.ImageLayer
import com.example.sociamediaapplication.model.editor.TextLayer
import kotlin.math.roundToInt

@Composable
fun EditorLayerRenderer(
    layer: EditorLayer,
    isSelected: Boolean,
    onTap: () -> Unit,
    onTransform: (Offset, Float, Float) -> Unit,
    onSizeMeasured: (IntSize) -> Unit,
    onTextChange: (String) -> Unit,
    onTextColorChange: (Color) -> Unit
) {
    val currentLayer by rememberUpdatedState(layer)



    Box(
        modifier = Modifier
            .wrapContentSize()
            .onSizeChanged {
                onSizeMeasured(it)
            }

            .graphicsLayer(
                scaleX = currentLayer.scale,
                scaleY = currentLayer.scale,
                rotationZ = currentLayer.rotation,
                transformOrigin = TransformOrigin.Center
            )
            .offset {
                IntOffset(
                    currentLayer.offset.x.roundToInt(),
                    currentLayer.offset.y.roundToInt()
                )
            }
            .pointerInput(currentLayer.id) {
                detectTransformGestures { _, pan, zoom, rotation ->
                    Log.d("PAN_DEBUG", "pan=$pan scale=${currentLayer.scale}")
                    val newScale = currentLayer.scale * zoom

                    val newOffset = currentLayer.offset + (pan)

                    val newRotation = currentLayer.rotation + rotation

                    onTransform(newOffset, newScale, newRotation)
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
                    modifier = Modifier
                        .wrapContentSize()
                        .onSizeChanged {
                            onSizeMeasured(it)
                        }
                )
            }
            is TextLayer -> {


                if (isSelected) {
                    BasicTextField(
                        value = layer.text,
                        onValueChange = { newText ->
                            onTextChange(newText)
                        },
                        textStyle = TextStyle(
                            color = layer.textColor,
                            fontSize = layer.fontSize.sp,
                            fontFamily = layer.fontFamily
                        ),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .widthIn(max = 300.dp, min = 50.dp)
                    )


                } else {
                    Text(
                        text = layer.text,
                        color = layer.textColor,
                        fontSize = layer.fontSize.sp,
                        fontFamily = layer.fontFamily
                    )
                }
            } else -> {}
        }
    }
}