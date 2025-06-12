package com.project.middleman.feature.openchallenges.presentation.uistate_handler

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.feature.openchallenges.viewmodel.OpenChallengeViewModel
import androidx.compose.runtime.getValue


@Composable
fun FetchChallengeResponseHandler(
    viewModel: OpenChallengeViewModel = hiltViewModel(),
    getChallenges: (result: List<Challenge>) -> Unit,
    onErrorMessage: (message: String) -> Unit
) {
    val openChallengeResponse by viewModel.challenges.collectAsState()

    when (openChallengeResponse) {
        is RequestState.Loading -> {
            // Show loading state if needed
        }

        is RequestState.Success -> {
            (openChallengeResponse as RequestState.Success<List<Challenge>>).data?.let {
                Log.d("DisplayChallengesData", it.toString())

                LaunchedEffect(it) {
                    getChallenges(it)
                }
            }
        }

        is RequestState.Error -> {
            val error = (openChallengeResponse as RequestState.Error).error
            LaunchedEffect(Unit) {
                onErrorMessage(error.message.toString())
                Log.d("DisplayChallengesData", error.message.toString())
                viewModel.setLoading(false)
            }
        }
    }
}
