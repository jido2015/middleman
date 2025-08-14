package com.project.middleman.feature.authentication.presentation

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.middleman.composables.button.CustomButton
import com.middleman.composables.textfield.BorderlessTextField
import com.middleman.composables.textfield.BorderlessTextField2
import com.middleman.composables.textfield.PhoneNumberBorderlessTextField
import com.project.middleman.core.common.formatPhoneNumber
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.colorBlack
import com.project.middleman.feature.authentication.viewmodel.CreateProfileViewModel
import kotlinx.coroutines.delay


@Composable
fun PhoneNumberScreen(
    viewModel: CreateProfileViewModel = hiltViewModel(),
    onSaveChallenge: () -> Unit
){

    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var phoneNumber by remember { mutableStateOf(TextFieldValue("")) }

    val duration = Toast.LENGTH_SHORT

    val message = "Please fill all fields correctly."
    val toast = Toast.makeText(context, message, duration)


    // Character limit
    val maxCharacters = 18
    val currentCharCount = phoneNumber.text.length

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(16.dp)
    ) {
        val (constraintTitle, subtitle, textFieldTitle, charCount, button) = createRefs()

        Text(
            "What's your mobile number?",
            style = Typography.bodyLarge.copy(fontSize = 28.sp, color = colorBlack),
            modifier = Modifier.constrainAs(constraintTitle) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start)
            }
        )

        Text(
            "For match updates, challenge alerts, and secure payouts. Use your real number.",
            style = Typography.labelSmall.copy(fontSize = 16.sp, color = colorBlack),
            modifier = Modifier.constrainAs(subtitle) {
                top.linkTo(constraintTitle.bottom, margin = 12.dp)
                start.linkTo(parent.start)
            }
        )

        PhoneNumberBorderlessTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .constrainAs(textFieldTitle) {
                    top.linkTo(subtitle.bottom, margin = 28.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            value = phoneNumber,
            onValueChange = { newValue ->
                val oldText = phoneNumber.text
                val formatted = formatPhoneNumber(newValue.text)

                val newCursorPos = if (formatted.length > oldText.length) {
                    formatted.length // move to end if adding
                } else {
                    newValue.selection.end // keep position if deleting
                }

                if (formatted.length <= maxCharacters) {
                    phoneNumber = TextFieldValue(
                        text = formatted,
                        selection = TextRange(newCursorPos)
                    )
                }
            },
            placeholder = "Enter your number",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done
            ),
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
        )


        Text(
            "$currentCharCount/$maxCharacters characters",
            style = Typography.labelSmall.copy(fontSize = 12.sp, color = colorBlack),
            modifier = Modifier.constrainAs(charCount) {
                top.linkTo(textFieldTitle.bottom, margin = 8.dp)
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
                if (phoneNumber.text.isNotBlank()
                ) {
                    viewModel.phoneNumber = phoneNumber.text
                    onSaveChallenge()
                } else {
                    toast.show()
                }

            },
            text = "Next",

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
fun AddressLineScreenPreview() {
    MaterialTheme {
        PhoneNumberScreen(
            onSaveChallenge = {}
        )
    }
}
