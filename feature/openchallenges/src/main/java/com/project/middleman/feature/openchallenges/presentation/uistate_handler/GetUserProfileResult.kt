package com.project.middleman.feature.openchallenges.presentation.uistate_handler

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.middleman.core.source.data.model.UserDTO
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.feature.openchallenges.viewmodel.OpenChallengeViewModel


@Composable
fun GetUserProfileResult(
    viewModel: OpenChallengeViewModel = hiltViewModel(),
    launch: (result: UserDTO) -> Unit,
    onError: (message: String) -> Unit
) {
    when(val credManagerSignInResponse = viewModel.userProfileResponse) {
        is RequestState.Loading -> {}
        is RequestState.Success -> credManagerSignInResponse.data?.let {
            Log.d("CredentialManagerSignSignIn", it.toString())
            LaunchedEffect(it) {
                launch(it)
            }
        }
        is RequestState.Error -> LaunchedEffect(Unit) {
            onError(credManagerSignInResponse.error.message.toString())
            viewModel.setLoading(false)
        }
    }
}
