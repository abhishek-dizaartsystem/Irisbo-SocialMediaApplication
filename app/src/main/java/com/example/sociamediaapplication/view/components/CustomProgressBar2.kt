package com.example.sociamediaapplication.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.GreyTrack

@Composable
fun CustomProgressBar2(
    value: Float,
    maxValue: Float,
    modifier: Modifier = Modifier,
    barHeight: Float = 10f,
    trackColor: Color = GreyTrack,
    progressColor: Color = Blue
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
                    .background(
                        color = trackColor,
                        shape = RoundedCornerShape(12.dp)
                    )
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
                    .background(
                        color = progressColor,
                        shape = RoundedCornerShape(12.dp)
                    )
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun CustomProgressBar2Preview(){
    CustomProgressBar2(
        value = 0.2f,
        maxValue = 2f
    )


}