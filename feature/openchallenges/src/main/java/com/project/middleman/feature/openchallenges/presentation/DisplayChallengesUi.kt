package com.project.middleman.feature.openchallenges.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.feature.openchallenges.viewmodel.OpenChallengeViewModel
import com.stevdzasan.messagebar.MessageBarState

@Composable
fun DisplayChallenges(
    viewModel: OpenChallengeViewModel = hiltViewModel(),
    getChallenges: (result: List<Challenge>) -> Unit,
    messageBarState: MessageBarState,
) {
    when(val openChallengeResponse = viewModel.openChallengeResponse) {
        is RequestState.Loading -> {}
        is RequestState.Success -> openChallengeResponse.data?.let {
            Log.d("DisplayChallengesData", it.toString())
            LaunchedEffect(it) {
                getChallenges(it)
            }
        }
        is RequestState.Error -> LaunchedEffect(Unit) {
            messageBarState.addError(Exception(openChallengeResponse.error.message))
            Log.d("DisplayChallengesData", openChallengeResponse.error.message.toString())

            viewModel.setLoading(false)
        }
    }
}
