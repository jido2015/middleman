package com.project.middleman.core.source.domain.profile.repository

import com.project.middleman.core.source.data.model.UserDTO
import com.project.middleman.core.source.data.model.UserProfile
import com.project.middleman.core.source.data.sealedclass.RequestState

typealias SignOutResponse = RequestState<Boolean>
typealias GetUserProfileResponse = RequestState<UserDTO>
typealias AddUserProfileResponse = RequestState<Boolean>
typealias CheckDisplayNameResponse = RequestState<Boolean>

interface ProfileRepository {
    suspend fun signOut(): SignOutResponse
    suspend fun getUserProfile(userId: String): GetUserProfileResponse

    suspend fun addUserProfile(user: UserProfile): AddUserProfileResponse

}