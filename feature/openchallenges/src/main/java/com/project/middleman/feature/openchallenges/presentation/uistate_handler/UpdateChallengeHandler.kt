package com.project.middleman.feature.openchallenges.presentation.uistate_handler

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.feature.openchallenges.viewmodel.OpenChallengeViewModel
import com.stevdzasan.messagebar.MessageBarState

@Composable
fun UpdateChallengeResponseHandler(
    viewModel: OpenChallengeViewModel = hiltViewModel(),
    messageBarState: MessageBarState
    ){
    val updateChallengeResponse by viewModel.updateChallenge.collectAsState()
    LaunchedEffect(updateChallengeResponse) { // ✅ only runs once per challenge ID
        when(updateChallengeResponse){
            is RequestState.Error -> {
                viewModel.loadingState.value = false
                val error = (updateChallengeResponse as RequestState.Error).error
                messageBarState.addError(Exception(error.message))
            }
            RequestState.Loading -> viewModel.loadingState.value = true
            is RequestState.Success<*> -> {
                viewModel.loadingState.value = false
                val challenge = (updateChallengeResponse as RequestState.Success<Challenge>).data!!
                messageBarState.addSuccess(" You have accepted the challenge")
                Log.d("UpdateChallengeResponseHandler", challenge.toString())
                viewModel.startListeningToChallengeUpdate(challenge)

            }
        }

    }
}