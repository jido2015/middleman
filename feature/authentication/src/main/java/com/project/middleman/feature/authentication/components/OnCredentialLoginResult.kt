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
    // Properly observe the StateFlow
    val credManagerSignInResponse by viewModel.credentialManagerSignInResponse.collectAsState()
    
    Log.d("CredentialManager", "OnCredentialLoginResult recomposed with state: $credManagerSignInResponse")
    
    // Add LaunchedEffect to monitor state changes
    LaunchedEffect(credManagerSignInResponse) {
        Log.d("CredentialManager", "LaunchedEffect triggered with state: $credManagerSignInResponse")
    }
    
    when(credManagerSignInResponse) {
        is RequestState.Loading -> {
            Log.d("CredentialManager", "Loading state")
        }
        is RequestState.Success -> {
            Log.d("CredentialManager", "Success state: ${(credManagerSignInResponse as RequestState.Success<AuthCredential>).data}")
            (credManagerSignInResponse as RequestState.Success<AuthCredential>).data?.let { credential ->
                LaunchedEffect(credential) {
                    Log.d("CredentialManager", "Launching with credential: ${credential.provider}")
                    launch(credential)
                    // Reset state after launching
                    viewModel.resetCredentialManagerState()
                }
            } ?: run {
                Log.d("CredentialManager", "Success but no credential data")
                // Reset state if no data
                LaunchedEffect(Unit) {
                    viewModel.resetCredentialManagerState()
                }
            }
        }
        is RequestState.Error -> {
            Log.d("CredentialManager", "Error state: ${(credManagerSignInResponse as RequestState.Error).error.message}")
            LaunchedEffect(Unit) {
                onError((credManagerSignInResponse as RequestState.Error).error.message.toString())
                viewModel.setLoading(false)
                // Reset state after error
                viewModel.resetCredentialManagerState()
            }
        }
    }
}
