package com.example.sociamediaapplication.view.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.LLBlue
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.CustomTextField
import com.example.sociamediaapplication.viewmodel.GroupViewModel
import com.example.sociamediaapplication.viewmodel.PageViewModel

@Composable
fun EditPageScreen(
    navController: NavController = rememberNavController(),
    pageId: String = "1",
    viewModel: PageViewModel = viewModel()
){

    val categories = listOf(
        "Technology", "Art and Photography", "Health and Fitness", "Travel",
        "Food and Cooking", "Education", "Music", "Business"
    )

    var selectedCategory by remember { mutableStateOf("Category") }
    var showDropDownMenu by remember { mutableStateOf(false) }

    var isPrivate by remember { mutableStateOf(true) }
    var allowMemberPost by remember { mutableStateOf(false) }
    var isApprovalRequired by remember { mutableStateOf(false) }

    val coverPhoto by viewModel.coverPhoto.collectAsState()
    val pageProfile by viewModel.pageProfile.collectAsState()

    val coverImagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.updateCoverImage(uri)
        }

    }

    val groupProfilePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {uri: Uri?->
        uri?.let{
            viewModel.updatePageProfile(uri)
        }
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BackgroundColor),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.back_svgrepo_com),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Text(
                    text = "Edit Page-$pageId",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Row() {

                    Text(
                        text = "Create",
                        fontSize = 18.sp,
                        color = Transparent
                    )

                }

            }
        }
    ) { innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    color = BackgroundColor
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .aspectRatio(1.7f)
                    ) {
                        IconButton(
                            onClick = {
                                coverImagePicker.launch("image/*")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(2f),
                            shape = RoundedCornerShape(0.dp)
                        ) {
                            when(coverPhoto){
                                is Int->{
                                    Image(
                                        painter = painterResource(coverPhoto as Int),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(2f)
                                            .clip(RoundedCornerShape(12.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                is Uri->{
                                    AsyncImage(
                                        model = coverPhoto as Uri,
                                        contentDescription = "",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(2f)
                                            .clip(RoundedCornerShape(12.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }

                        }
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            IconButton(
                                onClick = {
                                    groupProfilePicker.launch("image/*")
                                },
                                modifier = Modifier
                                    .fillMaxWidth(0.25f)
                                    .padding(start = 20.dp)
                                    .border(
                                        width = 4.dp,
                                        color = Grey,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .aspectRatio(1f)


                                ,
                                shape = RoundedCornerShape(0.dp)
                            ){
                                when(pageProfile){
                                    is Int->{
                                        Image(
                                            painter = painterResource(pageProfile as Int),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .aspectRatio(1f)
                                                .clip(RoundedCornerShape(12.dp)),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                    is Uri->{
                                        AsyncImage(
                                            model = pageProfile as Uri,
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .aspectRatio(1f)
                                                .clip(RoundedCornerShape(12.dp)),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    CustomTextField(
                        "Page name",
                        "travel page",
                        onValueChange = {},
                    )

                }

                item {
                    CustomTextField(
                        "Description",
                        "What is this page about?",
                        onValueChange = {},
                    )

                }

                item {

                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.Bottom,
                    ) {

                        Text(
                            text = "Category",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Column(
                            modifier = Modifier
                                .border(
                                    1.dp,
                                    color = Grey,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .height(50.dp)
                                .clickable{
                                    showDropDownMenu = true
                                },
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                Modifier
                                    .padding(horizontal = 12.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = selectedCategory,
                                    modifier = Modifier
                                )
                                Icon(
                                    painter = painterResource(R.drawable.back_svgrepo_com),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(20.dp)
                                        .rotate(-90f)
                                )
                            }

                        }
                        DropdownMenu(
                            expanded = showDropDownMenu,
                            onDismissRequest = {
                                showDropDownMenu = false
                            },
                            Modifier
                                .height(150.dp)
                                .fillMaxWidth(0.8f),
                            containerColor = White
                        ) {
                            categories.forEach {category->
                                DropdownMenuItem(
                                    text = {
                                        Text(category)
                                    },
                                    onClick = {
                                        selectedCategory = category
                                        showDropDownMenu = false
                                    }
                                )
                                HorizontalDivider()
                            }
                        }

                    }
                }

                item {
                    Card(
                        onClick = {},
                        elevation = CardDefaults.cardElevation(2.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = White
                        )
                    ) {
                        Column(
                            Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                "Contact Information",
                                fontSize = 18.sp
                            )
                            CustomTextField(
                                "Website",
                                "https://yourwebsite.com",
                                onValueChange = {},
                            )
                            CustomTextField(
                                "Phone",
                                "+91 1234567890",
                                onValueChange = {},
                            )
                            CustomTextField(
                                "Email",
                                "contact@yourpage.com",
                                onValueChange = {},
                            )
                            CustomTextField(
                                "Address",
                                "NX1 greater noida near gaurs",
                                onValueChange = {},
                            )

                        }

                    }
                }

                item {
                    Row(
                        Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Blue
                            ),
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Save",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                        Spacer(Modifier.width(12.dp))
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Grey
                            ),
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Discard",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Black
                            )
                        }
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditPageScreenPreview(){
    EditPageScreen()
}