package com.project.middleman.feature.createchallenge.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.domain.challenge.repository.CreateChallengeResponse
import com.project.middleman.core.source.domain.challenge.usecase.CreateChallengeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.project.middleman.core.source.data.model.ParticipantProgress
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import kotlin.String

@HiltViewModel
class CreateChallengeViewModel @Inject constructor(
    private val repo: CreateChallengeUseCase,
    private val auth: FirebaseAuth
) : ViewModel() {

    // Represents the state of the create challenge operation
    var createChallengeResponse by mutableStateOf<CreateChallengeResponse>(RequestState.Success(null))
        private set
    /**
     * Creates a new challenge with the current user as creator.
     */
    fun createChallenge(title: String, description: String, selectedTimeInMillis: Long, stake: Double) {
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
            userId = user.uid,
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
            startDate = selectedTimeInMillis,
            payoutAmount = stake * 2
        )

        viewModelScope.launch {

            createChallengeResponse = RequestState.Loading
            createChallengeResponse = try {
                repo.invoke(challenge)
            } catch (e: Exception) {
                // Catch any unexpected exceptions and update the UI state
                RequestState.Error(e)
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
