package com.project.middleman.core.source.data.repository.challenge

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.project.middleman.core.source.data.model.Participant
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.core.source.domain.challenge.repository.AcceptParticipantResponse
import com.project.middleman.core.source.domain.challenge.repository.DeleteParticipantResponse
import com.project.middleman.core.source.domain.challenge.repository.ParticipantRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class ParticipantRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : ParticipantRepository {

    override suspend fun removeParticipant(
        betStatus: String,
        challengeId: String,
        participantId: String,
    ): DeleteParticipantResponse {
        return try {
            val challengeRef = db.collection("challenges").document(challengeId)

            db.runTransaction { transaction ->
                // You don't really need snapshot unless you want to check something first
                val updates = mapOf<String, Any>(
                    "participant.$participantId" to FieldValue.delete(),
                    "status" to betStatus // ← Set the new status you want
                )

                transaction.update(challengeRef, updates)

                null // Firestore requires a return value
            }.await()

            RequestState.Success(true)
        } catch (ex: Exception) {
            RequestState.Error(Exception(ex.message))
        }
    }


    override fun fetchParticipants(challengeId: String): Flow<RequestState<List<Participant>>> = callbackFlow {
        trySend(RequestState.Loading)

        val listenerRegistration = db.collection("challenges")
            .document(challengeId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(RequestState.Error(error))
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    // Get participant map
                    val participantMap = snapshot.get("participant") as? Map<String, Map<String, Any>> ?: emptyMap()

                    // Convert map entries into Participant objects
                    val participants = participantMap.mapNotNull { (id, data) ->
                        try {
                            val json = Gson().toJson(data)
                            Gson().fromJson(json, Participant::class.java).copy(userId = id)
                        } catch (e: Exception) {
                            null
                        }
                    }

                    trySend(RequestState.Success(participants))
                } else {
                    trySend(RequestState.Success(emptyList()))
                }
            }

        awaitClose { listenerRegistration.remove() }
    }.catch { e ->
        emit(RequestState.Error(e))
    }

    override suspend fun acceptParticipant(
        betStatus: String,
        challengeId: String,
        participantId: String
    ): AcceptParticipantResponse {
        return try {
            val challengeRef = db.collection("challenges").document(challengeId)

           // challengeRef.update("status", betStatus).await()


            db.runTransaction { transaction ->
                // You don't really need snapshot unless you want to check something first
                val updates = mapOf<String, Any>(
                    "status" to betStatus // ← Set the new status you want
                )

                transaction.update(challengeRef, updates)

                null // Firestore requires a return value
            }.await()

            RequestState.Success(true)
        } catch (ex: Exception) {
            RequestState.Error(Exception(ex.message))
        }
    }

}
