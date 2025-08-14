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
    Log.d("LoginScreen", "=== AuthenticationScreen START ===")
    Log.d("LoginScreen", "AuthenticationScreen recomposed")
    
    // Add a side effect to track when this screen is created
    LaunchedEffect(Unit) {
        Log.d("LoginScreen", "AuthenticationScreen LaunchedEffect(Unit) - screen is active")
    }
    
    // Add DisposableEffect to track screen lifecycle
    DisposableEffect(Unit) {
        Log.d("LoginScreen", "AuthenticationScreen DisposableEffect - screen created")
        onDispose {
            Log.d("LoginScreen", "AuthenticationScreen DisposableEffect - screen disposed")
        }
    }
    
    AuthenticationContent(
        loadingState = viewModel.loadingState,
        onButtonClicked = {
            Log.d("LoginScreen", "Button clicked, starting credential manager sign in")
            viewModel.setLoading(true)
            viewModel.resetCredentialManagerState() // Reset state before starting
            viewModel.credentialManagerSignIn()
        })


    fun launch(googleCredentials: AuthCredential) {
        Log.d("LoginScreen", "Launch function called with credential: ${googleCredentials.provider}")
        try {
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
            Log.d("LoginScreen", "GetUserProfileWrapper onSuccess called")
            viewModel.setLoading(false)
        },
        onErrorMessage = {
            Log.d("credentialsData", it)
            viewModel.setLoading(false)
            gotoProfileSetup()
        }
    )

    Log.d("LoginScreen", "About to call OnCredentialLoginResult")
    
    // Add a SideEffect to track when this composable is called
    SideEffect {
        Log.d("LoginScreen", "SideEffect: About to call OnCredentialLoginResult composable")
    }
    
    OnCredentialLoginResult(
        onError = {
            Log.d("credentialsData", it)
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
    
    Log.d("LoginScreen", "=== AuthenticationScreen END ===")
}

