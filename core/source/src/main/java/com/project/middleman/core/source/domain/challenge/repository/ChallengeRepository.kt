package com.project.middleman.core.source.domain.challenge.repository

import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.model.ParticipantProgress
import kotlinx.coroutines.flow.Flow

typealias CreateChallengeResponse = RequestState<Challenge>
typealias UpdateChallengeResponse = Flow<RequestState<Challenge>>
typealias DeleteChallengeResponse = RequestState<Challenge>
typealias FetchChallengesResponse = Flow<RequestState<List<Challenge>>>

interface ChallengeRepository {
    suspend fun createChallenge(challenge: Challenge): CreateChallengeResponse
    fun updateChallenge(challenge: Challenge, participant: ParticipantProgress): UpdateChallengeResponse
    suspend fun deleteChallenge(challengeId: String): DeleteChallengeResponse
}

interface FetchChallengesRepository {
    fun fetchChallenges():FetchChallengesResponse
}