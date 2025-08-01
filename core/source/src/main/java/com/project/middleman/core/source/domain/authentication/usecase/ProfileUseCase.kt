package com.project.middleman.core.source.domain.authentication.usecase

import com.project.middleman.core.source.domain.authentication.repository.GetUserProfileResponse
import com.project.middleman.core.source.domain.authentication.repository.ProfileRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(userId: String): GetUserProfileResponse
            = profileRepository.getUserProfile(userId = userId)
}