package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.view.components.CustomTextField
import com.example.sociamediaapplication.viewmodel.JobViewModel

@Composable
fun JobApplyScreen(
    navController: NavController = rememberNavController(),
    viewModel: JobViewModel = viewModel(),
    id: Int? = 0
) {

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    var resumeUrl by remember { mutableStateOf("") }
    var coverLetter by remember { mutableStateOf("") }

    var experience by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }
    var currentCtc by remember { mutableStateOf("") }
    var expectedCtc by remember { mutableStateOf("") }

    var portfolio by remember { mutableStateOf("") }
    var linkedin by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    var noticePeriod by remember { mutableStateOf("Select notice period") }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier.background(BackgroundColor)
            ) {

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(R.drawable.back_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Text(
                        text = "Apply for Job",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Text(
                        text = "Create",
                        fontSize = 18.sp,
                        color = Transparent
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Spacer(
                    modifier = Modifier
                        .background(color = Grey)
                        .height(1.dp)
                        .fillMaxWidth()
                )
            }
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // ---------------- Personal Info ----------------
            item { SectionTitle("Personal Information") }

            item {
                CustomTextField(
                    label = "Full Name *",
                    value = fullName,
                    placeHolder = "John Doe",
                    onValueChange = { fullName = it }
                )
            }

            item {
                CustomTextField(
                    label = "Email *",
                    value = email,
                    placeHolder = "you@example.com",
                    onValueChange = { email = it },
                    keyboardType = KeyboardType.Email
                )
            }

            item {
                CustomTextField(
                    label = "Phone *",
                    value = phone,
                    placeHolder = "1234567890",
                    onValueChange = { phone = it },
                    keyboardType = KeyboardType.Phone
                )
            }

            item { DividerLine() }

            // ---------------- Resume ----------------
            item { SectionTitle("Resume & Cover Letter") }

            item {
                CustomTextField(
                    label = "Resume URL *",
                    value = resumeUrl,
                    placeHolder = "https://example.com/resume.pdf",
                    onValueChange = { resumeUrl = it },
                    keyboardType = KeyboardType.Uri
                )
            }

            item {
                CustomMultiLineField(
                    label = "Cover Letter",
                    value = coverLetter,
                    placeHolder = "Tell us why you're a great fit...",
                    onValueChange = { coverLetter = it }
                )
            }

            item { DividerLine() }

            // ---------------- Professional ----------------
            item { SectionTitle("Professional Details") }

            item {
                CustomTextField(
                    label = "Experience (Years)",
                    value = experience,
                    placeHolder = "e.g. 1.5",
                    onValueChange = { experience = it },
                    keyboardType = KeyboardType.Number
                )
            }

            item {
                CustomTextField(
                    label = "Current Company",
                    value = company,
                    placeHolder = "Company name",
                    onValueChange = { company = it }
                )
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CustomTextField(
                        label = "Current CTC (₹)",
                        value = currentCtc,
                        placeHolder = "500000",
                        onValueChange = { currentCtc = it },
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.weight(1f)
                    )

                    CustomTextField(
                        label = "Expected CTC (₹)",
                        value = expectedCtc,
                        placeHolder = "900000",
                        onValueChange = { expectedCtc = it },
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Column {

                    Text(
                        text = "Notice Period",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Box {
                        TextField(
                            value = noticePeriod,
                            onValueChange = {},
                            enabled = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .clickable { expanded = true },
                            colors = TextFieldDefaults.colors(
                                disabledContainerColor = LGrey,
                                disabledTextColor = Black,
                                disabledIndicatorColor = Transparent
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            listOf("15 Days", "1 Month", "2 Months", "Immediate")
                                .forEach {
                                    DropdownMenuItem(
                                        text = { Text(it) },
                                        onClick = {
                                            noticePeriod = it
                                            expanded = false
                                        }
                                    )
                                }
                        }
                    }
                }
            }

            item { DividerLine() }

            // ---------------- Links ----------------
            item { SectionTitle("Links") }

            item {
                CustomTextField(
                    label = "Portfolio URL",
                    value = portfolio,
                    placeHolder = "https://portfolio.example.com",
                    onValueChange = { portfolio = it },
                    keyboardType = KeyboardType.Uri
                )
            }

            item {
                CustomTextField(
                    label = "LinkedIn URL",
                    value = linkedin,
                    placeHolder = "https://linkedin.com/in/yourname",
                    onValueChange = { linkedin = it },
                    keyboardType = KeyboardType.Uri
                )
            }

            item { DividerLine() }

            item{
                Button(
                    onClick = {
                        viewModel.applyToJob(
                            id?:0,
                            fullName,
                            email,
                            phone,
                            resumeUrl,
                            coverLetter,
                            experience.toFloat(),
                            company,
                            currentCtc.toInt(),
                            expectedCtc.toInt(),
                            noticePeriod,
                            portfolio,
                            linkedin
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Blue
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Apply Now")
                }
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun NoticePeriodDropdown() {

    var expanded by remember { mutableStateOf(false) }
    var noticePeriod by remember { mutableStateOf("Select notice period") }

    Column {

        Text(
            text = "Notice Period",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(6.dp))

        Box {
            TextField(
                value = noticePeriod,
                onValueChange = {},
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clickable { expanded = true },
                colors = TextFieldDefaults.colors(
                    disabledContainerColor = LGrey,
                    disabledTextColor = Black,
                    disabledIndicatorColor = Transparent
                ),
                shape = RoundedCornerShape(16.dp)
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listOf("15 Days", "1 Month", "2 Months", "Immediate")
                    .forEach {
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                noticePeriod = it
                                expanded = false
                            }
                        )
                    }
            }
        }
    }
}

@Composable
fun CustomMultiLineField(
    label: String,
    value: String,
    placeHolder: String,
    onValueChange: (String) -> Unit
) {
    Column {

        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(6.dp))

        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeHolder) },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Transparent,
                unfocusedIndicatorColor = Transparent,
                focusedContainerColor = LGrey,
                unfocusedContainerColor = LGrey
            ),
            shape = RoundedCornerShape(16.dp),
            maxLines = 5
        )
    }
}

@Composable
fun DividerLine() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Grey)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JobApplyScreenPreview(){
    JobApplyScreen()
}