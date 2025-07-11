package com.project.middleman.challengedetails.presentation.uistate_handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.middleman.challengedetails.viewmodel.ChallengeDetailsViewModel
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.sealedclass.RequestState

@Composable
fun ChallengeDetailsWrapper(
    viewModel: ChallengeDetailsViewModel = hiltViewModel(),
    onSuccess: (Challenge) -> Unit,
    onSuccessMessage: () -> Unit,
    onErrorMessage: (String) -> Unit,
    ) {
    val response by viewModel.updateChallenge.collectAsState()


    LaunchedEffect(response) {
        when (response) {
            is RequestState.Error -> {
                viewModel.loadingState.value = false
                val error = (response as RequestState.Error).error
                onErrorMessage(error.message.toString())
            }

            RequestState.Loading -> viewModel.loadingState.value = true

            is RequestState.Success<*> -> {
                viewModel.loadingState.value = false
                val challenge = (response as RequestState.Success<*>).data
                onSuccessMessage()
                onSuccess(challenge as Challenge)
            }
        }

    }

}