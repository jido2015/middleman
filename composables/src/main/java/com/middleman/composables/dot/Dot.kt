package com.middleman.composables.dot


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.project.middleman.designsystem.themes.Grey
import com.project.middleman.designsystem.themes.borderGrey

@Composable
fun Dot(
    size: Dp = 2.dp,
    color: Color = borderGrey
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}

@Composable
fun Options(
    modifier: Modifier = Modifier,
    size: Dp = 4.dp,
    color: Color = Grey
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier.padding(end = 4.dp)
                .size(size)
                .clip(CircleShape)
                .background(color)
        )
        Box(
            modifier = Modifier.padding(end = 4.dp)
                .size(size)
                .clip(CircleShape)
                .background(color)
        )
        Box(
            modifier = Modifier.padding(end = 4.dp)
                .size(size)
                .clip(CircleShape)
                .background(color)
        )
    }

}
