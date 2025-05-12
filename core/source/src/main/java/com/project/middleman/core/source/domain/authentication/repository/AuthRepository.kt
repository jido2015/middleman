package com.project.middleman.core.source.domain.authentication.repository

import com.google.firebase.auth.AuthCredential
import com.project.middleman.core.source.data.RequestState

typealias AuthCredentialResponse = RequestState<AuthCredential>
typealias SignInWithGoogleResponse = RequestState<Boolean>

interface  AuthRepository {
    val isUserAuthenticatedInFirebase: Boolean

    suspend fun credentialManagerWithGoogle(): AuthCredentialResponse

    suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): SignInWithGoogleResponse

}