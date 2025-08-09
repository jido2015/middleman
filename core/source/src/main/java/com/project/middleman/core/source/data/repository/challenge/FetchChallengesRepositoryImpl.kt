package com.project.middleman.core.source.data.repository.challenge

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.domain.challenge.repository.FetchChallengesRepository
import javax.inject.Inject
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.core.source.domain.challenge.repository.FetchChallengesResponse
import com.project.middleman.core.source.domain.challenge.repository.GetChallengeDetailsResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch


class FetchChallengesRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : FetchChallengesRepository {

    override fun fetchChallenges(): FetchChallengesResponse = callbackFlow {
        trySend(RequestState.Loading) // Optional: Emit loading state

        val listenerRegistration = db.collection("challenges")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(RequestState.Error(error))
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val challenges = snapshot.documents.mapNotNull {
                        it.toObject(Challenge::class.java)?.copy(id = it.id)
                    }
                    trySend(RequestState.Success(challenges))
                }else{
                    trySend(RequestState.Success(emptyList()))
                }
            }

        // Clean up listener when flow is closed
        awaitClose {
            listenerRegistration.remove()
        }
    }.catch { e ->
        emit(RequestState.Error(e))
    }


    override fun getChallengeDetails(challengeId: String): GetChallengeDetailsResponse = callbackFlow {
        val listener = db.collection("challenges")
            .document(challengeId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error) // Stop the flow if there's an error
                    return@addSnapshotListener
                }
                val challenge = snapshot?.toObject(Challenge::class.java)
                if (challenge != null) {
                    trySend(RequestState.Success(challenge))
                }
            }

        awaitClose { listener.remove() }
    }


}
