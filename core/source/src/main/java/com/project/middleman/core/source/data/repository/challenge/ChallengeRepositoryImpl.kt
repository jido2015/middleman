package com.project.middleman.core.source.data.repository.challenge

import android.util.Log
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

    override fun updateChallenge(
        challenge: Challenge,
        participant: Participant
    ): Flow<RequestState<Challenge>> = callbackFlow {
        trySend(RequestState.Loading)

        val challengeRef = db.collection("challenges").document(challenge.id)

        // Start snapshot listener for real-time sync
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

        // Run transaction in a coroutine
            try {
                db.runTransaction { transaction ->
                    // 1. Get current challenge (optional if needed)

                    // 2. Update status field
                    transaction.update(challengeRef, "status", challenge.status)

                    // 3. Add participant to nested map
                    val updatePath = "participant.${participant.userId}"
                    transaction.update(challengeRef, updatePath, participant)

                    null // Transactions must return something
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

