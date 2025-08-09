package com.project.middleman.core.source.data.repository.challenge

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.model.Participant
import com.project.middleman.core.source.domain.challenge.repository.ChallengeRepository
import com.project.middleman.core.source.domain.challenge.repository.CreateChallengeResponse
import com.project.middleman.core.source.domain.challenge.repository.DeleteChallengeResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

//Add challenge repository implementation
class ChallengeRepositoryImpl  @Inject constructor(
    private val db: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
    ): ChallengeRepository{
    override suspend fun createChallenge(challenge: Challenge): CreateChallengeResponse {
        return try {

            if (challenge.title.isBlank() || challenge.category.isBlank()){
                RequestState.Error(Exception("Empty fields"))
            }else{
                db.collection("challenges").document(challenge.id)
                    .set(challenge)
                    .await()
                RequestState.Success(challenge)
            }
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override fun acceptChallenge(
        challenge: Challenge,
        participant: Participant
    ): Flow<RequestState<Challenge>> = callbackFlow {
        trySend(RequestState.Loading)

        val challengeRef = db.collection("challenges").document(challenge.id)

        // Snapshot listener for real-time sync
        val listenerRegistration = challengeRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                trySend(RequestState.Error(error)).isSuccess
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val updatedChallenge = snapshot.toObject(Challenge::class.java)
                if (updatedChallenge != null) {
                    trySend(RequestState.Success(updatedChallenge)).isSuccess
                } else {
                    trySend(RequestState.Error(Exception("Challenge is null"))).isSuccess
                }
            }
        }

        try {
            db.runTransaction { transaction ->
                val snapshot = transaction.get(challengeRef)

                // Current participants map (or empty if none)
                val participantsMap = snapshot.get("participant") as? Map<String, Any> ?: emptyMap()

                // Check participant limit
                if (participantsMap.size >= 2) {
                    throw Exception("Challenge already has maximum participants")
                }

                // Optional: prevent the same user from joining twice
                if (participantsMap.containsKey(participant.userId)) {
                    throw Exception("User already joined this challenge")
                }

                // Update status
                transaction.update(challengeRef, "status", challenge.status)

                // Add participant
                val updatePath = "participant.${participant.userId}"
                transaction.update(challengeRef, updatePath, participant)

                null // Transaction return type
            }.await()
        } catch (e: Exception) {
            trySend(RequestState.Error(e)).isSuccess
        }

        awaitClose { listenerRegistration.remove() }
    }


    override suspend fun deleteChallenge(challengeId: String): DeleteChallengeResponse {
        TODO("Not yet implemented")
    }
}

