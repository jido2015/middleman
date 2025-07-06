package com.project.middleman.feature.authentication

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.project.middleman.core.source.data.DispatchProvider
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.core.source.domain.authentication.repository.AuthCredentialResponse
import com.project.middleman.core.source.domain.authentication.repository.AuthRepository
import com.project.middleman.core.source.domain.authentication.repository.SignInWithGoogleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val dispatchProvider: DispatchProvider,
    private val firebaseAuth: FirebaseAuth
): ViewModel() {

   // val isUserAuthenticated  get() = repo.isUserAuthenticatedInFirebase

    private val _isUserAuthenticated = MutableStateFlow(false)
    val isUserAuthenticated: StateFlow<Boolean> = _isUserAuthenticated

    private val authStateListener = FirebaseAuth.AuthStateListener { auth ->
        _isUserAuthenticated.value = auth.currentUser != null
        Log.d("AuthCheck", "User: ${auth.currentUser?.uid}")
    }

    init {
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    override fun onCleared() {
        super.onCleared()
        firebaseAuth.removeAuthStateListener(authStateListener)
    }


    var loadingState = mutableStateOf(false)
    fun setLoading(loading: Boolean){
        loadingState.value = loading
    }

    var credentialManagerSignInResponse by mutableStateOf<AuthCredentialResponse>(RequestState.Success(null))
        private set
    var signInWithGoogleResponse by mutableStateOf<SignInWithGoogleResponse>(RequestState.Success(false))
        private set

    fun credentialManagerSignIn() =
        viewModelScope.launch {
            Log.d("credentialManagerSignIn","Checking credentialManagerSignIn")

            withContext(dispatchProvider.io) {
                credentialManagerSignInResponse = RequestState.Loading
                credentialManagerSignInResponse = repo.credentialManagerWithGoogle()
            }
        }

    fun signInWithGoogle(googleCredential: AuthCredential) = viewModelScope.launch {
        Log.d("signInWithGoogle","Checking Firebase Login")
        signInWithGoogleResponse = RequestState.Loading
        signInWithGoogleResponse = repo.firebaseSignInWithGoogle(googleCredential)
    }


    fun onLoginComplete() {
        Log.d("onLoginComplete", "Refreshing auth state")
        repo.refreshAuthState()
    }

}
