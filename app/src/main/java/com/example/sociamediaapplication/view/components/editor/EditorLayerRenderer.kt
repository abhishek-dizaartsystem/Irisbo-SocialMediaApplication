package com.example.sociamediaapplication.view.components.editor

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Green
import com.example.sociamediaapplication.ui.theme.Red
import com.example.sociamediaapplication.ui.theme.TransparentBlack
import com.example.sociamediaapplication.ui.theme.TransparentWhite
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.ui.theme.Yellow

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

    var textColorOptions = remember { mutableStateListOf(
        Black, White, Blue, Red, Green, Yellow
    ) }


    Box(
        modifier = Modifier
            .wrapContentSize()
            .onSizeChanged {
                Log.d("EDITOR_DEBUG", "Layer ${layer.id} size: $it")
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
                val selectedTextColor = layer.textColor

                if (isSelected) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            textColorOptions.forEach { color->
                                IconButton(
                                    onClick = {
                                        //selectedTextColor = color
                                        onTextColorChange(color)
                                    },
                                    modifier = Modifier.size(30.dp),
                                    colors = IconButtonDefaults.iconButtonColors(
                                        containerColor = if(color == selectedTextColor) TransparentWhite else TransparentBlack
                                    )
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.dot),
                                        contentDescription = "",
                                        tint = color,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }

                        }
                        BasicTextField(
                            value = layer.text,
                            onValueChange = { newText ->
                                onTextChange(newText)
                            },
                            textStyle = TextStyle(
                                color = layer.textColor,
                                fontSize = layer.textSize.sp
                            ),
                            modifier = Modifier.widthIn(max = 300.dp)
                        )
                    }

                } else {
                    Text(
                        text = layer.text,
                        color = layer.textColor,
                        fontSize = layer.textSize.sp
                    )
                }
            } else -> {}
        }
    }
}