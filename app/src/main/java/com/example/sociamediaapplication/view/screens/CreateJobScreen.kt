package com.example.sociamediaapplication.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sociamediaapplication.view.components.CustomTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateJobScreen(
    onSubmit: (Map<String, Any>) -> Unit = {}
) {

    var title by remember { mutableStateOf("") }
    var companyName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var responsibilities by remember { mutableStateOf("") }
    var requirements by remember { mutableStateOf("") }
    var benefits by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var workplaceType by remember { mutableStateOf("") }
    var jobType by remember { mutableStateOf("") }
    var experienceLevel by remember { mutableStateOf("") }
    var minSalary by remember { mutableStateOf("") }
    var maxSalary by remember { mutableStateOf("") }
    var currency by remember { mutableStateOf("INR") }
    var vacancies by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Create Job") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val body = mapOf(
                    "title" to title,
                    "company_name" to companyName,
                    "description" to description,
                    "responsibilities" to responsibilities,
                    "requirements" to requirements,
                    "benefits" to benefits,
                    "location" to location,
                    "workplace_type" to workplaceType,
                    "job_type" to jobType,
                    "experience_level" to experienceLevel,
                    "min_salary" to minSalary,
                    "max_salary" to maxSalary,
                    "salary_currency" to currency,
                    "vacancies" to (vacancies.toIntOrNull() ?: 0),
                    "application_deadline" to deadline
                )
                onSubmit(body)
            }) {
                Text("Post")
            }
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item { CustomTextField(label = "Job Title", value = title, placeHolder = "Enter job title", onValueChange = { title = it }  ) }
            item { CustomTextField(label = "Company Name", value = companyName, placeHolder = "Enter company name", onValueChange = { companyName = it })  }
            item { CustomTextField(label = "Description", value = description, placeHolder = "Enter description", onValueChange =  { description = it }) }
            item { CustomTextField(label = "Responsibilities", value = responsibilities, placeHolder = "A, B, C", onValueChange = { responsibilities = it })  }
            item { CustomTextField(label = "Requirements", value = requirements, placeHolder = "Enter requirements", onValueChange =  { requirements = it }) }
            item { CustomTextField(label = "Benefits", value = benefits, placeHolder = "P, Q, R", onValueChange = { benefits = it })  }
            item { CustomTextField(label = "Location", value = location, placeHolder = "Enter location", onValueChange =  { location = it }) }

            item { CustomTextField(label = "Workplace Type", value = workplaceType, placeHolder = "onsite / remote", onValueChange = { workplaceType = it })  }
            item { CustomTextField(label = "Job Type", value = jobType, placeHolder = "full_time", onValueChange = { jobType = it })  }
            item { CustomTextField(label = "Experience Level", value = experienceLevel, placeHolder = "fresher", onValueChange = { experienceLevel = it })  }

            item { CustomTextField(label = "Min Salary", value = minSalary, placeHolder = "400000", keyboardType = KeyboardType.Number, onValueChange = { minSalary = it })  }
            item { CustomTextField(label = "Max Salary", value = maxSalary, placeHolder = "800000", keyboardType = KeyboardType.Number, onValueChange =  { maxSalary = it }) }

            item { CustomTextField(label = "Currency", value = currency, placeHolder = "INR", onValueChange = { currency = it })  }
            item { CustomTextField(label = "Vacancies", value = vacancies, placeHolder = "1", keyboardType = KeyboardType.Number, onValueChange = { vacancies = it })  }

            item { CustomTextField(label = "Application Deadline", value = deadline, placeHolder = "YYYY-MM-DD", onValueChange = { deadline = it })  }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCreateJobScreen() {
    CreateJobScreen()
}
