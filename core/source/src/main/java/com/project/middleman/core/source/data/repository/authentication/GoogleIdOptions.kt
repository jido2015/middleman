package com.project.middleman.core.source.data.repository.authentication

import java.security.MessageDigest
import java.util.UUID
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.project.middleman.core.source.BuildConfig.OAuth_2_0_WEB_CLIENT_ID

fun getSignInWithGoogleOption(): GetSignInWithGoogleOption {

    // 2.1
    val rawNonce = UUID.randomUUID().toString()
    val bytes = rawNonce.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    val hashedNonce = digest.fold("") { str, it ->
        str + "%02x".format(it)
    }
    // 2.2
    return GetSignInWithGoogleOption.Builder(OAuth_2_0_WEB_CLIENT_ID)
        .setNonce(hashedNonce)
        .build()
}