package com.project.middleman.core.source.domain.challenge.repository

import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.model.Participant
import kotlinx.coroutines.flow.Flow

typealias CreateChallengeResponse = RequestState<Challenge>
typealias AcceptChallengeResponse = Flow<RequestState<Challenge>>
typealias DeleteChallengeResponse = RequestState<Challenge>
typealias FetchChallengesResponse = Flow<RequestState<List<Challenge>>>
typealias GetChallengeDetailsResponse = Flow<RequestState<Challenge>>

interface ChallengeRepository {
    suspend fun createChallenge(challenge: Challenge): CreateChallengeResponse
    fun acceptChallenge(challenge: Challenge, participant: Participant): AcceptChallengeResponse
    suspend fun deleteChallenge(challengeId: String): DeleteChallengeResponse
}

interface FetchChallengesRepository {
    fun fetchChallenges():FetchChallengesResponse
    fun getChallengeDetails(challengeId: String): GetChallengeDetailsResponse
}