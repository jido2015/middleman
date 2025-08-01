package com.project.middleman.core.source.domain.authentication.repository

import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.core.source.data.model.UserDTO

typealias SignOutResponse = RequestState<Boolean>
typealias GetUserProfileResponse = RequestState<UserDTO>

interface ProfileRepository {
    suspend fun signOut(): SignOutResponse
    suspend fun getUserProfile(userId: String): GetUserProfileResponse
}