package com.project.middleman.feature.authentication

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.project.middleman.core.source.data.RequestState
import com.project.middleman.core.source.domain.authentication.repository.AuthCredentialResponse
import com.project.middleman.core.source.domain.authentication.repository.AuthRepository
import com.project.middleman.core.source.domain.authentication.repository.SignInWithGoogleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {

    var loadingState = mutableStateOf(false)
    fun setLoading(loading: Boolean){
        loadingState.value = loading
    }

    val isUserAuthenticated get() = repo.isUserAuthenticatedInFirebase

    var credentialManagerSignInResponse by mutableStateOf<AuthCredentialResponse>(RequestState.Success(null))
        private set
    var signInWithGoogleResponse by mutableStateOf<SignInWithGoogleResponse>(RequestState.Success(false))
        private set

    fun credentialManagerSignIn() =
        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                credentialManagerSignInResponse = RequestState.Loading
                credentialManagerSignInResponse = repo.credentialManagerWithGoogle()
            }
        }

    fun signInWithGoogle(googleCredential: AuthCredential) = viewModelScope.launch {
        Log.d("signInWithGoogle","Checking Firebase Login")
        signInWithGoogleResponse = RequestState.Loading
        signInWithGoogleResponse = repo.firebaseSignInWithGoogle(googleCredential)
    }

}