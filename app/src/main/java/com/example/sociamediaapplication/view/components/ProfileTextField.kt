package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sociamediaapplication.ui.theme.Grey
import com.example.sociamediaapplication.ui.theme.Transparent

@Composable
fun ProfileTextField(
    label: String,
    value: String,
    onChange: (String) -> Unit
) {
    Text(label)

    TextField(
        value = value,
        onValueChange = onChange,
        placeholder = { Text("Enter $label") },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Transparent,
            unfocusedIndicatorColor = Transparent,
            disabledIndicatorColor = Transparent,
            focusedContainerColor = Transparent,
            unfocusedContainerColor = Transparent
        ),
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Grey, RoundedCornerShape(50.dp))
            .height(50.dp)
    )

    Spacer(Modifier.height(8.dp))
}