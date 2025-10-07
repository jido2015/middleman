package com.project.middleman.challengedetails.component.dipsute

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.surfaceBrandLighter

@Composable
fun DisputeNotice(
    localUserId: String?,
    creatorId: String?,
    participantId: String?,
    disputeById: String?,
    modifier: Modifier = Modifier,
    surfaceColor: Color = surfaceBrandLighter,
    timeRemaining: String = "24:30"
) {
    val message = when (localUserId) {
        null -> "A dispute has been raised for this wager"
        disputeById -> "You have raised a dispute for this wager"
        participantId, creatorId -> "Your opponent has raised a dispute for this wager"
        else -> "A dispute has been raised for this wager"
    }

    Column(
        modifier = modifier
            .background(surfaceColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            text = message,
            style = Typography.labelSmall.copy(fontSize = 12.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = buildAnnotatedString {
                append("Winnings will be allocated appropriately once resolution is completed in ")
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = 12.sp))
                append(timeRemaining)
                pop()
            },
            style = Typography.labelSmall.copy(fontSize = 12.sp, textAlign = TextAlign.Center)
        )
    }
}
