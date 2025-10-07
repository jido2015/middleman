package com.project.middleman.feature.authentication.presentation


import android.util.Log
import androidx.compose.runtime.*
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.project.middleman.feature.authentication.components.GetUserProfileWrapper
import com.project.middleman.feature.authentication.viewmodel.AuthViewModel
import com.project.middleman.feature.authentication.components.OnCredentialLoginResult
import com.project.middleman.feature.authentication.components.SignInWithGoogle

@Composable
fun AuthenticationScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    gotoProfileSetup: () -> Unit,
    proceedToDashBoard: () -> Unit
){

    AuthenticationContent(
        loadingState = viewModel.loadingState,
        onButtonClicked = {
            viewModel.credentialManagerSignIn()
        },
        viewModel = viewModel,
    )

    fun launch(googleCredentials: AuthCredential) {
        try {
            viewModel.signInWithGoogle(googleCredentials)
        } catch (it: ApiException) {
            print(it)
        }
    }

    GetUserProfileWrapper(
        viewModel = viewModel,
        onSuccess = {
            proceedToDashBoard()
        },
        onErrorMessage = {
            gotoProfileSetup()
        }
    )
    
    OnCredentialLoginResult(
        onError = {
        },
        launch = {
            launch(it)
        })

    SignInWithGoogle(
        navigateToHomeScreen = { signedIn ->
            if (signedIn) {
                viewModel.getUserProfile()
                Log.d("credentialsData", "signed in")
            }
        }
    )
}

