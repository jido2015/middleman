package com.project.middleman.challengedetails.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.project.middleman.core.source.data.model.Challenge


@Composable
fun ParticipantActionButton(
    challenge: Challenge
) {
    when (challenge.status) {
        "pending" -> {
            ActionButton(
                onClick = { /* Add logic to conclude wager */ },
                btnText = "Cancel Request",
                containerColor = Color.Red,
                enableButton = true

            )
        }

        else -> {
            ActionButton(
                onClick = { /* Add logic to conclude wager */ },
                btnText = "Conclude wager",
                containerColor = Color.Green,
                enableButton = false
            )
        }
    }
}


// For participants
fun participantActionMessage(
    challenge: Challenge
): String {

     return if( challenge.status == "pending") {
        "Your request has been sent. We’ll notify you when the other player responds."

    } else {
        "Always keep verifiable evidence of your wagers for dispute " +
                "resolution purposes."
    }
}