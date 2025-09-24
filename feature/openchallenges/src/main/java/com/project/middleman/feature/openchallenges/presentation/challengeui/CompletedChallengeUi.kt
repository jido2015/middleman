package com.project.middleman.feature.openchallenges.presentation.challengeui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.feature.openchallenges.presentation.ChallengeCardItem

@Composable
fun CompletedChallengeUi(
    challenges: List<Challenge>,
    onCardChallengeClick: (Challenge) -> Unit
)
{

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {

        items(challenges.filter { challenge -> challenge.status == "CLOSED" }) { challenge ->
            ChallengeCardItem(
                challenge = challenge,
                onChallengeClick = {

                    onCardChallengeClick(challenge)
                })
            Spacer(modifier = Modifier.height(10.dp))
        }

    }

}