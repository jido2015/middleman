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
    LaunchedEffect(Unit) {
        viewModel.createChallengeResponse.collect { result ->
            launch(result) // triggered once per emission (success or error)
        }
    }
}
