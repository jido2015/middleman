package com.project.middleman.feature.authentication.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun AuthenticationContent(
    loadingState: MutableState<Boolean>,
    onButtonClicked: () -> Unit
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 20.dp, start = 20.dp)
    ){

        val (_, _, _) = createRefs()


        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Challenge your crew." +
                "Win more than bragging rights.")



        //Getting started button
        GettingStartedButton(loadingState, onButtonClicked)

    }
}

@Composable
private fun GettingStartedButton(loadingState: MutableState<Boolean>, onButtonClicked: ()-> Unit) {

    Column(modifier = Modifier
        .padding(bottom = 40.dp, top = 27.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = { onButtonClicked()},
                shape = RoundedCornerShape(20)
            ) {
                Text(
                    text = "Login"
                )
            }
        }

    }
}


@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun GettingStartedPreview() {
    AuthenticationContent(loadingState = mutableStateOf(true), onButtonClicked = {})
}