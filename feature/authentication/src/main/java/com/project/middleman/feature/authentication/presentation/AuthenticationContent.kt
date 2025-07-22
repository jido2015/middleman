package com.project.middleman.feature.authentication.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.middleman.composables.R
import com.middleman.composables.button.CustomButton
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.colorBlack
import com.project.middleman.designsystem.themes.white

@Composable
fun AuthenticationContent(
    loadingState: MutableState<Boolean>,
    onButtonClicked: () -> Unit
) {
    // Animate on first appearance
    var imageVisible by remember { mutableStateOf(false) }

    // Trigger animation once when the composable enters composition
    LaunchedEffect(Unit) {
        imageVisible = true
    }

    Box(
        modifier = Modifier
            .background(white)
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        // Centered content
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = imageVisible && !loadingState.value, // show on enter and when not loading
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                Image(
                    painter = painterResource(R.drawable.app_icon),
                    contentDescription = "offer icon",
                    modifier = Modifier.size(200.dp),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                textAlign = TextAlign.Center,
                text = "Challenge your crew.\nWin more than bragging rights.",
                style = Typography.titleLarge.copy(fontSize = 20.sp, color = colorBlack)
            )
        }

        // Button pinned at the bottom
        CustomButton(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp),
            onClick = { onButtonClicked() },
            text = "Setup With Google Account"
        )
    }
}


@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun GettingStartedPreview() {
    AuthenticationContent(loadingState = mutableStateOf(true), onButtonClicked = {})
}