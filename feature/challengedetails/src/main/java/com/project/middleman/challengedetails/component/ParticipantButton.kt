package com.project.middleman.challengedetails.component

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.project.middleman.challengedetails.viewmodel.ChallengeDetailsViewModel
import com.project.middleman.core.common.BetStatus
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.model.Participant
import com.project.middleman.designsystem.themes.colorAccent
import com.project.middleman.designsystem.themes.colorGreen


@Composable
fun ParticipantActionButton(
    challenge: Challenge,
    challengeDetailsViewModel: ChallengeDetailsViewModel,
    participant: Participant?
) {

    Log.d("ParticipantActionButton", "Status: ${challenge.status}")
    when (challenge.status) {
        BetStatus.PENDING.name -> {
            ActionButton(
                onClick = {
                    challengeDetailsViewModel
                        .removeParticipant(challenge.id,
                            participant?.userId ?: ""
                        )
                },
                btnText = "Cancel Request",
                containerColor = Color.Red,
                enableButton = true

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


// For participants
fun participantActionMessage(
    challenge: Challenge
): String {

     return if( challenge.status ==  BetStatus.PENDING.name) {
        "Your request has been sent. We’ll notify you when the other player responds."

    } else {
        "Always keep verifiable evidence of your wagers for dispute " +
                "resolution purposes."
    }
}