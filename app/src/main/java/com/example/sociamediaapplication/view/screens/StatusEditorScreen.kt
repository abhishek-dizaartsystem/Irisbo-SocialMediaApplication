package com.example.sociamediaapplication.view.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sociamediaapplication.model.editor.ImageLayer
import com.example.sociamediaapplication.model.editor.TextLayer
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.view.components.editor.EditorLayerRenderer
import com.example.sociamediaapplication.viewmodel.StatusEditorViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.sociamediaapplication.ui.theme.Blue

@Composable
fun StatusEditorScreen( viewModel: StatusEditorViewModel = viewModel() ) {

    val layers by viewModel.layers.collectAsState()
    val selectedId by viewModel.selectedLayerId.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Blue)
            .onSizeChanged {
                viewModel.setCanvasSize(it)
            }
    ) {
        layers.sortedBy {
            it.zIndex
        }.forEach { layer ->
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
        viewModel.addCenteredText("Hello Story")
        viewModel.addCenteredImage("https://img.freepik.com/free-vector/modern-abstract-dark-violate-pink-background_84443-2784.jpg?semt=ais_user_personalization&w=740&q=80")
    }
    StatusEditorScreen(viewModel = viewModel)
}