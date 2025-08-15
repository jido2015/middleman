package com.project.middleman.feature.authentication.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.middleman.core.common.appstate.viewmodel.AppStateViewModel
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.feature.authentication.viewmodel.CreateProfileViewModel

@Composable
fun AddUserProfileWrapper(
    viewModel: CreateProfileViewModel,
    onSuccess: () -> Unit,
    onErrorMessage: (String) -> Unit,
) {
    val response by viewModel.addUserProfile.collectAsState()

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
                onSuccess()
            }
        }
    }
}

