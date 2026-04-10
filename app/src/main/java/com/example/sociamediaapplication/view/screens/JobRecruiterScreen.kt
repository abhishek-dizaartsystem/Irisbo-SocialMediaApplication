package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.utils.formatToDate
import com.example.sociamediaapplication.ui.theme.BackgroundColor
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.viewmodel.JobViewModel

// -------------------- DATA MODEL --------------------
data class Job(
    val title: String,
    val company: String,
    val location: String,
    val postedAt: String,
    val status: String,
    val views: String,
    val applicants: String
)

// -------------------- MAIN SCREEN --------------------
@Composable
fun JobRecruiterScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: JobViewModel = viewModel(),
    onCreateClick: ()-> Unit = {}
) {

    val myJobs by viewModel.myJobs.collectAsState()

    val jobList = myJobs?.data ?: emptyList()

    val totalJobs = jobList.size

    val activeJobs = jobList.count { it.status.equals("open", ignoreCase = true) }

    val totalApplicants = jobList.sumOf { it.applications_count }

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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateClick,
                containerColor = Blue,
                contentColor = White,
                modifier = Modifier
                    .height(34.dp)
                    .width(100.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.add_svgrepo_com),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Text("Post Job")
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundColor)
                .padding(16.dp)
        ) {

            StatsRow(totalJobs, activeJobs, totalApplicants)

            Spacer(modifier = Modifier.height(24.dp))

            SectionTitle("Your Posted Jobs")

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(myJobs?.data ?: emptyList()){job->
                    JobItem(
                        title = job.title,
                        postedAt = formatToDate(job.created_at),
                        status = if(job.status == "open") "Active" else "Closed",
                        views = job.views_count,
                        applicants = job.applications_count
                    )
                }
            }
        }
    }
}
// -------------------- TOP BAR --------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecruiterTopBar(onBackClick: () -> Unit = {}) {
    TopAppBar(
        title = {
            Column() {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.back_svgrepo_com),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Text(
                        text = "Recruiter Dashboard",
                        fontWeight = FontWeight.Bold
                    )

                }

                HorizontalDivider(modifier = Modifier.padding(end = 12.dp))
            }

        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = BackgroundColor
        )
    )
}

// -------------------- SECTION TITLE --------------------
@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
}

// -------------------- STATS ROW --------------------
@Composable
fun StatsRow(totalJobs: Int, activeJobs: Int, totalApplicants: Int) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        StatCard(totalJobs.toString(), "Total Jobs")
        StatCard(activeJobs.toString(), "Active")
        StatCard(totalApplicants.toString(), "Applicants")
    }
}

@Composable
fun StatCard(value: String, label: String) {
    Card(
        modifier = Modifier.width(100.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(White)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(label, fontSize = 14.sp, color = Color.Gray)
        }
    }
}

// -------------------- JOB ITEM --------------------
@Composable
fun JobItem(title: String, postedAt: String, status: String, views: Int, applicants: Int) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
            // 🔹 Top Row
                Row() {
                    Image(
                        painter = painterResource(R.drawable.coverphoto),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Column(modifier = Modifier.padding(start = 8.dp)) {
                        Text(
                            title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            "Posted $postedAt",
                            color = Color.Gray
                        )
                    }
                }

                StatusBadge(status)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Bottom Row (Views + Applicants)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.eye_outlined_svgrepo_com),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = GreyTxt
                        )
                        Text("$views views", color = Color.Gray, modifier = Modifier.padding(start = 4.dp))
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.team_3),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = GreyTxt
                        )
                        Text("$applicants applicants", color = Color.Gray, modifier = Modifier.padding(start = 4.dp))
                    }
                }


            }
        }
    }
}
@Composable
fun StatusBadge(status: String) {

    val backgroundColor =
        if (status == "Active") Color(0xFF1E66F5) else Color.LightGray

    val textColor =
        if (status == "Active") Color.White else Color.Black

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(50)
    ) {
        Text(
            text = status,
            color = textColor,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

// -------------------- PREVIEW --------------------
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JobRecruiterScreenPreview() {
    JobRecruiterScreen()
}