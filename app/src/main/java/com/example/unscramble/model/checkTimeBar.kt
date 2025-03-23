package com.example.unscramble.model

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TimerBar(remainingTime: Int, maxTime: Int, modifier: Modifier = Modifier) {
    var animatedProgress by remember { mutableFloatStateOf(1f) }

    LaunchedEffect(remainingTime) {
        animatedProgress = remainingTime.toFloat() / maxTime
    }

    val smoothProgress by animateFloatAsState(
        targetValue = animatedProgress,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing), label = ""
    )

    val progressColor by animateColorAsState(
        targetValue = when {
            smoothProgress > 0.5f -> Color.Green
            smoothProgress > 0.2f -> Color.Yellow
            else -> Color.Red
        },
        animationSpec = tween(durationMillis = 500), label = "" // Đổi màu mượt hơn
    )

    LinearProgressIndicator(
        progress = { smoothProgress },
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp),
        color = progressColor,
    )
}

