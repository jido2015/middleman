package com.project.middleman.challengedetails.component.action

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.project.middleman.challengedetails.viewmodel.ChallengeDetailsViewModel
import com.project.middleman.core.common.BetStatus
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.model.Participant
import com.project.middleman.designsystem.themes.colorBlack
import com.project.middleman.designsystem.themes.deepColorAccent
import com.project.middleman.designsystem.themes.lightGrey
import com.project.middleman.designsystem.themes.red
import com.project.middleman.designsystem.themes.white


@Composable
fun ParticipantActionButton(
    challenge: Challenge,
    challengeDetailsViewModel: ChallengeDetailsViewModel,
    participant: Participant?
) {

    val creatorId = challenge.participant.entries.find { it.value.status == "Creator" }?.value?.displayName

    Log.d("ParticipantActionButton", "Status: ${challenge.status}")
    when (challenge.status) {
        BetStatus.PENDING.name -> {
            ActionButton(
                onSecondButtonClick = {
                    challengeDetailsViewModel
                        .removeParticipant(challenge.id,
                            participant?.userId ?: ""
                        )
                },
                btn2Text = "Cancel Request",
                button2Color = Color.Red,
                enableBtn2 = true

            )
        }

        BetStatus.ACTIVE.name -> {
            ActionButton(
                secondButtonVisibility = true,
                onSecondButtonClick = { challengeDetailsViewModel.concludeChallenge(challenge,
                    BetStatus.PARTICIPANT_WINS.name) },
                btn2Text = "Claim win \uD83C\uDF89 \uD83D\uDCB0",
                button2Color = deepColorAccent,
                enableBtn2 = true,
                onFirstButtonClick = {},// Declare tie
                btn1Text = "Declare tie",
                button1Color = white,
                enableBtn1 = true,
                firstButtonVisibility = true,
                btnText1Color = colorBlack,
                border1Color = lightGrey
            )
        }

        BetStatus.CREATOR_WINS.name -> {
            Log.d("BetStatus", "CREATOR_WINS")

            // Reject or accept challenge
            ActionButton( isButtonVisible = false )

            RejectOrAcceptButton(
                onRejectButtonClick = {
                    challengeDetailsViewModel.openDisputeDialog()
                },
                onAcceptButtonClick = { challengeDetailsViewModel.concludeFinalChallenge(
                    winnerDisplayName = creatorId,
                    challenge,
                    BetStatus.CLOSED.name
                ) }
            )
        }
        BetStatus.PARTICIPANT_WINS.name -> {
            Log.d("BetStatus", "PARTICIPANT_WINS")

            ActionButton(
                onSecondButtonClick = {
                    challengeDetailsViewModel.concludeChallenge(challenge,
                        BetStatus.ACTIVE.name)
                },
                btn2Text = "Revert Claim",
                button2Color = red,
                enableBtn2 = true
            )
        }

        BetStatus.CLOSED.name -> {
            ActionButton(
                onSecondButtonClick = { /* Add logic to conclude wager */ },
                btn2Text = "Challenge Closed",
                button2Color = deepColorAccent,
                enableBtn2 = false
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