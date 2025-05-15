package com.project.middleman.core.source.domain.challenge.repository

import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.core.source.data.model.Challenge

typealias CreateChallengeResponse = RequestState<Challenge>
typealias UpdateChallengeResponse = RequestState<Challenge>
typealias DeleteChallengeResponse = RequestState<Challenge>
typealias FetchChallengesResponse = RequestState<List<Challenge>>

interface ChallengeRepository {
    suspend fun createChallenge(challenge: Challenge): CreateChallengeResponse
    suspend fun updateChallenge(challenge: Challenge): UpdateChallengeResponse
    suspend fun deleteChallenge(challengeId: String): DeleteChallengeResponse
}

interface FetchChallengesRepository {
    suspend fun fetchChallenges(): FetchChallengesResponse
}