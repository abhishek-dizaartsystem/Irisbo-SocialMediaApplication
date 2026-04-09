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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.utils.convertToBackendFormat
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.CustomTextField
import com.example.sociamediaapplication.viewmodel.EventViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventScreen(
    navController: NavController = rememberNavController(),
    viewModel: EventViewModel
){


    val categories by viewModel.categories.collectAsState()

    var selectedCategory by remember { mutableStateOf("Category") }
    var selectedCategoryId by remember { mutableStateOf(0) }
    var showDropDownMenu by remember { mutableStateOf(false) }

    var isPrivate by remember { mutableStateOf(true) }
    var allowMemberPost by remember { mutableStateOf(false) }
    var isApprovalRequired by remember { mutableStateOf(false) }
    var isVirtualEvent by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }


    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }


    val coverPhoto by viewModel.coverPhoto.collectAsState()

    val context = LocalContext.current.applicationContext

    val coverImagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.updateCoverImage(uri)
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
                    text = "Create Event",
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

                            AsyncImage(
                                model = coverPhoto ?: R.drawable.cover_image_placeholder,
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

                item {
                    CustomTextField(
                        label = "Event name",
                        value = name,
                        placeHolder = "Event Name",
                        onValueChange = {name = it},
                    )

                }

                item {
                    Row() {
                        CustomTextField(
                            label = "Date",
                            value = selectedDate,
                            placeHolder = "dd-mm-yyyy",
                            onValueChange = {selectedDate = it},
                            modifier = Modifier
                                .weight(1f)
                                .clickable { showDatePicker = true },
                            enabled = false
                        )
                        Spacer(Modifier.width(8.dp))
                        CustomTextField(
                            label = "Starts at",
                            value = selectedTime,
                            onValueChange = {selectedTime = it},
                            modifier = Modifier
                                .weight(1f)
                                .clickable { showTimePicker = true },
                            enabled = false
                        )
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

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier
                                ) {
                                    Text(
                                        text = "Virtual Event",
                                        color = Black,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = "This event will be held virtually",
                                        color = GreyTxt
                                    )
                                }
                            }
                            Switch(
                                checked = isVirtualEvent,
                                onCheckedChange = {
                                    isVirtualEvent = it
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = White,
                                    checkedTrackColor = Blue,
                                    uncheckedThumbColor = White,
                                    uncheckedTrackColor = Grey,
                                    uncheckedBorderColor = Grey,
                                )
                            )
                        }

                        Spacer(
                            modifier = Modifier
                                .height(1.dp)
                                .fillMaxWidth()
                                .background(color = Grey)
                        )


                    }
                }

                if(!isVirtualEvent){
                    item {
                        CustomTextField(
                            label = "Add address",
                            value = location,
                            onValueChange = {location = it},
                        )

                    }
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
                                .clickable {
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
                            categories?.categories?.forEach {category->
                                DropdownMenuItem(
                                    text = {
                                        Text(category.name)
                                    },
                                    onClick = {
                                        selectedCategory = category.name
                                        selectedCategoryId = category.id
                                        showDropDownMenu = false
                                    }
                                )
                                HorizontalDivider()
                            }
                        }

                    }
                }

                item {
                    CustomTextField(
                        label = "What is this event about?",
                        value = description,
                        onValueChange = {description = it},
                    )

                }


                item {
                    Button(
                        onClick = {
                            viewModel.createEvent(
                                context,
                                title = name,
                                description = description,
                                location_name = if(isVirtualEvent) "" else location,
                                start_time = convertToBackendFormat(selectedDate, selectedTime),
//                                end_time = "",
                                cover_image_uri = coverPhoto?: "".toUri(),
                                category_id = selectedCategoryId
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Blue
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Create Event",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            }
            if (showDatePicker) {

                val datePickerState = rememberDatePickerState()

                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        Button(onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                val sdf = java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault())
                                selectedDate = sdf.format(java.util.Date(millis))
                            }
                            showDatePicker = false
                        }) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDatePicker = false }) {
                            Text("Cancel")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }
            if (showTimePicker) {

                val timeState = rememberTimePickerState(
                    initialHour = 12,
                    initialMinute = 0,
                    is24Hour = false
                )

                AlertDialog(
                    onDismissRequest = { showTimePicker = false },
                    confirmButton = {
                        Button(onClick = {
                            val hour = timeState.hour
                            val minute = timeState.minute
                            selectedTime = String.format("%02d:%02d", hour, minute)
                            showTimePicker = false
                        }) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showTimePicker = false }) {
                            Text("Cancel")
                        }
                    },
                    text = {
                        TimePicker(state = timeState)
                    }
                )
            }


        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateEventScreenPreview(){
    CreateEventScreen(
        viewModel = viewModel()
    )
}