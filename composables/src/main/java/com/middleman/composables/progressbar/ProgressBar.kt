package com.middleman.composables.progressbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.project.middleman.designsystem.themes.borderGrey
import com.project.middleman.designsystem.themes.colorAccent

@Composable
fun ProgressBarUI(completedSteps: Float, modifier: Modifier){

    LinearProgressIndicator(
        trackColor = borderGrey,
        color = colorAccent,
        progress = { completedSteps },
        modifier = modifier.fillMaxWidth(),
    )
}