package com.project.middleman.core.source.domain.authentication.usecase

import com.project.middleman.core.source.data.model.UserProfile
import com.project.middleman.core.source.domain.authentication.repository.AddUserProfileResponse
import com.project.middleman.core.source.domain.authentication.repository.GetUserProfileResponse
import com.project.middleman.core.source.domain.authentication.repository.ProfileRepository
import com.project.middleman.core.source.domain.authentication.repository.SignOutResponse
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