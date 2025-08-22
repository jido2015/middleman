package com.project.middleman.challengedetails.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.middleman.composables.quickations.QuickActions
import com.project.middleman.challengedetails.viewmodel.ChallengeDetailsViewModel
import com.project.middleman.core.common.BetStatus
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.designsystem.themes.borderGrey
import com.project.middleman.designsystem.themes.colorAccent
import com.project.middleman.designsystem.themes.lightVColorAccent
import com.project.middleman.designsystem.themes.white
import javax.annotation.meta.When

@Composable
fun ActionButton(
    onClick: () -> Unit,
    btnText: String,
    containerColor: Color,
    btnTextColor: Color = white,
    enableButton: Boolean
) {
    QuickActions(
        btnTextColor = btnTextColor,
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
    showAcceptSummary: () -> Unit,
    challengeDetailsViewModel: ChallengeDetailsViewModel = hiltViewModel(),
    challenge: Challenge
) {
    when (challenge.status) {
        BetStatus.OPEN.name -> {
            ActionButton(
                onClick = {
                    showAcceptSummary()
                },
                btnText = "Join Wager",
                containerColor = colorAccent,
                enableButton = true
            )
        }

        else -> {
            ActionButton(
                btnTextColor = white,
                onClick = { /* Add logic to conclude wager */ },
                btnText = "Join Wager",
                containerColor = lightVColorAccent,
                enableButton = false
            )
        }
    }
}


fun viewersActionMessage(
    challenge: Challenge
): String{

        return if (challenge.status == BetStatus.OPEN.name) {
            "No challengers yet. Send your request to get the ball rolling!"
        }else if (challenge.status == BetStatus.PENDING.name) {

            "Another player has already requested to join this bet." +
                    " Click the bell Icon to stay up to date on the status of this wager."

        } else {
            "The bet has already been taken. Click the bell Icon to see how it plays out. "

        }

}