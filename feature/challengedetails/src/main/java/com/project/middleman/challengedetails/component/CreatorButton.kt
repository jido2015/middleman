package com.project.middleman.challengedetails.component


import android.util.Log
import androidx.compose.runtime.Composable
import com.project.middleman.challengedetails.viewmodel.ChallengeDetailsViewModel
import com.project.middleman.core.common.BetStatus
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.designsystem.themes.borderGrey
import com.project.middleman.designsystem.themes.colorBlack
import com.project.middleman.designsystem.themes.deepColorAccent
import com.project.middleman.designsystem.themes.lightGrey
import com.project.middleman.designsystem.themes.red
import com.project.middleman.designsystem.themes.white

@Composable
fun CreatorActionButton(

    challenge: Challenge,
    challengeDetailsViewModel: ChallengeDetailsViewModel
) {
    val participant = challenge.participant.entries.find { it.value.status == "Participant" }?.value

    when (challenge.status) {

        BetStatus.ACTIVE.name -> {
            ActionButton(
                secondButtonVisibility = true,
                onSecondButtonClick = { challengeDetailsViewModel.concludeChallenge(challenge,
                    BetStatus.CREATOR_WINS.name) },
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
        BetStatus.PARTICIPANT_WINS.name -> {
            Log.d("BetStatus", "PARTICIPANT_WINS")

            // Reject or accept challenge
            ActionButton( isButtonVisible = false )

            RejectOrAcceptButton(
                onRejectButtonClick = {
                    // Dispute
                  //  Toast.makeText(@this, "Dispute", Toast.LENGTH_SHORT).show()
                },
                onAcceptButtonClick = { challengeDetailsViewModel.concludeFinalChallenge(
                    winnerDisplayName = participant?.displayName,
                    challenge = challenge,
                    status = BetStatus.CLOSED.name
                ) }
            )
        }
        BetStatus.CLOSED.name -> {
            ActionButton(
                onSecondButtonClick = { /* Add logic to conclude wager */ },
                btn2Text = "Challenge Closed",
                button2Color = borderGrey,
                enableBtn2 = false
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
