package com.example.sociamediaapplication.view.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.rotate
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.model.editor.ImageLayer
import com.example.sociamediaapplication.model.editor.TextLayer
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.TransparentBlack
import com.example.sociamediaapplication.viewmodel.StatusEditorViewModel
import com.example.sociamediaapplication.view.components.editor.EditorLayerRenderer
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.sp
import com.example.sociamediaapplication.view.components.TextEditorControls
import com.example.sociamediaapplication.view.components.ToolIcon

@Composable
fun StatusEditorScreen(
    viewModel: StatusEditorViewModel = viewModel()
) {

    val layers by viewModel.layers.collectAsState()
    val selectedId by viewModel.selectedLayerId.collectAsState()

    val selectedLayer = layers.find { it.id == selectedId }
    val isTextSelected = selectedLayer is TextLayer


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Blue)
    ) {

        // 🔥 Canvas Layer
        Box(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged {
                    Log.d("EDITOR_DEBUG", "Canvas size: $it")
                    viewModel.setCanvasSize(it)
                }
                .pointerInput(Unit) {
                    detectTapGestures {
                        viewModel.selectLayer(null)
                    }
                }
        ) {

            layers
                .sortedBy { it.zIndex }
                .forEach { layer ->

                    EditorLayerRenderer(
                        layer = layer,
                        isSelected = layer.id == selectedId,
                        onTap = {
                            viewModel.selectLayer(layer.id)
                            viewModel.bringToFront(layer.id)
                        },
                        onTransform = { offset, scale, rotation ->
                            viewModel.updateTransform(
                                id = layer.id,
                                offset = offset,
                                scale = scale,
                                rotation = rotation
                            )
                        },
                        onSizeMeasured = { size ->
                            viewModel.updateLayerSize(layer.id, size)
                        },
                        onTextChange = { newText ->
                            viewModel.updateText(layer.id, newText)
                        },
                        onTextColorChange = { color ->
                            viewModel.updateTextColor(layer.id, color)
                        }
                    )
                }
        }

        // 🔥 Top Toolbar Overlay
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, start = 24.dp, end = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                ToolIcon(
                    icon = R.drawable.text_size_svgrepo_com,
                    onClick = {
                        viewModel.addCenteredText("Text")
                    }
                )
                ToolIcon(
                    icon = R.drawable.music_svgrepo_com,
                    onClick = {}
                )
                ToolIcon(R.drawable.edit_1_svgrepo_com,
                    onClick = {}
                )
                ToolIcon(R.drawable.sticker_add_svgrepo_com,
                    onClick = {}
                )
                ToolIcon(R.drawable.photo_svgrepo_com,
                    onClick = {}
                )
                ToolIcon(
                    icon = R.drawable.menu_dots_svgrepo_com,
                    rotate = 90f,
                    onClick = {}
                )


            }
            if (isTextSelected) {
                TextEditorControls(
                    textLayer = selectedLayer as TextLayer,
                    onTextColorChange = { viewModel.updateTextColor(selectedLayer.id, it) },
                    onTextSizeChange = {  viewModel.updateTextSize(selectedLayer.id, fontSize = it.toInt())},
                    onFontFamilyChange = { viewModel.updateFontFamily(selectedLayer.id, fontFamily = it)}
                )
            }
        }



        // 🔥 Bottom Toolbar Overlay
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .align(Alignment.BottomCenter)
//                .padding(bottom = 40.dp),
//            horizontalArrangement = Arrangement.Center
//        ) {
//
//            ToolIcon(R.drawable.text_size_svgrepo_com)
//            Spacer(Modifier.width(20.dp))
//            ToolIcon(R.drawable.sticker_add_svgrepo_com)
//            Spacer(Modifier.width(20.dp))
//            ToolIcon(R.drawable.photo_svgrepo_com)
//        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StoryEditorPreview() {

    val viewModel = remember { StatusEditorViewModel() }

    LaunchedEffect(Unit) {
        viewModel.addCenteredText("Hello Story")
        viewModel.addCenteredImage(
            "https://img.freepik.com/free-vector/modern-abstract-dark-violate-pink-background_84443-2784.jpg"
        )
    }

    StatusEditorScreen(viewModel = viewModel)
}
