package com.project.middleman.core.source.domain.authentication.repository

import com.google.firebase.auth.AuthCredential
import com.project.middleman.core.source.data.sealedclass.RequestState
import kotlinx.coroutines.flow.StateFlow

typealias AuthCredentialResponse = RequestState<AuthCredential>
typealias SignInWithGoogleResponse = RequestState<Boolean>

interface  AuthRepository {
    val isUserAuthenticatedInFirebase: StateFlow<Boolean>
    fun refreshAuthState()

    suspend fun credentialManagerWithGoogle(): AuthCredentialResponse

    suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): SignInWithGoogleResponse

}