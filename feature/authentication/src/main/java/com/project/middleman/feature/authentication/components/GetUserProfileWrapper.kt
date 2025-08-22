package com.project.middleman.feature.authentication.components

import android.util.Log
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
                val error = (response as RequestState.Error).error
                Log.d("GetUserProfileWrapper", error.message.toString())
                onErrorMessage(error.message.toString())
            }
            RequestState.Loading -> {
            }
            is RequestState.Success -> (response as RequestState.Success<UserDTO>).data?.let {
                onSuccess()
                viewModel.syncUserFromFirebase(it)
            }
        }
    }
}

