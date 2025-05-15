package com.project.middleman.feature.createchallenge.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.domain.challenge.repository.CreateChallengeResponse
import com.project.middleman.core.source.domain.challenge.usecase.CreateChallengeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.project.middleman.core.source.data.DispatchProvider
import com.project.middleman.core.source.data.model.ParticipantProgress
import com.project.middleman.core.source.data.model.User
import com.project.middleman.core.source.domain.authentication.usecase.ProfileUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import kotlin.String

@HiltViewModel
class CreateChallengeViewModel @Inject constructor(
    private val repo: CreateChallengeUseCase,
    private val auth: FirebaseAuth,
    private val dispatchProvider: DispatchProvider,
    private val userProfile: ProfileUseCase
) : ViewModel() {

    // Represents the state of the create challenge operation
    var createChallengeResponse by mutableStateOf<CreateChallengeResponse>(RequestState.Success(null))
        private set

    /**
     * Creates a new challenge with the current user as creator.
     */
    fun createChallenge(title: String, description: String, stake: Double) {
        val user = auth.currentUser
        if (user == null) {
            createChallengeResponse = RequestState.Error(IllegalStateException("User is not authenticated."))
            return
        }

        val creator = ParticipantProgress(
            status = "Creator",
            name = auth.currentUser?.displayName.toString(),
            joinedAt = System.currentTimeMillis(),
            amount = stake,
            won = false,
            winAmount = 0.0
        )

        val challenge = Challenge(
            id = UUID.randomUUID().toString(),
            title = title,
            participant = mapOf(user.uid to creator),
            description = description,
            status = "open",
            createdAt = System.currentTimeMillis(),
            startDate = System.currentTimeMillis(),
            endDate = System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000,
            payoutAmount = stake * 2
        )

        viewModelScope.launch {

            withContext(dispatchProvider.io){
                createChallengeResponse = RequestState.Loading
                try {
                    withContext(Dispatchers.IO) {
                        createChallengeResponse = repo.invoke(challenge)
                    }
                } catch (e: Exception) {
                    // Catch any unexpected exceptions and update the UI state
                    createChallengeResponse = RequestState.Error(e)
                }
            }

        }
    }
}



//    private fun participants(userId: String): Map<String, ParticipantProgress> {
//        val participants = mapOf<String, ParticipantProgress>(
//            userId to ParticipantProgress()
//        )
//
//        return participants
//    }
