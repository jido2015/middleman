package com.project.middleman.feature.createchallenge.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.middleman.composables.button.CustomButton
import com.middleman.composables.textfield.BorderlessTextField
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.colorBlack
import com.project.middleman.feature.createchallenge.viewmodel.CreateChallengeViewModel
import kotlinx.coroutines.delay

@Composable
fun InputAmountScreen(
    onCreateChallenge: () -> Unit,
    viewModel: CreateChallengeViewModel
) {

    val context = LocalContext.current

    var amountToStake by remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val duration = Toast.LENGTH_SHORT

    var message = "Please fill all fields correctly."
    val toast = Toast.makeText(context, message, duration)

    // Dollar amount limit
    val maxAmount = 5000.0

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(16.dp)
    ) {
        val (constraintTitle, subtitle, constraintStakeAmount, constraintTextBalance, button) = createRefs()

        Text(
            "Stake",
            style = Typography.bodyLarge.copy(fontSize = 28.sp, color = colorBlack),
            modifier = Modifier.constrainAs(constraintTitle) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start)
            }
        )

        Text(
            "Enter the amount you want to stake",
            style = Typography.labelSmall.copy(fontSize = 16.sp, color = colorBlack),
            modifier = Modifier.constrainAs(subtitle) {
                top.linkTo(constraintTitle.bottom, margin = 12.dp)
                start.linkTo(parent.start)
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(constraintStakeAmount) {
                    top.linkTo(subtitle.bottom, margin = 28.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(bottom = 1.dp),
                text = "$",
                style = Typography.bodyLarge.copy(fontSize = 20.sp, color = colorBlack),
            )
            BorderlessTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                text = amountToStake,
                onValueChange = { newText ->
                    // Only allow numeric input with decimal points and max 4 characters
                    if ((newText.isEmpty() || newText.matches(Regex("^\\d*\\.?\\d{0,2}$"))) && newText.length <= 4) {
                        amountToStake = newText
                    }
                },
                placeholder = "00.00",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
            )
        }

        Text(
            "Wallet balance: $1,000",
            style = Typography.labelSmall.copy(fontSize = 12.sp, color = colorBlack),
            modifier = Modifier.constrainAs(constraintTextBalance) {
                top.linkTo(constraintStakeAmount.bottom, margin = 8.dp)
                start.linkTo(parent.start)
            }
        )

        CustomButton(
            modifier = Modifier.fillMaxWidth()
                .constrainAs(button) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            onClick = {
                if (amountToStake.isNotBlank()) {
                    val amount = amountToStake.toDoubleOrNull()
                    if (amount != null) {
                        if (amount <= maxAmount) {
                            viewModel.stake = amount
                            onCreateChallenge()
                        } else {
                            // Show error for amount exceeding $5000
                            val errorToast = Toast.makeText(context, "Maximum stake amount is $${maxAmount.toInt()}", duration)
                            errorToast.show()
                        }
                    } else {
                        toast.show()
                    }
                } else {
                    toast.show()
                }
            },
            text = "Continue",
        )

        // Request focus when the screen is displayed
        LaunchedEffect(Unit) {
            delay(200)
            focusRequester.requestFocus()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CreateStakeChallengeScreenPreview() {
    MaterialTheme {
        InputAmountScreen(
            onCreateChallenge = {},
            viewModel = hiltViewModel()
        )
    }
}
