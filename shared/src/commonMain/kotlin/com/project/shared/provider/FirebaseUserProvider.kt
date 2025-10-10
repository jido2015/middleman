package com.project.shared.provider

import dev.gitlive.firebase.auth.FirebaseUser

interface FirebaseUserProvider {
    suspend fun getCurrentUser(): FirebaseUser?
}