package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.view.components.editor.EditorLayerRenderer
import com.example.sociamediaapplication.viewmodel.StatusEditorViewModel

@Composable
fun StatusEditorScreen(
    viewModel: StatusEditorViewModel = viewModel()
) {
    val layers by viewModel.layers.collectAsState()
    val selectedId by viewModel.selectedLayerId.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        viewModel.selectLayer(null)
                    }
                )
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
                    }
                )
            }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StoryEditorPreview() {

    val viewModel = remember { StatusEditorViewModel() }

    LaunchedEffect(Unit) {
        val id1 = viewModel.generateId()
        val id2 = viewModel.generateId()

        viewModel.addLayer(
            com.example.sociamediaapplication.model.editor.ImageLayer(
                id = id1,
                imageUri = "https://picsum.photos/400"
            )
        )

        viewModel.addLayer(
            com.example.sociamediaapplication.model.editor.TextLayer(
                id = id2,
                text = "Hello Story",
                textColor = 0xFFFFFFFF,
                textSize = 24f
            )
        )
    }

    StatusEditorScreen(viewModel = viewModel)
}
