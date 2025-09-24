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

    when (response) {
        is RequestState.Error -> {
            val error = (response as RequestState.Error).error
            Log.d("GetUserProfileWrapper", error.message.orEmpty())
            LaunchedEffect(error) {
                onErrorMessage(error.message.orEmpty())
            }
        }
        is RequestState.Success -> {
            val user = (response as RequestState.Success<UserDTO>).data
            user?.let {
                LaunchedEffect(it) {
                    viewModel.syncUserFromFirebase(it)
                    onSuccess()
                }
            }
        }
        RequestState.Loading -> {
            // You can show a loader here if needed
        }
    }
}

