package com.project.middleman.core.source.data.repository.authentication

import android.util.Log
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider

fun getAuthCredentialFromResult(result: GetCredentialResponse): AuthCredential? {
    Log.d("Auth", "Processing credential result: ${result.credential.javaClass.simpleName}")
    
    when (val credential = result.credential) {
        is CustomCredential -> {
            Log.d("Auth", "CustomCredential type: ${credential.type}")
            if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                try {
                    Log.d("Auth", "Parsing Google ID token credential")
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    val googleIdToken = googleIdTokenCredential.idToken
                    Log.d("Auth", "Google ID token extracted successfully")
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
