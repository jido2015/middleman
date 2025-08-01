package com.project.middleman.feature.authentication.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.AuthCredential
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.feature.authentication.AuthViewModel

@Composable
fun OnCredentialLoginResult(
    viewModel: AuthViewModel = hiltViewModel(),
    launch: (result: AuthCredential) -> Unit,
    onError: (message: String) -> Unit
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
            onError(credManagerSignInResponse.error.message.toString())
            viewModel.setLoading(false)
        }
    }
}
