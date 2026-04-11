package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.utils.correctUrl
import com.example.sociamediaapplication.data.utils.formatToDate
import com.example.sociamediaapplication.data.utils.openUrl
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LBlue
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.LLBlue
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.view.components.HexagonShape
import com.example.sociamediaapplication.viewmodel.JobViewModel

@Composable
fun ApplicantScreen(
    navController: NavController = rememberNavController(),
    viewModel: JobViewModel = viewModel()
){

    val applicationMetadata by viewModel.applicationMetadata.collectAsState()
    val applicantDetails by viewModel.applicant.collectAsState()

    Scaffold(
        topBar = {
            Column() {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BackgroundColor),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
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
                        text = "Applicant Details",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )


                    Text(
                        text = "Create",
                        fontSize = 18.sp,
                        color = Transparent
                    )
                }

                HorizontalDivider(color = LGrey)
            }

        }
    ) {innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(BackgroundColor)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(12.dp)
            ) {
                item{
                    Card(
                        colors = CardDefaults.cardColors(White),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            AsyncImage(
                                model = correctUrl(applicantDetails?.data?.user?.profile_image),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(HexagonShape),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                applicantDetails?.data?.user?.name?:"John Doe",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "Applied on ${formatToDate(applicantDetails?.data?.application?.applied_at?: "Jan 22, 2025")}",
                                color = GreyTxt
                            )
                            Row(
                                modifier = Modifier
                                    .background(LLBlue, RoundedCornerShape(16.dp))
                                    .border(1.dp, LBlue, RoundedCornerShape(16.dp))
                            ) {
                                Text(applicantDetails?.data?.application?.status ?: "Default", color = Blue,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp))
                            }
                        }
                    }
                }

                item {
                    Card(
                        colors = CardDefaults.cardColors(White),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(12.dp)
                        ) {
                            Text(
                                "Change Status",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            FlowRow(
                                modifier = Modifier.padding(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                applicationMetadata?.data?.status?.forEach { item ->
                                    Button(
                                        onClick = {
                                            viewModel.updateApplicationStatus(
                                                applicantDetails?.data?.application?.job_id?: 0,
                                                applicantDetails?.data?.application?.id?:0,
                                                applicantDetails?.data?.application?.applicant_id?:0,
                                                item
                                            )
                                        },
                                        contentPadding = PaddingValues(0.dp),
                                        modifier = Modifier.heightIn(10.dp, 50.dp).widthIn(30.dp, 100.dp),
                                        colors = ButtonDefaults.buttonColors(Transparent)
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .background(
                                                    if(applicantDetails?.data?.application?.status == item)LLBlue else LGrey,
                                                    RoundedCornerShape(16.dp))
                                                .border(
                                                    1.dp,
                                                    if(applicantDetails?.data?.application?.status == item)LBlue else Grey,
                                                    RoundedCornerShape(16.dp))
                                        ) {
                                            Text(
                                                text = item,
                                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                                if(applicantDetails?.data?.application?.status == item)Blue else GreyTxt,
                                            )
                                        }
                                    }


                                }
                            }
                        }
                    }

                }
                item {
                    Card(
                        colors = CardDefaults.cardColors(White),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                "Personal Information",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(R.drawable.email_svgrepo_com),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    applicantDetails?.data?.user?.email ?: "null",
                                    modifier = Modifier.padding(start = 12.dp)
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(R.drawable.call_svgrepo_com),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .rotate(-135f)
                                )
                                Text(
                                    applicantDetails?.data?.user?.phone?:"null",
                                    modifier = Modifier.padding(start = 12.dp)
                                )
                            }
                        }
                    }


                }

                item {
                    Card(
                        colors = CardDefaults.cardColors(White),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                "Professional Details",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 6.dp)
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                ApplicantCard(
                                    painter = painterResource(R.drawable.briefcase_svgrepo_com),
                                    title= "Experience",
                                    value = "${applicantDetails?.data?.application?.experience_years} years",
                                    modifier = Modifier.weight(1f)
                                )
                                ApplicantCard(
                                    painter = painterResource(R.drawable.profile_1341_svgrepo_com),
                                    title= "Current Company",
                                    value = applicantDetails?.data?.application?.current_company ?: "null",
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                ApplicantCard(
                                    painter = painterResource(R.drawable.rupee_sign_svgrepo_com),
                                    title= "Current CTC",
                                    value = applicantDetails?.data?.application?.current_ctc?:"null",
                                    modifier = Modifier.weight(1f)
                                )
                                ApplicantCard(
                                    painter = painterResource(R.drawable.rupee_sign_svgrepo_com),
                                    title= "Expected CTC",
                                    value = applicantDetails?.data?.application?.expected_ctc?:"null",
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            ApplicantCard(
                                painter = painterResource(R.drawable.clock_three_svgrepo_com),
                                title= "Notice Period",
                                value = applicantDetails?.data?.application?.notice_period?:"null",
                                modifier = Modifier.weight(1f)
                            )

                        }
                    }
                }

                item {
                    Card(
                        colors = CardDefaults.cardColors(White),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(12.dp)
                        ) {
                            Text(
                                "Cover Letter",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                applicantDetails?.data?.application?.cover_letter?:"null",
                                color = GreyTxt
                            )

                        }
                    }


                }

                item {
                    Card(
                        colors = CardDefaults.cardColors(White),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        FlowRow(
                            modifier = Modifier.fillMaxWidth().padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                "Links",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 6.dp)
                            )

                            val context = LocalContext.current
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable{
                                    openUrl(context, applicantDetails?.data?.application?.resume_url)
                                }
                                    .border(1.dp, LBlue, RoundedCornerShape(16.dp))
                                    .background(LLBlue, RoundedCornerShape(16.dp))
                            ) {
                                Spacer(modifier = Modifier.width(12.dp))
                                Icon(
                                    painter = painterResource(R.drawable.page_svgrepo_com),
                                    contentDescription = null,
                                    tint = Blue,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    "Resume",
                                    modifier = Modifier.padding(start = 6.dp, end = 12.dp, top = 6.dp, bottom = 6.dp),
                                    color = Blue
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable{
                                    openUrl(context, applicantDetails?.data?.application?.portfolio_url)
                                }
                                    .border(1.dp, LBlue, RoundedCornerShape(16.dp))
                                    .background(LLBlue, RoundedCornerShape(16.dp))
                            ) {
                                Spacer(modifier = Modifier.width(12.dp))
                                Icon(
                                    painter = painterResource(R.drawable.global_svgrepo_com),
                                    contentDescription = null,
                                    tint = Blue,
                                    modifier = Modifier
                                        .size(16.dp)
                                )
                                Text(
                                    "Portfolio",
                                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 6.dp, bottom = 6.dp),
                                    color = Blue
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable{
                                    openUrl(context, applicantDetails?.data?.application?.linkedin_url)
                                }
                                    .border(1.dp, LBlue, RoundedCornerShape(16.dp))
                                    .background(LLBlue, RoundedCornerShape(16.dp))
                            ) {
                                Spacer(modifier = Modifier.width(12.dp))
                                Icon(
                                    painter = painterResource(R.drawable.briefcase_svgrepo_com),
                                    contentDescription = null,
                                    tint = Blue,
                                    modifier = Modifier
                                        .size(16.dp)
                                )
                                Text(
                                    "Linked In",
                                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 6.dp, bottom = 6.dp),
                                    color = Blue
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                }
            }
        }
    }
}

@Composable
fun ApplicantCard(
    painter: Painter = painterResource(R.drawable.briefcase_svgrepo_com),
    title: String = "Experience",
    value: String = "4 years",
    modifier: Modifier
){
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(LGrey)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = GreyTxt
                )
                Text(title, color = GreyTxt, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                value,
                fontSize = 12.sp
            )
        }
    }
}