package com.project.middleman.feature.createchallenge.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
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
import com.project.middleman.feature.createchallenge.viewmodel.CreateChallengeViewModel
import kotlinx.coroutines.delay


@Composable
fun CreateChallengeDescription(
    onSaveChallenge: () -> Unit,
    viewModel: CreateChallengeViewModel,
) {
    val context = LocalContext.current
    var challengeDescription by remember { mutableStateOf("") }
    var usefulMessage by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    val duration = Toast.LENGTH_SHORT

    var message = "Please fill all fields correctly."
    val toast = Toast.makeText(context, message, duration)

    // Character limit
    val maxCharacters = 200
    val currentCharCount = challengeDescription.length

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
                .wrapContentHeight()
        ) {
            val (constraintTitle, subtitle, textFieldTitle, charCount, tittleUseful, usefulInfo, button) = createRefs()

            // Title
            Text(
                "Wager Description",
                style = Typography.bodyLarge.copy(fontSize = 28.sp, color = colorBlack),
                modifier = Modifier.constrainAs(constraintTitle) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start)
                }
            )

            // Subtitle
            Text(
                "What is the bet about?",
                style = Typography.labelSmall.copy(fontSize = 16.sp, color = colorBlack),
                modifier = Modifier.constrainAs(subtitle) {
                    top.linkTo(constraintTitle.bottom, margin = 12.dp)
                    start.linkTo(parent.start)
                }
            )

            // Challenge description input
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
                text = challengeDescription,
                onValueChange = { newText ->
                    if (newText.length <= maxCharacters) challengeDescription = newText
                },
                placeholder = "Describe and state your terms",
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
                singleLine = false,
                maxLines = 5
            )

            // Character count
            Text(
                "$currentCharCount/$maxCharacters characters",
                style = Typography.labelSmall.copy(fontSize = 12.sp, color = colorBlack),
                modifier = Modifier.constrainAs(charCount) {
                    top.linkTo(textFieldTitle.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                }
            )

            // Access details title
            Text(
                "Access Details",
                style = Typography.labelSmall.copy(fontSize = 16.sp, color = colorBlack),
                modifier = Modifier.constrainAs(tittleUseful) {
                    top.linkTo(charCount.bottom, margin = 40.dp)
                    start.linkTo(parent.start)
                }
            )

            // Useful info input
            BorderlessTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(usefulInfo) {
                        top.linkTo(tittleUseful.bottom, margin = 10.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    },
                text = usefulMessage,
                onValueChange = { newText ->
                    if (newText.length <= maxCharacters) usefulMessage = newText
                },
                placeholder = "Shared privately with your participant (e.g., code or link)",
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
                singleLine = false,
                maxLines = 3
            )

            // Continue button
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(button) {
                        top.linkTo(usefulInfo.bottom, margin = 32.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                onClick = {
                    when {
                        challengeDescription.isBlank() -> toast.show()
                        else -> {
                            viewModel.description = challengeDescription
                            viewModel.usefulMessage = usefulMessage
                            onSaveChallenge()
                        }
                    }
                },
                text = "Continue",
            )

            // Request focus on first field
            LaunchedEffect(Unit) {
                delay(200)
                focusRequester.requestFocus()
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ChallengeDescriptionPreview() {
    MaterialTheme {
        CreateChallengeDescription(
            onSaveChallenge = { ->
                // For preview, we just print to log (won't actually be called here)
            },
            viewModel = hiltViewModel()
        )
    }
}
