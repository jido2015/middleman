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
import com.project.middleman.core.source.domain.authentication.repository.AddUserProfileResponse
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

    init {
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    override fun onCleared() {
        super.onCleared()
        firebaseAuth.removeAuthStateListener(authStateListener)
    }


    private val _credentialManagerSignInResponse = MutableStateFlow<AuthCredentialResponse>(RequestState.Loading)
    val credentialManagerSignInResponse: StateFlow<AuthCredentialResponse> = _credentialManagerSignInResponse

    var signInWithGoogleResponse by mutableStateOf<SignInWithGoogleResponse>(
        RequestState.Success(false)
    )
        private set


    fun syncUserFromFirebase(remote: UserDTO) = viewModelScope.launch {
        local.upsert(remote.toEntity())
        // <- mapper used here
    }

    fun credentialManagerSignIn() =
        viewModelScope.launch {
            withContext(dispatchProvider.io) {
                _credentialManagerSignInResponse.value = RequestState.Loading
                _credentialManagerSignInResponse.value = repo.credentialManagerWithGoogle()
            }
        }

    fun signInWithGoogle(googleCredential: AuthCredential) = viewModelScope.launch {
        Log.d("signInWithGoogle","Checking Firebase Login")
        signInWithGoogleResponse = RequestState.Loading
        signInWithGoogleResponse = repo.firebaseSignInWithGoogle(googleCredential)
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