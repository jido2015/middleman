package com.project.middleman.challengedetails.component.action

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.project.middleman.core.common.BetStatus
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.designsystem.themes.colorAccent
import com.project.middleman.designsystem.themes.deepColorAccent
import com.project.middleman.designsystem.themes.lightVColorAccent
import com.project.middleman.designsystem.themes.white

@Composable
fun ActionButton(
    button1Color: Color = colorAccent,
    button2Color: Color = colorAccent,
    btnText1Color: Color = white,
    btnText2Color: Color = white,
    btn1Text: String = "Declare tie",
    btn2Text: String = "Join Wager",
    onFirstButtonClick: () -> Unit = {},
    onSecondButtonClick: () -> Unit = {},
    firstButtonVisibility: Boolean = false,
    secondButtonVisibility: Boolean = true,
    enableBtn2: Boolean = false,
    enableBtn1: Boolean = false,
    border1Color: Color = Color.Transparent,
    isButtonVisible: Boolean = true

) {
    DynamicButton(
        isButtonVisible = isButtonVisible,
        button1Color = button1Color,
        button2Color = button2Color,
        btnText1Color = btnText1Color,
        btnText2Color = btnText2Color,
        btn1Text = btn1Text,
        onFirstButtonClick = onFirstButtonClick,
        onSecondButtonClick = onSecondButtonClick,
        firstButtonVisibility = firstButtonVisibility,
        secondButtonVisibility = secondButtonVisibility,
        border1Color = border1Color,
        enableBtn1 = enableBtn1,
        enableBtn2 = enableBtn2,
        btn2Text = btn2Text,
        modifier = Modifier.fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),

    )
}


@Composable
fun ViewersActionButton(
    showAcceptSummary: () -> Unit,
    challenge: Challenge
) {
    when (challenge.status) {
        BetStatus.OPEN.name -> {
            ActionButton(
                onSecondButtonClick = {
                    showAcceptSummary()
                },
                btn2Text = "Join Wager",
                button2Color = deepColorAccent,
                enableBtn2 = true,
                enableBtn1 = false,
                firstButtonVisibility = false,
                secondButtonVisibility = true
            )
        }

        else -> {
            ActionButton(
                btnText2Color = white,
                onSecondButtonClick = { /* Add logic to conclude wager */ },
                btn2Text = "Join Wager",
                button2Color = lightVColorAccent,
                enableBtn2 = false,
                enableBtn1 = false,
                secondButtonVisibility = false,
                firstButtonVisibility = false

            )
        }
    }
}


fun viewersActionMessage(
    challenge: Challenge
): String{

        return when (challenge.status) {
            BetStatus.OPEN.name -> {
                "No challengers yet. Send your request to get the ball rolling!"
            }
            BetStatus.PENDING.name -> {

                "Another player has already requested to join this bet." +
                        " Click the bell Icon to stay up to date on the status of this wager."

            }
            else -> {
                "The bet has already been taken. Click the bell Icon to see how it plays out. "
            }
        }

}