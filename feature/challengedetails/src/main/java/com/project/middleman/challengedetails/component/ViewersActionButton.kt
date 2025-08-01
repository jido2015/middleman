package com.project.middleman.challengedetails.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.middleman.composables.quickations.QuickActions
import com.project.middleman.challengedetails.viewmodel.ChallengeDetailsViewModel
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.designsystem.themes.colorAccent

@Composable
fun ActionButton(
    onClick: () -> Unit,
    btnText: String,
    containerColor: Color,
    enableButton: Boolean
) {
    QuickActions(
        enableButton = enableButton,
        onButtonClick = onClick,
        btnText = btnText,
        containerColor = containerColor,
        shouldActionBtnVisible = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
    )
}


@Composable
fun ViewersActionButton(
    challengeDetailsViewModel: ChallengeDetailsViewModel,
    onAcceptChallenge: () -> Unit,
    challenge: Challenge
) {
    when (challenge.status) {
        "open" -> {
            ActionButton(
                onClick = {
                    challengeDetailsViewModel.onChallengeAccepted(challenge)
                    onAcceptChallenge()
                },
                btnText = "Join Wager",
                containerColor = colorAccent,
                enableButton = true
            )
        }

        "pending", "something_else" -> {
            ActionButton(
                onClick = { /* Add logic to conclude wager */ },
                btnText = "Conclude wager",
                containerColor = colorAccent,
                enableButton = false
            )
        }
    }
}


fun viewersActionMessage(
    challenge: Challenge
): String{

        return if (challenge.status == "open") {
            "No challengers yet. Send your request to get the ball rolling!"
        }else if (challenge.status == "pending") {

            "Another player has already requested to join this bet." +
                    " Click the bell Icon to stay up to date on the status of this wager."

        } else {
            "The bet has already been taken. Click the bell Icon to see how it plays out. "

        }

}