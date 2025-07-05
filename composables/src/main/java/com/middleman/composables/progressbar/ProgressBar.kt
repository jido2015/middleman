package com.middleman.composables.progressbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.project.middleman.designsystem.themes.borderGrey
import com.project.middleman.designsystem.themes.colorAccent

@Composable
fun ProgressBarUI(completedSteps: Int, modifier: Modifier){
    val totalSteps = 4
    val progress = completedSteps.toFloat() / totalSteps

    LinearProgressIndicator(
        trackColor = borderGrey,
        color = colorAccent,
        progress = { progress },
        modifier = modifier.fillMaxWidth(),
    )
}