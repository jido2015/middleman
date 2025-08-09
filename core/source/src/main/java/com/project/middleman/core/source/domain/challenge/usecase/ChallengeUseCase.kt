package com.project.middleman.core.source.domain.challenge.usecase

import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.model.Participant
import com.project.middleman.core.source.domain.challenge.repository.ChallengeRepository
import com.project.middleman.core.source.domain.challenge.repository.CreateChallengeResponse
import com.project.middleman.core.source.domain.challenge.repository.FetchChallengesRepository
import com.project.middleman.core.source.domain.challenge.repository.FetchChallengesResponse
import com.project.middleman.core.source.domain.challenge.repository.UpdateChallengeResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateChallengeUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    suspend operator fun invoke(challenge: Challenge): CreateChallengeResponse
        = challengeRepository.createChallenge(challenge)
}

@Singleton
class UpdateChallengeUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
     operator fun invoke(challenge: Challenge, participant: Participant): UpdateChallengeResponse =
        challengeRepository.updateChallenge(challenge, participant)
}

@Singleton
class FetchChallengesUseCase @Inject constructor(
    private val fetchChallengesRepository: FetchChallengesRepository){
    operator fun invoke(): FetchChallengesResponse = fetchChallengesRepository.fetchChallenges()
}