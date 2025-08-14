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

            val credentialManager = CredentialManager.create(activityContext)

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(getSignInWithGoogleOption())
                .build()


            val result = credentialManager.getCredential(
                request = request,
                context = activityContext,
            )

            val credential = getAuthCredentialFromResult(result)

            RequestState.Success(credential)
        } catch (e: NoCredentialException) {
            Log.d("signInWithGoogleError-1", "$e")
            RequestState.Error(Throwable("No credentials available"))
        } catch (e: Exception) {
            Log.d("signInWithGoogleError-2", "$e")
            RequestState.Error(Throwable("Unable to get credential"))
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