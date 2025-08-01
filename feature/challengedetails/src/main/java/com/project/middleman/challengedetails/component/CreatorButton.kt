package com.project.middleman.challengedetails.component


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.project.middleman.core.source.data.model.Challenge


@Composable
fun CreatorActionButton(
    challenge: Challenge
) {
    when (challenge.status) {
        "open", "pending" -> {
            ActionButton(
                onClick = { /* Add logic to conclude wager */ },
                btnText = "Cancel Wager",
                containerColor = Color.Red,
                enableButton = false

            )
        }

        else -> {
            ActionButton(
                onClick = { /* Add logic to conclude wager */ },
                btnText = "Conclude wager",
                containerColor = Color.Green,
                enableButton = true
            )
        }
    }
}

fun creatorActionMessage(
): String{
    return " You don't have to do anything right now. \n\nAlways keep verifiable evidence of your wagers for dispute resolution purposes."
}

