package com.example.sociamediaapplication.view.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.data.utils.correctUrl
import com.example.sociamediaapplication.model.request.EditProfileRequest
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.LBlue
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.ProfileTextField
import com.example.sociamediaapplication.viewmodel.ProfileViewModel

@Composable
fun EditProfileScreen(
    navController: NavController,
    onSave: (EditProfileRequest) -> Unit = {},
    profileViewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){

    val profile by profileViewModel.profile.collectAsState()

    /* ------------------ FORM STATES ------------------ */

    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var work by remember { mutableStateOf("") }
    var education by remember { mutableStateOf("") }
    var phone: String? by remember { mutableStateOf("") }

    var profileUri by remember { mutableStateOf<Uri?>(null) }
    var coverUri by remember { mutableStateOf<Uri?>(null) }

    /* 🔥 Auto-fill when profile loads */
    LaunchedEffect(profile) {
        profile?.let {
            name = it.data.name
            username = it.data.username
            bio = it.data.bio ?: "null"
            work = it.data.work ?: "null"
            education = it.data.education ?: "null"
            phone = it.data.phone
        }
    }

    /* ------------------ IMAGE PICKERS ------------------ */

    val profilePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileUri = uri
    }

    val coverPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        coverUri = uri
    }

    /* ------------------ UI ------------------ */

    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(BackgroundColor)) {

                Spacer(Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(R.drawable.back_svgrepo_com),
                            contentDescription = null
                        )
                    }

                    Text(
                        text = "Edit Profile",
                        fontSize = 18.sp
                    )

                    Button(
                        onClick = {
                            val request = EditProfileRequest(
                                name = name,
                                username = username,
                                bio = bio,
                                work = work,
                                education = education,
                                profileUri = profileUri,
                                coverUri = coverUri,
                                phone = phone
                            )
                            onSave(request)
                        },
                        contentPadding = PaddingValues(4.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Blue)
                    ) {
                        Text("Save", fontSize = 18.sp)
                    }
                }

                Spacer(Modifier.height(6.dp))
                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Grey)
                )
            }
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(BackgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item { Spacer(Modifier.height(12.dp)) }

            /* ------------------ PROFILE IMAGE ------------------ */

            item {

                Box(contentAlignment = Alignment.BottomEnd) {

                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(138.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = LBlue
                        )
                    ) {
                        val profileImage =  if(profileUri == null) {
                            if (profile?.data?.profile_image == null)
                                R.drawable.profile_image_placeholder
                            else
                                correctUrl(profile?.data?.profile_image)
                        }else{
                            profileUri
                        }
                        AsyncImage(
                            model = profileImage,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(130.dp)
                                .clip(CircleShape)
                        )
                    }

                    IconButton(
                        onClick = { profilePicker.launch("image/*") },
                        modifier = Modifier.size(40.dp),
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Blue)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.camera_svgrepo_com),
                            contentDescription = null,
                            tint = White
                        )
                    }
                }

                Text(
                    text = "Change Profile Photo",
                    modifier = Modifier.padding(vertical = 6.dp),
                    color = Blue
                )
            }

            /* ------------------ COVER IMAGE ------------------ */

            item {

                Spacer(Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {

                    Text("Cover Photo")

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.BottomEnd
                    ) {

                        val coverImage =  if(coverUri == null) {
                            if (profile?.data?.cover_img == null)
                                R.drawable.cover_image_placeholder
                            else
                                correctUrl(profile?.data?.cover_img)
                        }else{
                            coverUri
                        }
                        AsyncImage(
                            model = coverImage,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(2f)
                        )

                        IconButton(
                            onClick = { coverPicker.launch("image/*") },
                            colors = IconButtonDefaults.iconButtonColors(containerColor = Blue)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.camera_svgrepo_com),
                                contentDescription = null,
                                tint = White
                            )
                        }
                    }

                    Spacer(Modifier.height(12.dp))
                }
            }

            /* ------------------ FORM FIELDS ------------------ */

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {

                    ProfileTextField("Name", name) { name = it }
                    ProfileTextField("Username", username) { username = it }
                    ProfileTextField("Bio", bio) { bio = it }
                    ProfileTextField("Work", work) { work = it }
                    ProfileTextField("Education", education) { education = it }
                    ProfileTextField("Phone", phone) { phone = it}

                    Spacer(Modifier.height(20.dp))
                }
            }
        }
    }
}

/* ---------- SMALL HELPER (UI ONLY) ---------- */



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditProfileScreenPreview(){
    EditProfileScreen(
        navController = rememberNavController()
    )
}