package com.project.middleman.core.source.data.repository.authentication

import android.content.Context
import androidx.credentials.GetCredentialRequest
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.exceptions.NoCredentialException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.core.source.domain.authentication.repository.AuthCredentialResponse
import com.project.middleman.core.source.domain.authentication.repository.AuthRepository
import com.project.middleman.core.source.domain.authentication.repository.SignInWithGoogleResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val activityContext: Context,
    private val auth: FirebaseAuth,
) : AuthRepository {

    private val _isUserAuthenticatedInFirebase = MutableStateFlow(auth.currentUser != null)
    override val isUserAuthenticatedInFirebase: StateFlow<Boolean> = _isUserAuthenticatedInFirebase

    // Optional: call this after login/logout to update auth state
    override fun refreshAuthState() {
        _isUserAuthenticatedInFirebase.value = auth.currentUser != null
    }

    override suspend fun credentialManagerWithGoogle(): AuthCredentialResponse {
        return try {
            Log.d("CredentialManager", "Starting credential manager sign in")
            
            val credentialManager = CredentialManager.create(activityContext)
            Log.d("CredentialManager", "CredentialManager created")

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(getSignInWithGoogleOption())
                .build()
            Log.d("CredentialManager", "GetCredentialRequest built")

            val result = credentialManager.getCredential(
                request = request,
                context = activityContext,
            )
            Log.d("CredentialManager", "Credential result received: ${result.credential}")

            val credential = getAuthCredentialFromResult(result)
            Log.d("CredentialManager", "AuthCredential extracted: ${credential?.provider}")

            if (credential != null) {
                Log.d("CredentialManager", "Returning Success with credential")
                RequestState.Success(credential)
            } else {
                Log.e("CredentialManager", "Failed to extract AuthCredential from result")
                RequestState.Error(Throwable("Failed to extract authentication credential"))
            }
        } catch (e: NoCredentialException) {
            Log.e("CredentialManager", "NoCredentialException: ${e.message}", e)
            RequestState.Error(Throwable("No credentials available: ${e.message}"))
        } catch (e: Exception) {
            Log.e("CredentialManager", "Exception in credentialManagerWithGoogle: ${e.message}", e)
            RequestState.Error(Throwable("Unable to get credential: ${e.message}"))
        }
    }

    override suspend fun firebaseSignInWithGoogle(
        googleCredential: AuthCredential
    ): SignInWithGoogleResponse {
        return try {
            Log.d("signInWithGoogle", "Checking Firebase Auth")

            val authResult = auth.signInWithCredential(googleCredential).await()

            Log.d("signInWithGoogleUser", "${authResult.user}")
            RequestState.Success(true)
        } catch (e: Exception) {
            Log.d("signInWithGoogleError", "${e.message}")
            RequestState.Error(e)
        }
    }
}