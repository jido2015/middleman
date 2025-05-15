package com.project.middleman.core.source.data.challenge

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.domain.challenge.repository.FetchChallengesRepository
import com.project.middleman.core.source.domain.challenge.repository.FetchChallengesResponse
import javax.inject.Inject
import com.project.middleman.core.source.data.sealedclass.RequestState
import kotlinx.coroutines.tasks.await

class FetchChallengesRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore

): FetchChallengesRepository {
    override suspend fun fetchChallenges(): FetchChallengesResponse {
        return try {

            val snapshot = db.collection("challenges")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()

            RequestState.Success(
                snapshot.documents.mapNotNull { it.toObject(Challenge::class.java)?.copy(id = it.id) }
            )

        }catch (x: Exception){
            RequestState.Error(x)
        }
    }
}