package com.middleman.composables.tab

import com.middleman.composables.R
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.project.middleman.designsystem.themes.colorAccent
import com.project.middleman.designsystem.themes.lightColorAccent
import androidx.compose.runtime.getValue
import com.project.middleman.designsystem.themes.Grey


@Composable
fun CustomTab() {
    Card(
        shape = RoundedCornerShape(100.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .padding(top = 4.dp, bottom = 4.dp, start = 24.dp, end = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center // ✅ center the content
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(R.drawable.home),
                    contentDescription = null,
                    tint = Grey
                )
                CircularButton(
                    onClick = { /* TODO */ }
                )
                Icon(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(R.drawable.explore),
                    contentDescription = null,
                    tint = Grey
                )
            }
        }
    }
}

@Composable
fun CircularButton(
    onClick: () -> Unit,
    backgroundColor: Color = colorAccent,
    borderColor: Color = lightColorAccent,
    borderWidth: Dp = 4.dp,
    size: Dp = 70.dp
) {
    val infiniteTransition = rememberInfiniteTransition()

    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val glowColor = borderColor.copy(alpha = glowAlpha)

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .border(BorderStroke(borderWidth, glowColor), CircleShape)
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(R.drawable.add),
            contentDescription = null,
            tint = Color.White
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CustomTabPreview(){
    CustomTab()
}

