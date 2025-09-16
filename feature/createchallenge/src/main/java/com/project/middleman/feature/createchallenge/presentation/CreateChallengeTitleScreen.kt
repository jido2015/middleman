package com.project.middleman.feature.createchallenge.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
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
import com.project.middleman.core.common.getSelectedTimeInMillis
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.colorBlack
import com.project.middleman.feature.createchallenge.components.ComposeTimeInputDialogCustom
import com.project.middleman.feature.createchallenge.components.VisibilityCheckbox
import com.project.middleman.feature.createchallenge.viewmodel.CreateChallengeViewModel
import kotlinx.coroutines.delay

@Composable
fun CreateChallengeTitle(
    onSaveChallenge: () -> Unit,
    viewModel: CreateChallengeViewModel? = null,
) {
    val context = LocalContext.current
    var challengeTitle by remember { mutableStateOf("") }
    var showTIme by remember { mutableStateOf(false) }
    var selectedTimeInMillis by remember { mutableLongStateOf(System.currentTimeMillis()) }
    val isInviteOnly = remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val duration = Toast.LENGTH_SHORT
    var selectedCategory by remember { mutableStateOf("Select a category for this wager") }
    val category = listOf("Sports",
        "Gaming", "Politics",
        "Stocks", "Entertainment", "Crypto", "Others")

    val message = "Please fill all fields correctly."
    val toast = Toast.makeText(context, message, duration)

    // Character limit
    val maxCharacters = 100
    val currentCharCount = challengeTitle.length

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(16.dp)
    ) {
        val (constraintTitle, subtitle, textFieldTitle, charCount, button, dropDown, checkbox) = createRefs()

        Text(
            "Wager Name",
            style = Typography.bodyLarge.copy(fontSize = 28.sp, color = colorBlack),
            modifier = Modifier.constrainAs(constraintTitle) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start)
            }
        )

        Text(
            "Name your wager in few words",
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
            text = challengeTitle,
            onValueChange = { newText ->
                // Only allow input if it's within the character limit
                if (newText.length <= maxCharacters) {
                    challengeTitle = newText
                }
            },
            placeholder = "First player to score a goal?",
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
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

        CustomDropdownMenu(
            modifier = Modifier.fillMaxWidth().constrainAs(dropDown) {
                top.linkTo(charCount.bottom, margin = 70.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            selectedOption = selectedCategory,
            onOptionSelected = { selectedCategory = it },
            options = category
        )
        VisibilityCheckbox(visibilityState = isInviteOnly,
            modifier = Modifier.constrainAs(checkbox) {
            top.linkTo(dropDown.bottom, margin = 20.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })


        CustomButton(
            modifier = Modifier.fillMaxWidth()
                .constrainAs(button) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            onClick = {
                if (challengeTitle.isNotBlank()
                    && selectedCategory.isNotBlank() &&
                    selectedCategory != "Select a category for this wager"
                ) {
                    viewModel?.title = challengeTitle
                    viewModel?.category = selectedCategory
                    viewModel?.visibility = isInviteOnly.value
                    viewModel?.selectedTimeInMillis = selectedTimeInMillis
                    onSaveChallenge()
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


    // Show time picker
    if (showTIme) {
        ComposeTimeInputDialogCustom(
            onConfirm = { hour, minute ->
                selectedTimeInMillis = getSelectedTimeInMillis(hour, minute)
                showTIme = false
            },
            onDismiss = { showTIme = false })
    }

}

@Preview(showBackground = true)
@Composable
fun CreateChallengeScreenPreview() {
    MaterialTheme {
        CreateChallengeTitle(
            onSaveChallenge = { ->
                // For preview, we just print to log (won't actually be called here)
            }
        )
    }
}
