package com.example.sociamediaapplication.view.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.White
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    isLoggedIn: Boolean,
    onNavigateToAuth: () -> Unit,
    onNavigateToMain: () -> Unit
) {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        delay(1500)

        if (isLoggedIn) {
            onNavigateToMain()
        } else {
            onNavigateToAuth()
        }
    }

    Column(modifier = Modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(){

                Column(
                    modifier = Modifier.fillMaxSize().background(color = White),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    GlowingLogo()
                    Text(
                        text = "FitTrack Pro",
                        fontSize = 30.sp,
                        color = Blue,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    AnimatedDots()
                }
            }

        }

    }
}

@Composable
fun AnimatedDots(
    dotSize: Dp = 8.dp,
    color: Color = Blue,
    distance: Dp = 10.dp,
    duration: Int = 600
) {
    val transition = rememberInfiniteTransition()

    val yOffsets = List(3) { i ->
        transition.animateFloat(
            initialValue = 0f,
            targetValue = 0.5f,
            animationSpec = infiniteRepeatable(
                animation = tween(duration, easing = LinearEasing, delayMillis = i * 100),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(dotSize),
        verticalAlignment = Alignment.CenterVertically
    ) {
        yOffsets.forEach { animatedOffset ->
            Box(
                modifier = Modifier
                    .size(dotSize)
                    .offset(y = (-animatedOffset.value * distance))
                    .background(color, shape = CircleShape)
            )
        }
    }
}

@Composable
fun GlowingLogo() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(120.dp)) {
        // Simulate glow with a large translucent circle
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Blue.copy(alpha = 0.8f),
                            Blue.copy(alpha = 0.7f),
                            Blue.copy(alpha = 0.4f),
                            Blue.copy(alpha = 0.8f),
                            Blue.copy(alpha = 0.8f),
                            Blue.copy(alpha = 0.8f),
                            Blue.copy(alpha = 0.8f),
                            Blue.copy(alpha = 0.8f),
                            Blue.copy(alpha = 0.8f),
                            Blue.copy(alpha = 0.2f),
                            Blue.copy(alpha = 0.4f),
                            Blue.copy(alpha = 0.2f),
                            Blue.copy(alpha = 0.08f),
                            Blue.copy(alpha = 0.05f),
                            Blue.copy(alpha = 0.02f),
                            Blue.copy(alpha = 0.01f),
                        )
                    ),
                    shape = CircleShape
                )
        )
        // Logo circle
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(
                    color = Blue,
                    shape = CircleShape
                )
        ) {
            Icon(
                painter = painterResource(R.drawable.facebook_svgrepo_com),
                contentDescription = null,
                tint = White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(44.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview(){
    SplashScreen(
        isLoggedIn = true,
        onNavigateToAuth = {},
        onNavigateToMain = {}
    )
}

