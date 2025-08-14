package com.project.middleman.feature.authentication.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.AuthCredential
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.feature.authentication.viewmodel.AuthViewModel

@Composable
fun OnCredentialLoginResult(
    viewModel: AuthViewModel = hiltViewModel(),
    launch: (result: AuthCredential) -> Unit,
    onError: (message: String) -> Unit
) {

    val credManagerSignInResponse by viewModel.credentialManagerSignInResponse.collectAsState()

    LaunchedEffect(credManagerSignInResponse) {
        when(credManagerSignInResponse) {
            is RequestState.Loading -> {}
            is RequestState.Success -> {
                Log.d("CredentialManagerSignSignIn","Success state: ${(credManagerSignInResponse as RequestState.Success<AuthCredential>).data}")
                (credManagerSignInResponse as RequestState.Success<AuthCredential>).data?.let {
                    Log.d("CredentialManagerSignSignIn","Success:")
                    launch(it)
                }

            }
            is RequestState.Error -> {
                Log.d("CredentialManagerSignSignIn","Success:")

                Log.d("CredentialManagerSignSignIn", (credManagerSignInResponse as RequestState.Error).error.message.toString())
                onError((credManagerSignInResponse as RequestState.Error).error.message.toString())
                viewModel.setLoading(false)
            }
        }
    }

}
