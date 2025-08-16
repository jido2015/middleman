package com.project.middleman.core.source.domain.profile.usecase

import com.project.middleman.core.source.data.model.UserProfile
import com.project.middleman.core.source.domain.profile.repository.AddUserProfileResponse
import com.project.middleman.core.source.domain.profile.repository.CheckDisplayNameResponse
import com.project.middleman.core.source.domain.profile.repository.GetUserProfileResponse
import com.project.middleman.core.source.domain.profile.repository.ProfileRepository
import com.project.middleman.core.source.domain.profile.repository.SignOutResponse
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GetUserProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(userId: String): GetUserProfileResponse
            = profileRepository.getUserProfile(userId = userId)
}

@Singleton
class SignOutUseCase @Inject constructor(
    private val profileRepository: ProfileRepository

){
    suspend operator fun invoke(): SignOutResponse
            = profileRepository.signOut()

}

@Singleton
class AddUserProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(user: UserProfile): AddUserProfileResponse
            = profileRepository.addUserProfile(user = user)
}
