package com.project.middleman.feature.authentication.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.feature.authentication.viewmodel.AuthViewModel

@Composable
fun SignInWithGoogle(
    viewModel: AuthViewModel = hiltViewModel(),
    navigateToHomeScreen: (signedIn: Boolean) -> Unit
) {
    // Properly observe the StateFlow
    val signInWithGoogleResponse by viewModel.signInWithGoogleResponse.collectAsState()
    
    Log.d("SignInWithGoogle", "SignInWithGoogle recomposed with state: $signInWithGoogleResponse")
    
    // Store in local variable to enable smart cast
    val currentState = signInWithGoogleResponse
    
    when(currentState) {
        is RequestState.Loading -> {
            Log.d("SignInWithGoogle", "Loading state")
        }
        is RequestState.Success -> {
            Log.d("SignInWithGoogle", "Success state: ${currentState.data}")
            currentState.data?.let { signedIn ->
                LaunchedEffect(signedIn) {
                    Log.d("SignInWithGoogle", "Navigating with signedIn: $signedIn")
                    navigateToHomeScreen(signedIn)
                    // Reset state after navigation
                    viewModel.resetSignInWithGoogleState()
                }
            } ?: run {
                Log.d("SignInWithGoogle", "Success but no data")
                // Reset state if no data
                LaunchedEffect(Unit) {
                    viewModel.resetSignInWithGoogleState()
                }
            }
        }
        is RequestState.Error -> {
            Log.d("SignInWithGoogle", "Error state: ${currentState.error.message}")
            LaunchedEffect(Unit) {
                Log.d("SignInWithGoogle", "Error: ${currentState.error.message}")
                // Reset state after error
                viewModel.resetSignInWithGoogleState()
            }
        }
    }
}