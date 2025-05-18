package com.project.middleman.feature.openchallenges.presentation

import android.R.attr.padding
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.project.middleman.core.source.data.model.Challenge
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.middleman.core.source.data.model.ParticipantProgress
import com.project.middleman.feature.openchallenges.viewmodel.OpenChallengeViewModel
import com.stevdzasan.messagebar.MessageBarState


@Composable
fun ChallengeListScreen(
    messageBarState: MessageBarState,
    onChallengeClick: (Challenge) -> Unit,
    openChallengeViewModel: OpenChallengeViewModel = hiltViewModel()
) {
    var challenges by remember { mutableStateOf(emptyList<Challenge>()) }
    
    openChallengeViewModel.setLoading(true)
    openChallengeViewModel.fetchOpenChallenges()

    fun getChallenges(result: List<Challenge>) {
        openChallengeViewModel.setLoading(false)
        challenges = result
        Log.d("getChallenges", result.toString())
    }


    DisplayChallenges(
        getChallenges = {
            getChallenges(it)
        },
        messageBarState = messageBarState
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(challenges) { challenge ->
            ChallengeCard(challenge = challenge, onClick = { onChallengeClick(challenge) })
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}


@Composable
fun ChallengeCard(
    challenge: Challenge,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = challenge.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = challenge.description,
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
                    text = "Status: ${if (challenge.status =="open") "Open" else "Closed"}",
                    color = if (challenge.status == "open") Color.Green else Color.Red,
                    style = MaterialTheme.typography.labelMedium
                )

                Text(
                    text = challenge.createdAt.toTimeAgo(), // Helper extension below
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }
        }
    }
}

fun Long.toTimeAgo(): String {
    val now = System.currentTimeMillis()
    val diff = now - this

    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
        days > 0 -> "$days day${if (days > 1) "s" else ""} ago"
        hours > 0 -> "$hours hour${if (hours > 1) "s" else ""} ago"
        minutes > 0 -> "$minutes min${if (minutes > 1) "s" else ""} ago"
        else -> "Just now"
    }
}


@Preview(showBackground = true)
@Composable
fun ChallengeListPreview() {
    val mockParticipants = mapOf(
        "user1" to ParticipantProgress(name = "Jide"),
        "user2" to ParticipantProgress(name = "Mary")
    )

    val mockChallenges = listOf(
        Challenge(
            id = "1",
            title = "Read a book",
            description = "Read at least 20 pages today.",
            createdAt = System.currentTimeMillis() - 90_000,
            status = "open",
            participant = mockParticipants
        )
    )

    ChallengeListScreen(
        messageBarState = MessageBarState(),
        onChallengeClick = {}
    )
}
