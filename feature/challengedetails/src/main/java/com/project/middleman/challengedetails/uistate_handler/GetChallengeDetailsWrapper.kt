package com.project.middleman.challengedetails.uistate_handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.middleman.challengedetails.viewmodel.ChallengeDetailsViewModel
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.sealedclass.RequestState

@Composable
fun GetChallengeDetailsWrapper(
    viewModel: ChallengeDetailsViewModel = hiltViewModel(),
    onSuccess: (Challenge) -> Unit,
    onErrorMessage: (String) -> Unit,
) {
    val response by viewModel.getChallengeState.collectAsState()

    LaunchedEffect(response) {
        when (response) {
            is RequestState.Error -> {
                viewModel.loadingState.value = false
                val error = (response as RequestState.Error).error
                onErrorMessage(error.message.toString())
            }

            RequestState.Loading -> viewModel.loadingState.value = true

            is RequestState.Success<Challenge> -> {
                viewModel.loadingState.value = false
                val challenge = (response as RequestState.Success<Challenge>).data
                    ?: return@LaunchedEffect // Skip if null
                onSuccess(challenge)
            }
        }
}
}