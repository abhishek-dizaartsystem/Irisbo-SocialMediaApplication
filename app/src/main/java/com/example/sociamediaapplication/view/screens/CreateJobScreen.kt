package com.example.sociamediaapplication.view.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
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
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.CustomTextField
import com.example.sociamediaapplication.viewmodel.JobViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateJobScreen(
    viewModel: JobViewModel = viewModel(),
    navController: NavController = rememberNavController()
) {

    val metadata by viewModel.jobMetadata.collectAsState()

    val workplaceOptions = metadata?.data?.workplace_type ?: emptyList()
    val jobTypeOptions = metadata?.data?.job_type ?: emptyList()
    val experienceOptions = metadata?.data?.experience_level ?: emptyList()

    var title by remember { mutableStateOf("") }
    var companyName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var responsibilities by remember { mutableStateOf("") }
    var requirements by remember { mutableStateOf("") }
    var benefits by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    var workplaceType by remember { mutableStateOf("Select Workplace Type") }
    var jobType by remember { mutableStateOf("Select Job Type") }
    var experienceLevel by remember { mutableStateOf("Select Experience Level") }

    var showWorkplaceDropDown by remember { mutableStateOf(false) }
    var showJobTypeDropDown by remember { mutableStateOf(false) }
    var showExperienceDropDown by remember { mutableStateOf(false) }

    var minSalary by remember { mutableStateOf("") }
    var maxSalary by remember { mutableStateOf("") }
    var currency by remember { mutableStateOf("INR") }
    var vacancies by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(BackgroundColor)
            ) {

                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
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
                        text = "Post Job",
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item { CustomTextField(label = "Job Title", value = title, placeHolder = "Enter job title", onValueChange = { title = it }) }
            item { CustomTextField(label = "Company Name", value = companyName, placeHolder = "Enter company name", onValueChange = { companyName = it }) }
            item { CustomTextField(label = "Description", value = description, placeHolder = "Enter description", onValueChange = { description = it }) }
            item { CustomTextField(label = "Responsibilities", value = responsibilities, placeHolder = "A, B, C", onValueChange = { responsibilities = it }) }
            item { CustomTextField(label = "Requirements", value = requirements, placeHolder = "Enter requirements", onValueChange = { requirements = it }) }
            item { CustomTextField(label = "Benefits", value = benefits, placeHolder = "P, Q, R", onValueChange = { benefits = it }) }
            item { CustomTextField(label = "Location", value = location, placeHolder = "Enter location", onValueChange = { location = it }) }

            // 🔽 Workplace Dropdown
            item {
                DropdownLikeField(
                    label = "Workplace Type",
                    selected = workplaceType,
                    expanded = showWorkplaceDropDown,
                    onExpand = { showWorkplaceDropDown = true },
                    onDismiss = { showWorkplaceDropDown = false },
                    options = workplaceOptions,
                    onSelect = {
                        workplaceType = it
                        showWorkplaceDropDown = false
                    }
                )
            }

            // 🔽 Job Type Dropdown
            item {
                DropdownLikeField(
                    label = "Job Type",
                    selected = jobType,
                    expanded = showJobTypeDropDown,
                    onExpand = { showJobTypeDropDown = true },
                    onDismiss = { showJobTypeDropDown = false },
                    options = jobTypeOptions,
                    onSelect = {
                        jobType = it
                        showJobTypeDropDown = false
                    }
                )
            }

            // 🔽 Experience Dropdown
            item {
                DropdownLikeField(
                    label = "Experience Level",
                    selected = experienceLevel,
                    expanded = showExperienceDropDown,
                    onExpand = { showExperienceDropDown = true },
                    onDismiss = { showExperienceDropDown = false },
                    options = experienceOptions,
                    onSelect = {
                        experienceLevel = it
                        showExperienceDropDown = false
                    }
                )
            }

            item { CustomTextField(label = "Min Salary", value = minSalary, placeHolder = "400000", keyboardType = KeyboardType.Number, onValueChange = { minSalary = it }) }
            item { CustomTextField(label = "Max Salary", value = maxSalary, placeHolder = "800000", keyboardType = KeyboardType.Number, onValueChange = { maxSalary = it }) }

            item { CustomTextField(label = "Currency", value = currency, placeHolder = "INR", onValueChange = { currency = it }) }
            item { CustomTextField(label = "Vacancies", value = vacancies, placeHolder = "1", keyboardType = KeyboardType.Number, onValueChange = { vacancies = it }) }

            item { CustomTextField(label = "Application Deadline", value = deadline, placeHolder = "YYYY-MM-DD", onValueChange = { deadline = it }) }

            item {
                Button(
                    onClick = {
                        viewModel.createJob(
                            title,
                            companyName,
                            description,
                            responsibilities,
                            requirements,
                            benefits,
                            location,
                            workplaceType,
                            jobType,
                            experienceLevel,
                            minSalary.toIntOrNull() ?: 0,
                            maxSalary.toIntOrNull() ?: 0,
                            currency,
                            vacancies.toIntOrNull() ?: 0,
                            deadline
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("Post Job", fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun DropdownLikeField(
    label: String,
    selected: String,
    expanded: Boolean,
    onExpand: () -> Unit,
    onDismiss: () -> Unit,
    options: List<String>,
    onSelect: (String) -> Unit
) {
    Column {
        Text(text = label, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(6.dp))

        Column(
            modifier = Modifier
                .border(1.dp, Grey, RoundedCornerShape(16.dp))
                .height(50.dp)
                .clickable { onExpand() },
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = selected)
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
            expanded = expanded,
            onDismissRequest = onDismiss,
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth(0.8f),
            containerColor = White
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = { onSelect(option) }
                )
                HorizontalDivider()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCreateJobScreen() {
    CreateJobScreen()
}