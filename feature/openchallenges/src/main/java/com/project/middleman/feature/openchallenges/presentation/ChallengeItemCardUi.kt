package com.project.middleman.feature.openchallenges.presentation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.middleman.core.source.data.model.Challenge

@Composable
fun ChallengeCardItem(
    userUid: String? = null,
    challenge: Challenge = Challenge(),
    onCardClicked: () -> Unit,
    onChallengeClick: (Challenge) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClicked() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = challenge.participant.entries.find {
                    it.value.status == "Creator" }?.value?.name ?: "Unknown Creator",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End // 👈 this aligns text content to the right
            )
            Text(
                text = challenge.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = challenge.category,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Status: ${if (challenge.status =="open") "Open" else 
                        if (challenge.status =="closed") "Closed" else "In-Progress"}",
                    color = if (challenge.status == "open") Color.Green else Color.Magenta,
                    style = MaterialTheme.typography.labelMedium
                )

                Text(
                    text = challenge.createdAt.toTimeAgo(), // Helper extension below
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }

            if (userUid == challenge.participant.entries.find{
                it.value.status == "Creator"}?.value?.userId ||
                challenge.participant.containsKey(userUid) ) {
                //Do nothing
                Log.d("ChallengeCardItem", "Do nothing")
            }else{
                Log.d("ChallengeCardItem", "Do Something")
                Spacer(modifier = Modifier.height(20.dp))

                //Accept Challenge Button
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = {
                        onChallengeClick(challenge)},
                    shape = RoundedCornerShape(20)
                ) {
                    Text(
                        text = "Accept & Join"
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChallengeCardItem() {

    ChallengeCardItem(
        userUid = "user123",
        onCardClicked = { /* Preview: card clicked */ },
        onChallengeClick = { /* Preview: challenge clicked */ }
    )
}

