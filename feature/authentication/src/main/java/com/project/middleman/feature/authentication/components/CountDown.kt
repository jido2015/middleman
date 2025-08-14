package com.project.middleman.feature.authentication.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun rememberCountDown(totalMinutes: Int): String {
    var remainingTime by remember { mutableIntStateOf(totalMinutes * 60) }

    LaunchedEffect(totalMinutes) {
        remainingTime = totalMinutes * 60
        while (remainingTime > 0) {
            delay(1000)
            remainingTime--
        }
    }

    val minutes = remainingTime / 60
    val seconds = remainingTime % 60
    return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
}
