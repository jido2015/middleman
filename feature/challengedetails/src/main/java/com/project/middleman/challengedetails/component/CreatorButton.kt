package com.project.middleman.challengedetails.component


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.project.middleman.challengedetails.viewmodel.ChallengeDetailsViewModel
import com.project.middleman.core.common.BetStatus
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.designsystem.themes.borderGrey
import com.project.middleman.designsystem.themes.deepColorAccent
import com.project.middleman.designsystem.themes.red

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
                onClick = { challengeDetailsViewModel.concludeChallenge(challenge,
                    BetStatus.CREATOR_WINS.name) },
                btnText = "Claim Win \uD83C\uDF89 \uD83D\uDCB0",
                containerColor = deepColorAccent,
                enableButton = true
            )
        }

        BetStatus.CREATOR_WINS.name -> {
            ActionButton(
                onClick = { /* Add logic to conclude wager */ },
                btnText = "Revert Claim",
                containerColor = red,
                enableButton = true
            )
        }
        BetStatus.PARTICIPANT_WINS.name -> {
            ActionButton(
                onClick = { /* Add logic to conclude wager */ },
                btnText = " Conclude Challenge \uD83D\uDE1E",
                containerColor = red,
                enableButton = true
            )
        }
        BetStatus.COMPLETED.name -> {
            ActionButton(
                onClick = { /* Add logic to conclude wager */ },
                btnText = "Challenge Closed",
                containerColor = borderGrey,
                enableButton = false
            )
        }

    }
}

fun creatorActionMessage(
): String{
    return "Always keep verifiable evidence of your wagers for dispute resolution purposes."
}


// For participants
fun creatorActionWin(
    challenge: Challenge
): String {

    return when (challenge.status) {
        BetStatus.PARTICIPANT_WINS.name -> {

            "Your opponent has claimed this win. Please confirm the result within " +
                    "23 hours 58 minutes, or dispute if you disagree."
        }
        BetStatus.CREATOR_WINS.name -> {
            "Your claim will be confirmed once your opponent confirms it within 23 hours 58 minutes."
        }
        else -> {
            ""
        }
    }
}
