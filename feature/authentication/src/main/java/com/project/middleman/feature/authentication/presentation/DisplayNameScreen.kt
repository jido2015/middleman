package com.project.middleman.feature.authentication.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.middleman.composables.button.CustomButton
import com.middleman.composables.textfield.DisplayNameBorderlessTextField
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.colorBlack
import com.project.middleman.feature.authentication.components.AddUserProfileWrapper
import com.project.middleman.feature.authentication.viewmodel.CreateProfileViewModel
import kotlinx.coroutines.delay

@Composable
fun DisplayNameScreen(
    viewModel: CreateProfileViewModel = hiltViewModel(),
    onSaveChallenge: () -> Unit,
){
    val usernameRegex = Regex("^[a-zA-Z0-9_]+$")

    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }

    var displayName by remember { mutableStateOf("") }

    val duration = Toast.LENGTH_SHORT

    val message = "Please fill all fields correctly."
    val toast = Toast.makeText(context, message, duration)


    // Character limit
    val maxCharacters = 20
    val currentCharCount = displayName.length


    AddUserProfileWrapper(
        viewModel = viewModel,
        onSuccess = {
            onSaveChallenge()
        },
        onErrorMessage = {
            Toast.makeText(context, it, duration).show()
        }
    )

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(16.dp)
    ) {
        val (constraintTitle, subtitle, textFieldTitle, charCount, button) = createRefs()

        Text(
            "What Should We Call You?",
            style = Typography.bodyLarge.copy(fontSize = 28.sp, color = colorBlack),
            modifier = Modifier.constrainAs(constraintTitle) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start)
            }
        )
        Text(
            "Set up your display name to make it easy for friends and mutuals to invite you to challenges.",
            style = Typography.labelSmall.copy(fontSize = 16.sp, color = colorBlack),
            modifier = Modifier.constrainAs(subtitle) {
                top.linkTo(constraintTitle.bottom, margin = 12.dp)
                start.linkTo(parent.start)
            }
        )

        DisplayNameBorderlessTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .constrainAs(textFieldTitle) {
                    top.linkTo(subtitle.bottom, margin = 28.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            text =displayName ,
            onValueChange = { newText ->
                if (newText.length <= maxCharacters && (newText.isEmpty() || usernameRegex.matches(newText))) {
                    displayName = newText
                }
            },
            placeholder = "e.g. CoolCat123",
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

        CustomButton(
            modifier = Modifier.fillMaxWidth()
                .constrainAs(button) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            onClick = {
                if (displayName.isNotBlank()
                ) {
                    viewModel.displayName = displayName
                    viewModel.addUserProfile()
                } else {
                    toast.show()
                }

            },
            text = "Next")

        // Request focus when the screen is displayed
        LaunchedEffect(Unit) {
            delay(200)
            focusRequester.requestFocus()
        }
    }
}
