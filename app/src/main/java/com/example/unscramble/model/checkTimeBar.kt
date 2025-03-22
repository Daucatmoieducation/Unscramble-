package com.example.unscramble.model

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animate
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
fun TimerBar(remainingTime: Int, modifier: Modifier = Modifier) {
    var animatedProgress by remember { mutableFloatStateOf(remainingTime / 10f) }

    LaunchedEffect(remainingTime) {
        animate(
            initialValue = animatedProgress,
            targetValue = remainingTime / 10f,
            animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
        ) { value, _ ->
            animatedProgress = value
        }
    }

    LinearProgressIndicator(
        progress = { animatedProgress },
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp),
        color = if (remainingTime > 3) Color.Green else Color.Red,
    )
}