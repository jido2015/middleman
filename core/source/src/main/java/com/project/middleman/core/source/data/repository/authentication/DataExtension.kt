package com.project.middleman.core.source.data.repository.authentication

import android.util.Log
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider

fun getAuthCredentialFromResult(result: GetCredentialResponse): AuthCredential? {
    when (val credential = result.credential) {
        is CustomCredential -> {
            if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                try {
                    Log.d("Auth", "Parsed Google ID token: ${credential.data}")
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    val googleIdToken = googleIdTokenCredential.idToken
                    return GoogleAuthProvider.getCredential(googleIdToken, null)
                } catch (e: Exception) {

                    Log.e("Auth", "Failed to parse Google ID token: ${e.localizedMessage}", e)
                }
            } else {
                Log.e("Auth", "Unexpected custom credential type: ${credential.type}")
            }
        }
        else -> {
            Log.e("Auth", "Unexpected type of credential: ${credential.javaClass.simpleName}")
        }
    }
    return null
}
