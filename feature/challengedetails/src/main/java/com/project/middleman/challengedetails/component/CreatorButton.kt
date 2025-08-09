package com.project.middleman.challengedetails.component


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.project.middleman.challengedetails.viewmodel.ChallengeDetailsViewModel
import com.project.middleman.core.common.BetStatus
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.designsystem.themes.colorAccent
import com.project.middleman.designsystem.themes.colorGreen
import com.project.middleman.designsystem.themes.lightVColorAccent


@Composable
fun CreatorActionButton(
    challenge: Challenge,
    challengeDetailsViewModel: ChallengeDetailsViewModel
) {
    when (challenge.status) {
        BetStatus.PENDING.name,  BetStatus.OPEN.name -> {
            ActionButton(
                onClick = { /* Add logic to conclude wager */ },
                btnText = "Cancel Wager",
                containerColor = Color.Red,
                enableButton = false
            )
        }

        BetStatus.ACTIVE.name -> {
            ActionButton(
                onClick = { /* Add logic to conclude wager */ },
                btnText = "Conclude wager",
                containerColor = colorGreen,
                enableButton = true
            )
        }
        BetStatus.COMPLETED.name -> {
            ActionButton(
                onClick = { /* Add logic to conclude wager */ },
                btnText = "Conclude wager",
                containerColor = colorAccent,
                enableButton = false
            )
        }
    }
}

fun creatorActionMessage(
): String{
    return "Always keep verifiable evidence of your wagers for dispute resolution purposes."
}

