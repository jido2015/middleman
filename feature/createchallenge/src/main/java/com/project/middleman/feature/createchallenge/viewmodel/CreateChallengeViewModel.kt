package com.project.middleman.feature.createchallenge.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableLongStateOf
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
import com.project.middleman.core.common.BetStatus
import com.project.middleman.core.source.data.model.Participant
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateChallengeViewModel @Inject constructor(
    private val repo: CreateChallengeUseCase,
    private val auth: FirebaseAuth
) : ViewModel() {

    var title by mutableStateOf("")
    var category by mutableStateOf("")
    var selectedTimeInMillis by mutableLongStateOf(0)
    var stake by mutableDoubleStateOf(0.0)
    var visibility by mutableStateOf(false)
    var description by mutableStateOf("")

    // Represents the state of the create challenge operation
    var createChallengeResponse by mutableStateOf<CreateChallengeResponse>(RequestState.Success(null))
        private set
    /**
     * Creates a new challenge with the current user as creator.
     */
    fun createChallenge() {
        val user = auth.currentUser
        if (user == null) {
            createChallengeResponse = RequestState.Error(IllegalStateException("User is not authenticated."))
            return
        }

        Log.d("BetStatus", BetStatus.OPEN.name)
        val creator = Participant(
            status = "Creator",
            joinedAt = System.currentTimeMillis(),
            amount = stake,
            displayName = user.displayName ?: "",
            photoUrl = user.photoUrl?.toString() ?: "",
            userId = user.uid,
            won = false,
            winAmount = 0.0
        )

        val challenge = Challenge(
            id = UUID.randomUUID().toString(),
            title = title,
            participant = mapOf(user.uid to creator),
            category = category,
            status = BetStatus.OPEN.name,
            visibility = visibility,
            createdAt = System.currentTimeMillis(),
            startDate = selectedTimeInMillis,
            payoutAmount = stake * 2,
            description = description
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

    init {
        Log.d("CreateChallengeViewModel", "ViewModel created")
    }

}

//    private fun participants(userId: String): Map<String, ParticipantProgress> {
//        val participants = mapOf<String, ParticipantProgress>(
//            userId to ParticipantProgress()
//        )
//
//        return participants
//    }
