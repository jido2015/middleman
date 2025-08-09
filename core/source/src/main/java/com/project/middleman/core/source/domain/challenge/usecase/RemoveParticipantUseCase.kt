package com.project.middleman.core.source.domain.challenge.usecase

import com.project.middleman.core.source.domain.challenge.repository.FetchParticipantsResponse
import com.project.middleman.core.source.domain.challenge.repository.ParticipantRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoveParticipantUseCase @Inject constructor(
    private val participantRepository: ParticipantRepository
) {
    suspend operator fun invoke(betStatus: String, challengeId: String, participantId: String) =
        participantRepository.removeParticipant(betStatus, challengeId, participantId)
}

@Singleton
class FetchParticipantsUseCase @Inject constructor(
    private val repository: ParticipantRepository)
{
    operator fun invoke( challengeId: String): FetchParticipantsResponse
    = repository.fetchParticipants(challengeId)
}

@Singleton
class AcceptParticipantUseCase @Inject constructor(
    private val repository: ParticipantRepository)
{
    suspend operator fun invoke(betStatus: String, challengeId: String, participantId: String) =
        repository.acceptParticipant(betStatus, challengeId, participantId)
}