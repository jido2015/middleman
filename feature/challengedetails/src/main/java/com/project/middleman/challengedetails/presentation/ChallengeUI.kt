package com.project.middleman.challengedetails.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.middleman.composables.card.ChallengeCard
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.designsystem.themes.borderGrey


@Composable
fun ChallengeDetailUi(
    modifier: Modifier = Modifier,
    challenge: Challenge = Challenge(),
    onAcceptChallenge: () -> Unit = {}) {

    val creator =  challenge.participant.entries.find {
        it.value.status == "Creator"
    }?.value

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(0.5.dp, borderGrey),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            ChallengeCard(
                challenge = challenge,
                onChallengeClick = { selectedChallenge -> }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ChallengeDetails(){
    ChallengeDetailUi()
}