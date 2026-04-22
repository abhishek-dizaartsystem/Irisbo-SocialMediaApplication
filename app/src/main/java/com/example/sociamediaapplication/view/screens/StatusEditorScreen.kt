package com.example.sociamediaapplication.view.screens

import android.graphics.Rect
import android.net.Uri
import android.view.View
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.utils.bitmapToUri
import com.example.sociamediaapplication.data.utils.captureCanvasBitmap
import com.example.sociamediaapplication.data.utils.captureViewBitmap
import com.example.sociamediaapplication.model.editor.TextLayer
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.TransparentBlack
import com.example.sociamediaapplication.view.components.TextEditorControls
import com.example.sociamediaapplication.view.components.ToolIcon
import com.example.sociamediaapplication.view.components.editor.EditorLayerRenderer
import com.example.sociamediaapplication.viewmodel.StatusEditorViewModel
import com.example.sociamediaapplication.viewmodel.StoryViewModel
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset

@Composable
fun StatusEditorScreen(
    viewModel: StatusEditorViewModel = viewModel(),
    storyViewModel: StoryViewModel = viewModel(),
    onUploadSuccess: () -> Unit = {}
) {

    val layers by viewModel.layers.collectAsState()
    val selectedId by viewModel.selectedLayerId.collectAsState()

    val selectedLayer = layers.find { it.id == selectedId }
    val isTextSelected = selectedLayer is TextLayer

    val selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current
    val view = LocalView.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {uri: Uri?->
        uri?.let{
            viewModel.addCenteredImage(it.toString())
        }
    }

    var isDropDownMenu by remember { mutableStateOf(false) }

    val canvasViewRef = remember { mutableStateOf<View?>(null) }

    var canvasBounds by remember { mutableStateOf<Rect?>(null) }

    var isExporting by remember { mutableStateOf(false) }

    var isDrawingMode by remember { mutableStateOf(false) }

    var paths by remember { mutableStateOf(listOf<Pair<Path, Color>>()) }
    var currentPath by remember { mutableStateOf<Path?>(null) }
    var currentColor by remember { mutableStateOf(Color.White) }

    var redraw by remember { mutableStateOf(0) }

    var draggingLayerId by remember { mutableStateOf<String?>(null) }
    var currentDragOffset by remember { mutableStateOf(Offset.Zero) }

    //Helper functions
    fun undoLastPath() {
        if (paths.isNotEmpty()) {
            paths = paths.dropLast(1)
        }
    }

    LaunchedEffect(isExporting) {

        if (isExporting) {

            // 🔥 Wait for UI to disappear
            kotlinx.coroutines.delay(100)

            val rootView = view
            val rect = canvasBounds

            if (rect != null) {

                val bitmap = captureCanvasBitmap(rootView, rect)
                val uri = bitmapToUri(context, bitmap)

                storyViewModel.createStory(
                    context = context,
                    imageUri = uri,
                    onSuccess = {
                        isExporting = false
                        onUploadSuccess()
                    }
                )
            }
        }
    }

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
                    viewModel.setCanvasSize(it)
                }
                .pointerInput(isDrawingMode) {

                    if (isDrawingMode) {

                        detectDragGestures(
                            onDragStart = { offset ->
                                currentPath = Path().apply {
                                    moveTo(offset.x, offset.y)
                                }
                            },
                            onDrag = { change, _ ->
                                change.consume()

                                currentPath?.lineTo(change.position.x, change.position.y)

                                redraw++   // 🔥 FORCE recomposition EVERY frame
                            },
                            onDragEnd = {
                                currentPath?.let {
                                    paths = paths + (it to currentColor)
                                }
                                currentPath = null
                            }
                        )

                    } else {

                        detectTapGestures {
                            viewModel.selectLayer(null)
                        }
                    }
                }
                .onGloballyPositioned {coords ->
                    val position = coords.positionInWindow()
                    val size = coords.size

                    canvasBounds = android.graphics.Rect(
                        position.x.toInt(),
                        position.y.toInt(),
                        (position.x + size.width).toInt(),
                        (position.y + size.height).toInt()
                    )   // 🔥 reference root, we'll crop later
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
                        },
                        onDragStart = { id ->
                            draggingLayerId = id
                        },

                        onDrag = { offset ->
                            currentDragOffset = offset
                        },

                        onDragEnd = { id, offset ->

                            val screenHeight = view.height

                            val isInDeleteZone = offset.y > screenHeight * 0.75f

                            if (isInDeleteZone) {
                                viewModel.removeLayer(id)   // 🔥 DELETE HERE
                            }

                            draggingLayerId = null
                        }
                    )
                }

            Canvas(modifier = Modifier.fillMaxSize()) {

                redraw

                // Draw previous paths
                paths.forEach { (path, color) ->
                    drawPath(
                        path = path,
                        color = color,
                        style = Stroke(width = 8f)
                    )
                }

                // Draw current path
                currentPath?.let {
                    drawPath(
                        path = it,
                        color = currentColor,
                        style = Stroke(width = 8f)
                    )
                }
            }
        }

        if(!isExporting){
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
                        icon = R.drawable.menu_dots_svgrepo_com,
                        rotate = 90f,
                        onClick = {
                            isDropDownMenu = true
                        }
                    )
                    ToolIcon(
                        icon = R.drawable.text_size_svgrepo_com,
                        onClick = {
                            viewModel.addCenteredText("Text")
                        }
                    )

                    ToolIcon(R.drawable.edit_1_svgrepo_com,
                        onClick = {
                            isDrawingMode = !isDrawingMode
                        }
                    )
                    ToolIcon(R.drawable.sticker_add_svgrepo_com,
                        onClick = {}
                    )
                    ToolIcon(R.drawable.photo_svgrepo_com,
                        onClick = {
                            imagePickerLauncher.launch("image/*")
                        }
                    )
                    ToolIcon(
                        icon = R.drawable.music_svgrepo_com,
                        onClick = {}
                    )

                }


                if (isDrawingMode) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, start = 24.dp, end = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        ToolIcon(
                            icon = R.drawable.undo_left_round_svgrepo_com, // use any undo icon
                            onClick = {
                                undoLastPath()
                            }
                        )
                    }

                }

                DropdownMenu(
                    expanded = isDropDownMenu,
                    onDismissRequest = {
                        isDropDownMenu = false
                    }
                ) {
                    DropdownMenuItem(
                        text = { Text("Save") },
                        onClick = {
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.download_svgrepo_com),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        modifier = Modifier.height(30.dp)
                    )
                    HorizontalDivider()
                    DropdownMenuItem(
                        text = { Text("Mention") },
                        onClick = {
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.at_sign_svgrepo_com),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        modifier = Modifier.height(30.dp)
                    )
                    HorizontalDivider()
                    DropdownMenuItem(
                        text = { Text("Hashtag") },
                        onClick = {
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.hashtag_svgrepo_com),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        modifier = Modifier.height(30.dp)
                    )

                }

                if (isTextSelected) {
                    isDropDownMenu = false
                    TextEditorControls(
                        textLayer = selectedLayer as TextLayer,
                        onTextColorChange = { viewModel.updateTextColor(selectedLayer.id, it) },
                        onTextSizeChange = {  viewModel.updateTextSize(selectedLayer.id, fontSize = it.toInt())},
                        onFontFamilyChange = { viewModel.updateFontFamily(selectedLayer.id, fontFamily = it)}
                    )
                }
            }



            // 🔥 Bottom Toolbar Overlay
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 40.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                if (draggingLayerId != null) {
                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(36.dp)
                            .background(Color.Red, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.delete_svgrepo_com),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Button(
                    onClick = {
                        isExporting = true

//                        val rootView = view
//                        val rect = canvasBounds
//
//                        if (rect != null) {
//
//                            val bitmap = captureCanvasBitmap(rootView, rect)
//                            val uri = bitmapToUri(context, bitmap)
//
//                            storyViewModel.createStory(
//                                context = context,
//                                imageUri = uri,
//                                onSuccess = {
//                                    onUploadSuccess()
//                                }
//                            )
//                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = TransparentBlack
                    ),
                    contentPadding = PaddingValues(vertical = 0.dp, horizontal = 16.dp),

                    ) {
                    Text(
                        text = "Upload Status",
                        fontSize = 16.sp
                    )
                }
            }
        }


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
