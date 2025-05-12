package com.project.middleman.core.source.domain.challenge.repository

import com.project.middleman.core.source.data.RequestState
import com.project.middleman.core.source.data.model.Challenge

typealias CreateChallengeResponse = RequestState<Challenge>
typealias UpdateChallengeResponse = RequestState<Challenge>
typealias DeleteChallengeResponse = RequestState<Challenge>
typealias GetChallengeResponse = RequestState<Challenge>
typealias GetChallengesResponse = RequestState<List<Challenge>>

interface ChallengeRepository {
    suspend fun createChallenge(challenge: Challenge): CreateChallengeResponse
    suspend fun updateChallenge(challenge: Challenge): UpdateChallengeResponse
    suspend fun deleteChallenge(challengeId: String): DeleteChallengeResponse
}