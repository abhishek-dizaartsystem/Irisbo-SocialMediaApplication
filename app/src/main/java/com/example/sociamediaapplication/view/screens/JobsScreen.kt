package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.utils.formatToDate
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.ApplicationItem
import com.example.sociamediaapplication.view.components.JobItem
import com.example.sociamediaapplication.viewmodel.JobViewModel

@Composable
fun JobsScreen(
    navController: NavController = rememberNavController(),
    viewModel: JobViewModel = viewModel(),
    onJobClick: (Int) -> Unit = {},
    onRecruiterClick: () -> Unit = {},
    onApplyClick: (Int) -> Unit = {}
){

    val publicJobs by viewModel.publicJobs.collectAsState()
    val savedJobs by viewModel.savedJobs.collectAsState()
    val myApplications by viewModel.myApplications.collectAsState()

    var searchTxt by remember { mutableStateOf("") }

    var optionSelected by remember { mutableStateOf("Browse") }

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
                        text = "Jobs",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Button(
                        onClick = onRecruiterClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Blue),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .height(34.dp)
                            .width(80.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "Create",
                            fontSize = 18.sp,
                            color = White
                        )
                    }







                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextField(
                        value = searchTxt,
                        onValueChange = {newMessage->
                            searchTxt = newMessage
                        },
                        placeholder = {
                            Text(
                                text = "Search Jobs, Companies...",
                                color = GreyTxt
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Transparent,
                            unfocusedIndicatorColor = Transparent,
                            disabledIndicatorColor = Transparent,
                            unfocusedContainerColor = LGrey,
                            focusedContainerColor = LGrey
                        ),
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .height(54.dp)
                            .fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.search_svgrepo_com),
                                contentDescription = "",
                                modifier = Modifier.size(24.dp),
                                tint = GreyTxt
                            )
                        }
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    color = BackgroundColor
                )
        ) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                item{
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                optionSelected = "Browse"
                            },
                            modifier = Modifier.fillMaxWidth(0.33f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Transparent
                            ),
                            shape = RoundedCornerShape(0.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "Browse",
                                fontSize = 16.sp,
                                color = if (optionSelected == "Browse") Black else GreyTxt
                            )


                        }
                        Button(
                            onClick = {
                                optionSelected = "Saved"
                            },
                            modifier = Modifier.fillMaxWidth(0.5f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Transparent
                            ),
                            shape = RoundedCornerShape(0.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "Saved",
                                fontSize = 16.sp,
                                color = if (optionSelected == "Saved") Black else GreyTxt
                            )


                        }
                        Button(
                            onClick = {
                                optionSelected = "Applications"
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Transparent
                            ),
                            shape = RoundedCornerShape(0.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "Applications",
                                fontSize = 16.sp,
                                color = if (optionSelected == "Applications") Black else GreyTxt
                            )


                        }
                    }
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth(0.33f)
                                .height(1.dp)
                                .background(
                                    color = if (optionSelected == "Browse") Blue else Transparent
                                )
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .height(1.dp)
                                .background(
                                    color = if (optionSelected == "Saved") Blue else Transparent
                                )
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(
                                    color = if (optionSelected == "Applications") Blue else Transparent
                                )
                        )
                    }

                }
                if(optionSelected == "Browse"){
                    items(publicJobs?.data ?: emptyList()){job->
                        JobItem(
                            position = job.title,
                            companyName = job.display_company_name,
                            location = job.location,
                            jobType = job.job_type,
                            postedSince = formatToDate(job.created_at),
                            salary = "${job.min_salary} - ${job.max_salary} ${job.salary_currency}",
                            officeType = job.workplace_type,
                            isSaved = if(job.is_saved == 1) true else false,
                            isApplied = if(job.has_applied == 1) true else false,
                            onJobClick = { onJobClick(job.id) },
                            onApplyClick = { onApplyClick(job.id) },
                            onSaveToggle = {
                                viewModel.saveJobToggle(
                                    job.id,
                                    job.is_saved == 1
                                )

                            }
                        )
                    }
                }
                else if (optionSelected == "Saved"){
                    items(savedJobs?.data ?: emptyList()){job->
                        JobItem(
                            position = job.title,
                            companyName = job.display_company_name,
                            location = job.location,
                            jobType = job.job_type,
                            postedSince = formatToDate(job.created_at),
                            salary = "${job.min_salary} - ${job.max_salary} ${job.salary_currency}",
                            officeType = job.workplace_type,
                            isSaved = true,
                            onJobClick = { onJobClick(job.id) },
                        ) { onApplyClick(job.id) }
                    }
                }
                else{
                    items(myApplications?.data ?: emptyList()) {application->
                        ApplicationItem(
                            position = application.title,
                            companyName = application.display_company_name,
                            appliedOn = formatToDate(application.applied_at)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JobsScreenPreview(){
    JobsScreen()
}