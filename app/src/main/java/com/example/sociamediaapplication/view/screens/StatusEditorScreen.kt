package com.example.sociamediaapplication.view.screens
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.DTransparentBlack
import com.example.sociamediaapplication.ui.theme.TransparentBlack

@Composable
fun StatusEditorScreen( viewModel: StatusEditorViewModel = viewModel() ) {

    val layers by viewModel.layers.collectAsState()
    val selectedId by viewModel.selectedLayerId.collectAsState()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(30.dp)
                        .background(
                            color = TransparentBlack,
                            shape = CircleShape
                        ),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.text_size_svgrepo_com),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp)

                    )
                }
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(30.dp)
                        .background(
                            color = TransparentBlack,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.music_svgrepo_com),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp)
                        )
                }
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(30.dp)
                        .background(
                            color = TransparentBlack,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.edit_1_svgrepo_com),
                        contentDescription = "",
                        modifier = Modifier.size(28.dp)
                    )
                }
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(30.dp)
                        .background(
                            color = TransparentBlack,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.sticker_add_svgrepo_com),
                        contentDescription = "",
                        modifier = Modifier.size(28.dp)
                    )
                }
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(30.dp)
                        .background(
                            color = TransparentBlack,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.photo_svgrepo_com),
                        contentDescription = "",
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    ) {innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    color = Blue,
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .onSizeChanged {
                        Log.d("EDITOR_DEBUG", "Canvas size: $it")
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