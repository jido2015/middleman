package com.project.middleman.feature.authentication.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
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
import com.project.middleman.feature.authentication.components.rememberCountDown
import com.project.middleman.feature.authentication.viewmodel.CreateProfileViewModel
import kotlinx.coroutines.delay


@Composable
fun PhoneVerificationScreen(
    viewModel: CreateProfileViewModel = hiltViewModel(),
    onSaveChallenge: () -> Unit
){
    val countDown = rememberCountDown(30) // 30 minutes

    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }

    var sentCode by remember { mutableStateOf("") }

    val duration = Toast.LENGTH_SHORT

    val message = "Please fill all fields correctly."
    val toast = Toast.makeText(context, message, duration)

    // Character limit
    val maxCharacters = 6
    val currentCharCount = sentCode.length


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(White)
            .padding(16.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight() // ✅ allow scrolling
        ) {
            val (constraintTitle, subtitle, textFieldTitle, charCount) = createRefs()

            Text(
                "Verify your mobile number",
                style = Typography.bodyLarge.copy(fontSize = 28.sp, color = colorBlack),
                modifier = Modifier.constrainAs(constraintTitle) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start)
                }
            )

            Text(
                "Enter the 6 digit code sent to you at ${viewModel.phoneNumber}",
                style = Typography.labelSmall.copy(fontSize = 16.sp, color = colorBlack),
                modifier = Modifier.constrainAs(subtitle) {
                    top.linkTo(constraintTitle.bottom, margin = 12.dp)
                    start.linkTo(parent.start)
                }
            )

            BorderlessTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .constrainAs(textFieldTitle) {
                        top.linkTo(subtitle.bottom, margin = 28.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    },
                text = sentCode,
                onValueChange = { newText ->
                    if (newText.length <= maxCharacters) {
                        sentCode = newText
                    }
                },
                placeholder = "e.g. ABC123",
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
                singleLine = false,
                maxLines = 3
            )

            Text(
                "$currentCharCount/$maxCharacters characters",
                style = Typography.labelSmall.copy(fontSize = 12.sp, color = colorBlack),
                modifier = Modifier.constrainAs(charCount) {
                    top.linkTo(textFieldTitle.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // countdown label
        Text(
            style = Typography.labelSmall.copy(fontSize = 16.sp, color = colorBlack),
            text = "Resend code in $countDown",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // button
        CustomButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (sentCode.isNotBlank()) {
                    onSaveChallenge()
                } else {
                    toast.show()
                }
            },
            text = "Next"
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
fun PhoneVerificationScreenPreview() {
    MaterialTheme {
        PhoneVerificationScreen(
            onSaveChallenge = {}
        )
    }
}
