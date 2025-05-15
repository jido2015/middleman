package com.project.middleman.feature.authentication.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.AuthCredential
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.feature.authentication.AuthViewModel
import com.stevdzasan.messagebar.MessageBarState
import java.lang.Exception

@Composable
fun CredentialManagerLogin(
    viewModel: AuthViewModel = hiltViewModel(),
    launch: (result: AuthCredential) -> Unit,
    messageBarState: MessageBarState,
) {
    when(val credManagerSignInResponse = viewModel.credentialManagerSignInResponse) {
        is RequestState.Loading -> {}
        is RequestState.Success -> credManagerSignInResponse.data?.let {
            Log.d("CredentialManagerSignSignIn", it.toString())
            LaunchedEffect(it) {
                launch(it)
            }
        }
        is RequestState.Error -> LaunchedEffect(Unit) {
            messageBarState.addError(Exception(credManagerSignInResponse.error.message))
            viewModel.setLoading(false)
        }
    }
}
