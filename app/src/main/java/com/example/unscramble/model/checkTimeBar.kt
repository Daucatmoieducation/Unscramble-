package com.example.unscramble.model

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TimerBar(remainingTime: Int, modifier: Modifier = Modifier) {
    val progress = remainingTime / 10f

    LinearProgressIndicator(
        progress = { progress },
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp),
        color = if (remainingTime > 3) Color.Green else Color.Red,
    )
}