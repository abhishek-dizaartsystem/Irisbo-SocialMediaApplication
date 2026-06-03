package com.example.sociamediaapplication.view.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadVideoScreen(
    navController: NavController,
    onUploadClick: (
        title: String,
        description: String,
        categoryId: Int,
        thumbnailUri: Uri?,
        videoUri: Uri?
    ) -> Unit = { _, _, _, _, _ -> }
) {

    val context = LocalContext.current

    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    var thumbnailUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var videoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedCategory by remember {
        mutableStateOf("Technology")
    }

    val categories = listOf(
        "Technology",
        "Education",
        "Gaming",
        "Music",
        "Entertainment"
    )

    val thumbnailPicker =
        rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->

            thumbnailUri = uri
        }

    val videoPicker =
        rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->

            videoUri = uri
        }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("Create Video")
                },

                navigationIcon = {

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {

                        Icon(
                            painter = painterResource(
                                R.drawable.back_svgrepo_com
                            ),
                            contentDescription = ""
                        )
                    }
                }
            )
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            LazyColumn() {
                item {
                    TextField(
                        value = title,
                        onValueChange = {
                            title = it
                        },
                        label = {
                            Text("Video Title")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    TextField(
                        value = description,
                        onValueChange = {
                            description = it
                        },
                        label = {
                            Text("Description")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        }
                    ) {

                        TextField(
                            value = selectedCategory,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            label = {
                                Text("Category")
                            }
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = {
                                expanded = false
                            }
                        ) {

                            categories.forEach { category ->

                                DropdownMenuItem(
                                    text = {
                                        Text(category)
                                    },
                                    onClick = {

                                        selectedCategory =
                                            category

                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    Button(
                        onClick = {
                            thumbnailPicker.launch(
                                "image/*"
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Select Thumbnail")
                    }

                    thumbnailUri?.let {

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        AsyncImage(
                            model = it,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    Button(
                        onClick = {
                            videoPicker.launch(
                                "video/*"
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Select Video")
                    }

                    if (videoUri != null) {

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = "Video Selected ✓",
                            color = Blue
                        )
                    }

                    Spacer(
                        modifier = Modifier.weight(1f)
                    )

                    Button(
                        onClick = {

                            onUploadClick(
                                title,
                                description,
                                categories.indexOf(
                                    selectedCategory
                                ) + 1,
                                thumbnailUri,
                                videoUri
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Blue
                        )
                    ) {

                        Text(
                            text = "Upload Video",
                            fontSize = 16.sp
                        )
                    }
                }
            }


        }
    }
}