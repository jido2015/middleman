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
import com.project.middleman.core.source.data.sealedclass.AuthState
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
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
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
) : ViewModel() {

    var loadingState = mutableStateOf(false)
    fun setLoading(loading: Boolean) {
        loadingState.value = loading
    }

    var credentialManagerSignInResponse by mutableStateOf<AuthCredentialResponse>(RequestState.Success(null))
        private set
    var signInWithGoogleResponse by mutableStateOf<SignInWithGoogleResponse>(RequestState.Success(false))
        private set

    private val _getUserProfile = MutableStateFlow<GetUserProfileResponse>(RequestState.Loading)
    val getUserProfileState: StateFlow<GetUserProfileResponse> = _getUserProfile
    private val _isUserAuthenticated = MutableStateFlow<AuthState>(AuthState.Loading)
    val isUserAuthenticated: StateFlow<AuthState> = _isUserAuthenticated


    private val authStateListener = FirebaseAuth.AuthStateListener { auth ->

        val user = repo.isUserAuthenticatedInFirebase
        _isUserAuthenticated.value = if (user.value) AuthState.Authenticated else AuthState.Unauthenticated
    }


    init {
        firebaseAuth.addAuthStateListener(authStateListener)
        // Don't set _authState.value immediately — let the listener handle it
    }

    override fun onCleared() {
        super.onCleared()
        firebaseAuth.removeAuthStateListener(authStateListener)
    }

    fun syncUserFromFirebase(remote: UserDTO) = viewModelScope.launch {
        try {
            local.upsert(remote.toEntity())
            Log.d("syncUser", "User saved successfully: $remote")

        } catch (e: Exception) {
            Log.e("syncUser", "Failed to save user", e)
        }
    }

    fun credentialManagerSignIn() = viewModelScope.launch {
        withContext(dispatchProvider.io) {
            credentialManagerSignInResponse = RequestState.Loading
            credentialManagerSignInResponse = repo.credentialManagerWithGoogle()
        }
    }

    fun signInWithGoogle(googleCredential: AuthCredential) = viewModelScope.launch {
        signInWithGoogleResponse = RequestState.Loading
        signInWithGoogleResponse = repo.firebaseSignInWithGoogle(googleCredential)
    }

    fun getUserProfile() {
        viewModelScope.launch {
            _getUserProfile.value = RequestState.Loading
            val uid = firebaseAuth.currentUser?.uid ?: ""
            if (uid.isNotEmpty()) {
                _getUserProfile.value = getUserProfileUseCase.invoke(uid)
            } else {
                _getUserProfile.value = RequestState.Error(Exception("User is not authenticated"))
            }
        }
    }

    fun onLoginComplete() {
        Log.d("onLoginComplete", "Refreshing auth state")
        repo.refreshAuthState()
    }
}