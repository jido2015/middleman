package com.project.middleman.core.source.data.challenge

import com.google.firebase.firestore.FirebaseFirestore
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.domain.challenge.repository.ChallengeRepository
import com.project.middleman.core.source.domain.challenge.repository.CreateChallengeResponse
import com.project.middleman.core.source.domain.challenge.repository.DeleteChallengeResponse
import com.project.middleman.core.source.domain.challenge.repository.UpdateChallengeResponse
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class ChallengeRepositoryImpl  @Inject constructor(
    private val db: FirebaseFirestore
    ): ChallengeRepository{
    override suspend fun createChallenge(challenge: Challenge): CreateChallengeResponse {
        return try {

            if (challenge.title.isBlank() || challenge.description.isBlank()){
                RequestState.Error(Exception("Empty fields"))
            }else{
                db.collection("challenges")
                    .add(challenge)
                    .await()
                RequestState.Success(challenge)
            }
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun updateChallenge(challenge: Challenge): UpdateChallengeResponse {
        TODO("Not yet implemented")
    }

    override suspend fun deleteChallenge(challengeId: String): DeleteChallengeResponse {
        TODO("Not yet implemented")
    }
}