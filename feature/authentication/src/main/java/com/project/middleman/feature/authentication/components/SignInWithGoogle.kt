package com.project.middleman.feature.authentication.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.feature.authentication.viewmodel.AuthViewModel

@Composable
fun SignInWithGoogle(
    viewModel: AuthViewModel = hiltViewModel(),
    navigateToHomeScreen: (signedIn: Boolean) -> Unit
) {
    when(val signInWithGoogleResponse = viewModel.signInWithGoogleResponse) {
        is RequestState.Loading -> {
            Log.d("LoadingState", "SignInWithGoogle: Loading")
        }
        is RequestState.Success -> signInWithGoogleResponse.data?.let { signedIn ->
            viewModel.setLoading(false)
            LaunchedEffect(signedIn) {
                navigateToHomeScreen(signedIn)
            }
        }
        is RequestState.Error -> LaunchedEffect(Unit) {
            viewModel.setLoading(false)
            Log.d("SignInWithGoogle", "SignInWithGoogle: ${signInWithGoogleResponse.error.message}")
        }
    }
}