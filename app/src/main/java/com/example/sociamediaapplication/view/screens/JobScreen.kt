package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.utils.formatPostTime
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.viewmodel.JobViewModel

@Composable
fun JobScreen(
    navController: NavController = rememberNavController(),
    viewModel: JobViewModel = viewModel(),
    onApplyClick: () -> Unit = {}
) {

    val jobDetails by viewModel.jobDetails.collectAsState()

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomStart
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(16.dp)
        ) {

            /* ---------------- HEADER ---------------- */

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(White, RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .background(Color(0xffeef2f7), RoundedCornerShape(14.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.briefcase_svgrepo_com),
                                contentDescription = null,
                                tint = Color(0xff6b7a90)
                            )
                        }

                        Spacer(Modifier.width(12.dp))

                        Column {
                            Text(
                                jobDetails?.data?.title ?: "Senior React Developer",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Text(
                                jobDetails?.data?.display_company_name ?: "TechCorp Inc.",
                                color = Color(0xff6b7a90)
                            )
                        }
                    }

                    Spacer(Modifier.height(14.dp))

                    Row {
                        Box(
                            modifier = Modifier
                                .background(Color(0xffe7f0ff), RoundedCornerShape(50))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            if(jobDetails?.data?.min_salary == null || jobDetails?.data?.max_salary == null){
                                Text("Not Disclosed", color = Color(0xff1d6ef2))
                            }else{

                                Text("${jobDetails?.data?.min_salary} - ${jobDetails?.data?.max_salary }", color = Color(0xff1d6ef2))
                            }
                        }

                        Spacer(Modifier.width(8.dp))

                        Box(
                            modifier = Modifier
                                .border(1.dp, Color(0xffd9e1ec), RoundedCornerShape(50))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text( jobDetails?.data?.job_type ?: "Full-time")
                        }

                        Spacer(Modifier.width(8.dp))

                        Box(
                            modifier = Modifier
                                .border(1.dp, Color(0xffd9e1ec), RoundedCornerShape(50))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(jobDetails?.data?.workplace_type ?: "Remote")
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Icon(
                            painter = painterResource(R.drawable.location_pin_svgrepo_com),
                            contentDescription = null,
                            tint = Color(0xff6b7a90),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(jobDetails?.data?.location ?: "San Francisco, CA", color = Color(0xff6b7a90))

                        Spacer(Modifier.width(16.dp))

                        Icon(
                            painter = painterResource(R.drawable.clock_three_svgrepo_com),
                            contentDescription = null,
                            tint = Color(0xff6b7a90),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(formatPostTime(jobDetails?.data?.created_at?: "2 days ago"), color = Color(0xff6b7a90))
                    }

                    Spacer(Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.team_3),
                            contentDescription = null,
                            tint = Color(0xff6b7a90),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text("${jobDetails?.data?.applications_count ?: "47"} applicants", color = Color(0xff6b7a90))
                    }
                }
            }

            /* ---------------- ABOUT ROLE ---------------- */

            item {
                Spacer(Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {

                    Text(
                        "About the Role",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        jobDetails?.data?.description
                            ?: ("We are looking for a Senior React Developer to join our growing engineering team. " +
                                    "You will be responsible for building and maintaining high-quality web applications."),
                        color = Color(0xff6b7a90)
                    )
                }
            }

            /* ---------------- REQUIREMENTS ---------------- */

            item {
                Spacer(Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {

                    Text(
                        "Requirements",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Spacer(Modifier.height(10.dp))

                    val reqs = jobDetails?.data?.requirements?.split(", ") ?: listOf(
                        "5+ years of experience with React",
                        "Strong TypeScript skills",
                        "Experience with state management (Redux, Zustand)",
                        "Familiarity with REST APIs and GraphQL",
                        "Experience with testing frameworks"
                    )

                    reqs.forEach {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 6.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.tick_svgrepo_com),
                                contentDescription = null,
                                tint = Color(0xff1d6ef2),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(it, color = Color(0xff4b5d73))
                        }
                    }
                }
            }

            /* ---------------- BENEFITS ---------------- */

            item {
                Spacer(Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {

                    Text(
                        "Benefits",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Spacer(Modifier.height(10.dp))

                    val benefits = jobDetails?.data?.benefits?.split(", ") ?: listOf(
                        "Health, dental & vision insurance",
                        "401(k) with company match",
                        "Unlimited PTO",
                        "Remote work flexibility",
                        "Annual learning budget"
                    )

                    benefits.forEach {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 6.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.tick_svgrepo_com),
                                contentDescription = null,
                                tint = Color(0xff22c55e),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(it, color = Color(0xff4b5d73))
                        }
                    }
                }
            }

            /* ---------------- COMPANY INFO ---------------- */

            item {
                Spacer(Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {

                    Text(
                        "About",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        "TechCorp Inc. is a leading technology company specializing in building enterprise-grade SaaS platforms. " +
                                "Founded in 2015, we've grown to a team of 500+ engineers across 12 countries.",
                        color = Color(0xff6b7a90)
                    )

                    Spacer(Modifier.height(16.dp))

                    Row {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .background(Color(0xffeef2f7), RoundedCornerShape(14.dp))
                                .padding(14.dp)
                        ) {
                            Text("Industry", color = Color.Gray, fontSize = 12.sp)
                            Spacer(Modifier.height(4.dp))
                            Text("Software Development")
                        }

                        Spacer(Modifier.width(10.dp))

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .background(Color(0xffeef2f7), RoundedCornerShape(14.dp))
                                .padding(14.dp)
                        ) {
                            Text("Company Size", color = Color.Gray, fontSize = 12.sp)
                            Spacer(Modifier.height(4.dp))
                            Text("500-1000")
                        }
                    }

                    Spacer(Modifier.height(10.dp))

                    Row {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .background(Color(0xffeef2f7), RoundedCornerShape(14.dp))
                                .padding(14.dp)
                        ) {
                            Text("Founded", color = Color.Gray, fontSize = 12.sp)
                            Spacer(Modifier.height(4.dp))
                            Text("2015")
                        }

                        Spacer(Modifier.width(10.dp))

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .background(Color(0xffeef2f7), RoundedCornerShape(14.dp))
                                .padding(14.dp)
                        ) {
                            Text("Website", color = Color.Gray, fontSize = 12.sp)
                            Spacer(Modifier.height(4.dp))
                            Text("techcorp.io", color = Color(0xff1d6ef2))
                        }
                    }
                }
            }

            item { Spacer(Modifier.height(50.dp)) }
        }

        Column(
            modifier = Modifier
                .background(BackgroundColor)
                .height(70.dp)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = onApplyClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Apply Now")
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JobScreenPreview(){
    JobScreen()
}