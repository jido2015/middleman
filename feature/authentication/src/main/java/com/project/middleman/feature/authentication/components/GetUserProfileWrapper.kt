package com.project.middleman.feature.authentication.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.project.middleman.core.source.data.model.UserDTO
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.feature.authentication.viewmodel.AuthViewModel


@Composable
fun GetUserProfileWrapper(
    viewModel: AuthViewModel,
    onSuccess: () -> Unit,
    onErrorMessage: (String) -> Unit,
) {
    val response by viewModel.getUserProfileState.collectAsState()

    LaunchedEffect(response) {
        when (response) {
            is RequestState.Error -> {
                viewModel.loadingState.value = false
                val error = (response as RequestState.Error).error
                onErrorMessage(error.message.toString())
            }
            RequestState.Loading -> viewModel.loadingState.value = true
            is RequestState.Success -> (response as RequestState.Success<UserDTO>).data?.let {
                onSuccess()
                viewModel.isUserAuthenticated
                viewModel.syncUserFromFirebase(it)
            }
        }
    }
}

