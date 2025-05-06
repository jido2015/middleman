package com.project.middleman.core.source.data.authentication

import android.content.Context
import androidx.credentials.GetCredentialRequest
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.exceptions.NoCredentialException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.project.middleman.core.common.Constants.USERS
import com.project.middleman.core.source.data.RequestState
import com.project.middleman.core.source.domain.model.User
import com.project.middleman.core.source.domain.repository.AuthCredentialResponse
import com.project.middleman.core.source.domain.repository.AuthRepository
import com.project.middleman.core.source.domain.repository.SignInWithGoogleResponse

import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val activityContext: Context,
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
) : AuthRepository {
    override val isUserAuthenticatedInFirebase = auth.currentUser != null


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
            val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
            if (isNewUser) {
                Log.d("isNewUser", "Add User To Firestore")
                addUserToFirestore()
            }
            RequestState.Success(true)
        } catch (e: Exception) {
            Log.d("signInWithGoogleError", "${e.message}")
            RequestState.Error(e)
        }
    }


    private suspend fun addUserToFirestore() {
        auth.currentUser?.apply {
            val user = toUser()
            db.collection(USERS).document(uid).set(user).await()
            Log.d("userAdded", "User Added to Firestore")
        }
    }

    private fun FirebaseUser.toUser(): User {
        return User(
            displayName = displayName,
            email = email,
            photoUrl = photoUrl.toString(),
            createdAt = FieldValue.serverTimestamp(),
        )
    }
}
