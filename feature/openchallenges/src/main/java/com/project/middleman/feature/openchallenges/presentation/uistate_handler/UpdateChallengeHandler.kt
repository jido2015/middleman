package com.project.middleman.feature.openchallenges.presentation.uistate_handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.middleman.feature.common.handler.handleUpdateChallengeResponse
import com.project.middleman.feature.openchallenges.viewmodel.OpenChallengeViewModel

@Composable
fun UpdateChallengeWrapper(
    viewModel: OpenChallengeViewModel = hiltViewModel(),
    onSuccessMessage: () -> Unit,
    onErrorMessage: (String) -> Unit,
) {
    val response by viewModel.updateChallenge.collectAsState()

    LaunchedEffect(response) {
        handleUpdateChallengeResponse(
            response = response,
            loadingState = viewModel.loadingState,
            onSuccess = { challenge ->
                onSuccessMessage()
                viewModel.startListeningToChallengeUpdate(challenge)
            },
            onError = { message ->
                onErrorMessage(message.toString())
            }
        )
    }
}
