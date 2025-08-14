package com.project.middleman.feature.authentication.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.project.middleman.core.source.data.DispatchProvider
import com.project.middleman.core.source.data.local.UserLocalDataSource
import com.project.middleman.core.source.data.local.entity.UserEntity
import com.project.middleman.core.source.data.mapper.toEntity
import com.project.middleman.core.source.data.model.UserDTO
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.core.source.domain.authentication.repository.AuthCredentialResponse
import com.project.middleman.core.source.domain.authentication.repository.AuthRepository
import com.project.middleman.core.source.domain.authentication.repository.GetUserProfileResponse
import com.project.middleman.core.source.domain.authentication.repository.SignInWithGoogleResponse
import com.project.middleman.core.source.domain.authentication.usecase.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val local: UserLocalDataSource,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val dispatchProvider: DispatchProvider,
    private val firebaseAuth: FirebaseAuth
): ViewModel() {

    var loadingState = mutableStateOf(false)
    fun setLoading(loading: Boolean){
        loadingState.value = loading
    }

    val currentUser: StateFlow<UserEntity?> =
        local.observeCurrentUser()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)


    private val _getUserProfile = MutableStateFlow<GetUserProfileResponse>(RequestState.Loading)
    val getUserProfileState: StateFlow<GetUserProfileResponse> = _getUserProfile

    private val _isUserAuthenticated = MutableStateFlow(false)
    val isUserAuthenticated: StateFlow<Boolean> = _isUserAuthenticated

    private val authStateListener = FirebaseAuth.AuthStateListener { auth ->
        if(currentUser.value != null) {
            _isUserAuthenticated.value = firebaseAuth.currentUser != null
            Log.d("AuthCheck", "User: ${auth.currentUser?.uid}")

        }
    }

    override fun onCleared() {
        super.onCleared()
        firebaseAuth.removeAuthStateListener(authStateListener)
    }


    private val _credentialManagerSignInResponse = MutableStateFlow<AuthCredentialResponse>(RequestState.Loading)
    val credentialManagerSignInResponse: StateFlow<AuthCredentialResponse> = _credentialManagerSignInResponse
    
    private val _signInWithGoogleResponse = MutableStateFlow<SignInWithGoogleResponse>(RequestState.Loading)
    val signInWithGoogleResponse: StateFlow<SignInWithGoogleResponse> = _signInWithGoogleResponse

    init {
        Log.d("AuthViewModel", "=== AuthViewModel constructor called ===")
        Log.d("AuthViewModel", "AuthViewModel created with repo: $repo")
        
        // Add state observer for debugging
        viewModelScope.launch {
            _credentialManagerSignInResponse.collect { state ->
                Log.d("AuthViewModel", "StateFlow changed to: $state")
            }
        }
        
        // Add another observer to track when the StateFlow is collected
        viewModelScope.launch {
            Log.d("AuthViewModel", "Starting to observe credentialManagerSignInResponse StateFlow")
            _credentialManagerSignInResponse.collect { state ->
                Log.d("AuthViewModel", "Direct StateFlow observer received: $state")
            }
        }
    }

    fun syncUserFromFirebase(remote: UserDTO) = viewModelScope.launch {
        local.upsert(remote.toEntity())
        // <- mapper used here
    }

    fun credentialManagerSignIn() =
        viewModelScope.launch {
            Log.d("AuthViewModel", "Starting credentialManagerSignIn")
            withContext(dispatchProvider.io) {
                _credentialManagerSignInResponse.value = RequestState.Loading
                Log.d("AuthViewModel", "Set loading state, calling repo.credentialManagerWithGoogle()")
                val response = repo.credentialManagerWithGoogle()
                Log.d("AuthViewModel", "Received response from repo: $response")
                _credentialManagerSignInResponse.value = response
                Log.d("AuthViewModel", "Updated StateFlow with response: ${_credentialManagerSignInResponse.value}")
            }
        }

    // Debug function to test state changes
    fun debugCredentialManagerState() {
        Log.d("AuthViewModel", "Current credentialManagerSignInResponse: ${_credentialManagerSignInResponse.value}")
    }

    fun signInWithGoogle(googleCredential: AuthCredential) = viewModelScope.launch {
        Log.d("signInWithGoogle","Checking Firebase Login")
        _signInWithGoogleResponse.value = RequestState.Loading
        _signInWithGoogleResponse.value = repo.firebaseSignInWithGoogle(googleCredential)
    }

    // Add function to reset states
    fun resetCredentialManagerState() {
        _credentialManagerSignInResponse.value = RequestState.Loading
    }

    fun resetSignInWithGoogleState() {
        _signInWithGoogleResponse.value = RequestState.Loading
    }

    fun getUserProfile() {
        viewModelScope.launch {
            _getUserProfile.value = RequestState.Loading
            val result = getUserProfileUseCase.invoke(firebaseAuth.currentUser?.uid ?: "")
            _getUserProfile.value = result
        }
    }

    fun userIsAuthenticated() {
        _isUserAuthenticated.value = firebaseAuth.currentUser != null
    }
}