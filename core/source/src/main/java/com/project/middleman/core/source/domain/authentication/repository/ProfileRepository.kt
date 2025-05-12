package com.project.middleman.core.source.domain.authentication.repository

import com.project.middleman.core.source.data.RequestState
import com.project.middleman.core.source.data.model.UserDTO

typealias SignOutResponse = RequestState<Boolean>
typealias GetUserProfileResponse = RequestState<UserDTO>

interface ProfileRepository {
    val displayName: String
    val photoUrl: String

    suspend fun signOut(): SignOutResponse
    suspend fun getUserProfile(): GetUserProfileResponse
}