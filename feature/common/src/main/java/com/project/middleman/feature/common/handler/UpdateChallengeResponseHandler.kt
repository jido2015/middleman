package com.project.middleman.feature.common.handler

import androidx.compose.runtime.MutableState
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.sealedclass.RequestState

fun handleUpdateChallengeResponse(
    response: RequestState<Challenge>,
    loadingState: MutableState<Boolean>,
    onSuccess: (Challenge) -> Unit,
    onError: (Exception) -> Unit
) {
    when (response) {
        is RequestState.Error -> {
            loadingState.value = false
            val error = response.error
            onError(Exception(error.message))
        }

        RequestState.Loading -> loadingState.value = true

        is RequestState.Success<*> -> {
            loadingState.value = false
            val challenge = response.data
            onSuccess(challenge as Challenge)
        }
    }
}
