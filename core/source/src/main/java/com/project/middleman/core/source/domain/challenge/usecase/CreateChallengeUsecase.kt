package com.project.middleman.core.source.domain.challenge.usecase

import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.domain.challenge.repository.ChallengeRepository
import com.project.middleman.core.source.domain.challenge.repository.CreateChallengeResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateChallengeUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    suspend operator fun invoke(challenge: Challenge): CreateChallengeResponse
        = challengeRepository.createChallenge(challenge)
}
