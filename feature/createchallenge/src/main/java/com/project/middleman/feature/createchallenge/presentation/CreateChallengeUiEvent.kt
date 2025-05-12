package com.project.middleman.feature.createchallenge.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.middleman.core.source.data.RequestState
import com.project.middleman.feature.createchallenge.viewmodel.CreateChallengeViewModel
import com.stevdzasan.messagebar.MessageBarState

@Composable
fun OnCreateChallengeEvent(
    viewModel: CreateChallengeViewModel = hiltViewModel(),
    launch: (result: String) -> Unit,
    messageBarState: MessageBarState,
){
    when(val credManagerSignInResponse = viewModel.createChallengeResponse) {
        is RequestState.Loading -> {}
        is RequestState.Success -> credManagerSignInResponse.data?.let {
            Log.d("CredentialManagerSignSignIn", "")
            LaunchedEffect(it) {
                launch("Challenge created")
            }
        }
        is RequestState.Error -> LaunchedEffect(Unit) {
            messageBarState.addError(Exception(credManagerSignInResponse.error.message))
           // viewModel.setLoading(false)
        }
    }
}
