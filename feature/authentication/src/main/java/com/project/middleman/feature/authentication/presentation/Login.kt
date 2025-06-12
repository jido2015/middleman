package com.project.middleman.feature.authentication.presentation


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.project.middleman.feature.authentication.AuthViewModel
import com.project.middleman.feature.authentication.components.CredentialManagerLogin
import com.project.middleman.feature.authentication.components.SignInWithGoogle
import com.stevdzasan.messagebar.MessageBarState
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthenticationScreen(
    messageBarState: MessageBarState,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
    onSignIn: () -> Unit
){
    AuthenticationContent(
        loadingState = viewModel.loadingState,
        onButtonClicked = {
            viewModel.setLoading(true)
            viewModel.credentialManagerSignIn()
        })


    fun launch(googleCredentials: AuthCredential) {
        try {

            messageBarState.addSuccess("Successfully Authenticated!")
            viewModel.setLoading(false)
            viewModel.signInWithGoogle(googleCredentials)
        } catch (it: ApiException) {
            Log.d("credentialsData", it.toString())
            messageBarState.addError(it)

            viewModel.setLoading(false)
            print(it)
        }
    }

    CredentialManagerLogin(
        onError = {
            messageBarState.addError(Exception(it))
        },
        launch = {
            launch(it)
        })

    SignInWithGoogle(
        navigateToHomeScreen = { signedIn ->
            if (signedIn) {
                onSignIn()
                Log.d("credentialsData", "signed in")
            }
        }
    )
}

