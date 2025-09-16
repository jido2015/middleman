package com.middleman.composables.progressbar

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.borderGrey
import com.project.middleman.designsystem.themes.colorAccent
import com.project.middleman.designsystem.themes.surface
import com.project.middleman.designsystem.themes.surfaceBrandLight

@Composable
fun ProgressBarUI(completedSteps: Float, modifier: Modifier){

    LinearProgressIndicator(
        trackColor = borderGrey,
        color = colorAccent,
        progress = { completedSteps },
        modifier = modifier.fillMaxWidth(),
    )
}

@Composable
fun CircularProgressBar(
    currentStake: Double,
    totalStake: Double,
    modifier: Modifier = Modifier
) {
    val targetProgress = if (totalStake > 0) {
        (currentStake / totalStake).toFloat().coerceIn(0f, 1f)
    } else 0f

    val animatedProgress = remember { Animatable(0f) }

    // Animate when composable enters OR targetProgress changes
    LaunchedEffect(targetProgress) {
        animatedProgress.animateTo(
            targetValue = targetProgress,
            animationSpec = tween(durationMillis = 1200)
        )
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(224.dp)
            .background(
                color = surfaceBrandLight, // your desired background color
                shape = CircleShape
            )
    ) {
        CircularProgressIndicator(
            progress = { animatedProgress.value },
            color = colorAccent,
            trackColor = borderGrey,
            strokeWidth = 6.dp,
            modifier = Modifier.fillMaxSize()
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$${"%.2f".format(currentStake)}",
                style = Typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 35.sp
                )
            )
            Text(
                text = "of $${"%.2f".format(totalStake)} in play",
                style = Typography.labelMedium.copy(fontSize = 14.sp)
            )
        }
    }
}
