package com.project.middleman.core.source.data.repository.challenge

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.model.ParticipantProgress
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

            if (challenge.title.isBlank() || challenge.description.isBlank()){
                Log.d("CreateChallengeScreen", "Fields are empty: title ${challenge.title}, description ${challenge.description}")
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
        participant: ParticipantProgress
    ): Flow<RequestState<Challenge>> = callbackFlow {
        trySend(RequestState.Loading).isSuccess

        // Start listening to the document snapshot IMMEDIATELY
        val listenerRegistration = db.collection("challenges")
            .document(challenge.id)
            .addSnapshotListener { snapshot, error ->
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

        // Perform the update AFTER setting up snapshot listener
        val updatePath = "participant.${firebaseAuth.currentUser?.uid}"
        db.collection("challenges")
            .document(challenge.id)
            .update(updatePath, participant)
            .addOnFailureListener { e ->
                trySend(RequestState.Error(e)).isSuccess
            }

        // Clean up snapshot listener when flow is closed
        awaitClose { listenerRegistration.remove() }
    }


    override suspend fun deleteChallenge(challengeId: String): DeleteChallengeResponse {
        TODO("Not yet implemented")
    }
}

