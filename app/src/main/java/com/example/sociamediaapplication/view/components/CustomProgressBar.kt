package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.GreyBtn
import com.example.sociamediaapplication.ui.theme.Red

@Composable
fun CustomProgressBar(
    value: Float,
    maxValue: Float,
    modifier: Modifier = Modifier,
    barHeight: Float = 2f,
    trackColor: Color = GreyBtn,
    progressColor: Color = Red
) {
    val progress = (value / maxValue).coerceIn(minimumValue = 0f, maximumValue = 1f)
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart) {
        // Background full width bar (track)
        Row () {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(barHeight.dp)
                    .background(trackColor)
            )
        }

        // Progress completed portion
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .height(barHeight.dp)
                    .background(progressColor)
            )
            Icon(
                painter = painterResource(R.drawable.dot),
                contentDescription = "",
                modifier = Modifier.size(10.dp)
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun CustomProgressBarPreview(){
    CustomProgressBar(
        value = 0.2f,
        maxValue = 2f
    )


}