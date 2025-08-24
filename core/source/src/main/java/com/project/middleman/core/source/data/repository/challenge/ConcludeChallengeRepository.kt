package com.project.middleman.core.source.data.repository.challenge

import com.google.firebase.firestore.FirebaseFirestore
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.core.source.domain.challenge.repository.ConcludeChallengeRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ConcludeChallengeRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : ConcludeChallengeRepository {

    override fun concludeChallenge(
        challenge: Challenge,
        betStatus: String
    ): Flow<RequestState<Challenge>> = callbackFlow {

        trySend(RequestState.Loading)

        val challengeRef = db.collection("challenges").document(challenge.id)

        // 1. Real-time listener to emit updates
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

        // 2. Run transaction → just update status
        try {
            db.runTransaction { transaction ->
                // Optionally fetch doc if you need checks:
                // val snapshot = transaction.get(challengeRef)

                // Update only the status
                transaction.update(challengeRef, "status", betStatus)

                null // Firestore requires a return value
            }.await()
        } catch (e: Exception) {
            trySend(RequestState.Error(e)).isSuccess
        }

        // 3. Clean up when flow collector is cancelled
        awaitClose { listenerRegistration.remove() }
    }
}
