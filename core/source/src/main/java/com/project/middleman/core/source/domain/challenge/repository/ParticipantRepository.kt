package com.project.middleman.core.source.domain.challenge.repository

import com.project.middleman.core.source.data.model.Participant
import com.project.middleman.core.source.data.sealedclass.RequestState
import kotlinx.coroutines.flow.Flow

typealias DeleteParticipantResponse = RequestState<Boolean>
typealias AcceptParticipantResponse = RequestState<Boolean>
typealias FetchParticipantsResponse = Flow<RequestState<List<Participant>>>

interface ParticipantRepository {

    suspend fun removeParticipant(betStatus: String, challengeId: String, participantId: String):
            DeleteParticipantResponse
    fun fetchParticipants(challengeId: String): FetchParticipantsResponse

    suspend fun acceptParticipant( betStatus: String, challengeId: String, participantId: String):
            AcceptParticipantResponse
}

