package com.project.middleman.feature.createchallenge.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.feature.createchallenge.viewmodel.CreateChallengeViewModel

@Composable
fun OnCreateChallengeEvent(
    viewModel: CreateChallengeViewModel,
    launch: (result: String) -> Unit,
){
    when(val onCreateChallengeResponse = viewModel.createChallengeResponse) {
        is RequestState.Loading -> {}
        is RequestState.Success -> onCreateChallengeResponse.data?.let {
            Log.d("onCreateChallengeResponse", "Created")
            LaunchedEffect(it) {
                launch("Challenge Created")
            }
        }
        is RequestState.Error -> LaunchedEffect(Unit) {

            Log.d("onCreateChallengeResponse", "${onCreateChallengeResponse.error}")
            // Exception(onCreateChallengeResponse.error.message)
           // viewModel.setLoading(false)
        }
    }
}
