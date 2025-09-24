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
            val (constraintTitle, subtitle, textFieldTitle, charCount, dropDown, checkbox, button) = createRefs()

            // Title
            Text(
                "Wager Name",
                style = Typography.bodyLarge.copy(fontSize = 28.sp, color = colorBlack),
                modifier = Modifier.constrainAs(constraintTitle) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start)
                }
            )

            // Subtitle
            Text(
                "Name your wager in few words",
                style = Typography.labelSmall.copy(fontSize = 16.sp, color = colorBlack),
                modifier = Modifier.constrainAs(subtitle) {
                    top.linkTo(constraintTitle.bottom, margin = 12.dp)
                    start.linkTo(parent.start)
                }
            )

            // Wager name input
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
                    if (newText.length <= maxCharacters) challengeTitle = newText
                },
                placeholder = "First player to score a goal?",
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
            )

            // Character count
            Text(
                "$currentCharCount/$maxCharacters characters",
                style = Typography.labelSmall.copy(fontSize = 12.sp, color = colorBlack),
                modifier = Modifier.constrainAs(charCount) {
                    top.linkTo(textFieldTitle.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                }
            )

            // Category dropdown
            CustomDropdownMenu(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(dropDown) {
                        top.linkTo(charCount.bottom, margin = 24.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                selectedOption = selectedCategory,
                onOptionSelected = { selectedCategory = it },
                options = category
            )

            // Visibility checkbox
            VisibilityCheckbox(
                visibilityState = isInviteOnly,
                modifier = Modifier.constrainAs(checkbox) {
                    top.linkTo(dropDown.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            // Optional Gaming field
            if (selectedCategory == "Gaming") {
                Text(
                    "Gaming detail (optional)",
                    style = Typography.labelSmall.copy(fontSize = 14.sp, color = colorBlack),
                    modifier = Modifier.constrainAs(createRef()) {
                        top.linkTo(checkbox.bottom, margin = 16.dp)
                        start.linkTo(parent.start)
                    }
                )
                // You can add another TextField here with a separate ref
            }

            // Continue button
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(button) {
                        top.linkTo(checkbox.bottom, margin = 32.dp)
                        start.linkTo(parent.start)
                        end.linkTo ( parent.end)
                    },
                onClick = {
                    when {
                        challengeTitle.isBlank() -> toast.show()
                        selectedCategory.isBlank() ||
                                selectedCategory == "Select a category for this wager" -> toast.show()
                        else -> {
                            viewModel?.title = challengeTitle
                            viewModel?.category = selectedCategory
                            viewModel?.visibility = isInviteOnly.value
                            viewModel?.selectedTimeInMillis = selectedTimeInMillis
                            onSaveChallenge()
                        }
                    }
                },
                text = "Continue",
            )

            // Focus on first field
            LaunchedEffect(Unit) {
                delay(200)
                focusRequester.requestFocus()
            }
        }
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
