package com.project.middleman.challengedetails.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.middleman.composables.button.CustomButton
import com.project.middleman.designsystem.themes.colorAccent
import com.project.middleman.designsystem.themes.colorGreen
import com.project.middleman.designsystem.themes.red
import com.project.middleman.designsystem.themes.white

@Composable
fun DynamicButton(
    button1Color: Color = colorAccent,
    button2Color: Color = colorAccent,
    modifier: Modifier,
    btnText1Color: Color = white,
    btnText2Color: Color = white,
    btn1Text: String = "Declare tie",
    btn2Text: String = "Join Wager",
    onFirstButtonClick: () -> Unit = {},
    onSecondButtonClick: () -> Unit = {},
    firstButtonVisibility: Boolean = true,
    secondButtonVisibility: Boolean = true,
    enableBtn2: Boolean = true,
    enableBtn1: Boolean = true,
    isButtonVisible: Boolean = true,
    border1Color: Color = Color.Transparent
) {
    val horizontalArrangement = if (firstButtonVisibility && secondButtonVisibility) {
        Arrangement.SpaceBetween
    } else {
        Arrangement.spacedBy(8.dp)
    }

    if (isButtonVisible) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (firstButtonVisibility) {
                CustomButton(
                    borderColor = border1Color,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    textColor = btnText1Color,
                    enableButton = enableBtn1,
                    containerColor = button1Color,
                    text = btn1Text,
                    onClick = { onFirstButtonClick() }
                )
            }

            if (secondButtonVisibility) {
                CustomButton(
                    textColor = btnText2Color,
                    enableButton = enableBtn2,
                    containerColor = button2Color,
                    text = btn2Text,
                    onClick = { onSecondButtonClick() },
                    modifier = Modifier.weight(1f)
                )
            }

        }
    }
}

@Composable
fun RejectOrAcceptButton(
    onRejectButtonClick: () -> Unit,
    onAcceptButtonClick: () -> Unit,
) {

    Row(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,

    ) {

        CustomButton(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            textColor = white,
            enableButton = true,
            containerColor = red,
            text = "Reject",
            onClick = { onRejectButtonClick() }
        )


        CustomButton(
            textColor = white,
            enableButton = true,
            containerColor = colorGreen,
            text = "Accept",
            onClick = { onAcceptButtonClick() },
            modifier = Modifier.weight(1f)
        )
    }
}