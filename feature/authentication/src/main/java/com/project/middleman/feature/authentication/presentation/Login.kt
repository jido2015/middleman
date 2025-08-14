package com.project.middleman.feature.authentication.presentation


import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.project.middleman.feature.authentication.components.GetUserProfileWrapper
import com.project.middleman.feature.authentication.viewmodel.AuthViewModel
import com.project.middleman.feature.authentication.components.OnCredentialLoginResult
import com.project.middleman.feature.authentication.components.SignInWithGoogle

@Composable
fun AuthenticationScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
    gotoProfileSetup: () -> Unit
){
    AuthenticationContent(
        loadingState = viewModel.loadingState,
        onButtonClicked = {
            viewModel.setLoading(true)
            viewModel.credentialManagerSignIn()
        })


    fun launch(googleCredentials: AuthCredential) {
        try {
            Log.d("credentialsData", "launch")

            viewModel.setLoading(false)
            viewModel.signInWithGoogle(googleCredentials)
        } catch (it: ApiException) {
            Log.d("credentialsData", it.toString())

            viewModel.setLoading(false)
            print(it)
        }
    }


    GetUserProfileWrapper(
        viewModel = viewModel,
        onSuccess = {
            viewModel.setLoading(false)
        },
        onErrorMessage = {
            Log.d("credentialsData", it)
            viewModel.setLoading(false)
            gotoProfileSetup()
        }
    )


    OnCredentialLoginResult(
        onError = {
            Log.d("credentialsData", it.toString())
            viewModel.setLoading(false)
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

