package com.project.middleman.challengedetails.component

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.project.middleman.challengedetails.viewmodel.ChallengeDetailsViewModel
import com.project.middleman.core.common.BetStatus
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.model.Participant
import com.project.middleman.designsystem.themes.deepColorAccent
import com.project.middleman.designsystem.themes.red


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
                // Status move to COMPLETED
                onClick = { challengeDetailsViewModel.concludeChallenge(challenge,
                    BetStatus.PARTICIPANT_WINS.name
                    ) },
                btnText = "Claim Win \uD83C\uDF89 \uD83D\uDCB0",
                containerColor = deepColorAccent,
                enableButton = true
            )
        }

        BetStatus.CREATOR_WINS.name -> {
            ActionButton(
                onClick = { /* Close challenge */ },
                btnText = "Conclude Challenge \uD83D\uDE1E",
                containerColor = deepColorAccent,
                enableButton = true
            )
        }
        BetStatus.PARTICIPANT_WINS.name -> {
            ActionButton(
                onClick = { /* Add logic to conclude wager */ },
                btnText = "Revert Claim",
                containerColor = red,
                enableButton = true
            )
        }

        BetStatus.COMPLETED.name -> {
            ActionButton(
                onClick = { /* Add logic to conclude wager */ },
                btnText = "Challenge Closed",
                containerColor = deepColorAccent,
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

// For participants
fun participantActionWin(
    challenge: Challenge
): String {

    return when (challenge.status) {
        BetStatus.PARTICIPANT_WINS.name -> {
            "Your claim will be confirmed once your opponent confirms it within 23 hours 58 minutes."
        }
        BetStatus.CREATOR_WINS.name -> {
            "Your opponent has claimed this win. Please confirm the result within " +
                    "23 hours 58 minutes, or dispute if you disagree."
        }
        else -> {
            ""
        }
    }
}