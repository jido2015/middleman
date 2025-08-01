package com.project.middleman.feature.openchallenges.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.project.middleman.core.source.data.DispatchProvider
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.model.ParticipantProgress
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.core.source.domain.authentication.repository.GetUserProfileResponse
import com.project.middleman.core.source.domain.authentication.usecase.ProfileUseCase
import com.project.middleman.core.source.domain.challenge.usecase.FetchChallengesUseCase
import com.project.middleman.core.source.domain.challenge.usecase.UpdateChallengeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OpenChallengeViewModel @Inject constructor(
    private val fetchChallengesUseCase: FetchChallengesUseCase,
    private val firebaseAuth: FirebaseAuth,
    private val dispatchProvider: DispatchProvider,
    private val updateChallengeUseCase: UpdateChallengeUseCase,
    private val getUserProfileUseCase: ProfileUseCase
): ViewModel() {

    private val _challenges = MutableStateFlow<RequestState<List<Challenge>>>(RequestState.Loading)
    val challenges: StateFlow<RequestState<List<Challenge>>> = _challenges

    // Represents the state of the update challenge operation
    private val _updateChallenge = MutableStateFlow<RequestState<Challenge>>(RequestState.Loading)
    val updateChallenge: StateFlow<RequestState<Challenge>> = _updateChallenge

    var userProfileResponse by mutableStateOf<GetUserProfileResponse>(RequestState.Success(null))
        private set

    var loadingState = mutableStateOf(false)


    fun setLoading(loading: Boolean){
        loadingState.value = loading
    }


    fun getCurrentUser(): FirebaseUser? {
        val user = firebaseAuth.currentUser
        return user
    }

    // Participant accepts challenge
    fun onChallengeAccepted(challenge: Challenge){

        val updatedChallenge = challenge.copy(title = "In-Progress")

        val participant = ParticipantProgress(
            status = "Participant",
            joinedAt = System.currentTimeMillis(),
            amount = updatedChallenge.payoutAmount / 2,
            userId = getCurrentUser()?.uid ?: "",
            displayName = getCurrentUser()?.displayName ?: "",
            photoUrl = getCurrentUser()?.photoUrl?.toString() ?: "",
            won = false,
            winAmount = 0.0
        )

        Log.d("LogChallengeId", updatedChallenge.id)
        viewModelScope.launch {
            _updateChallenge.value = RequestState.Loading
            updateChallengeUseCase.invoke(updatedChallenge, participant).collect { state ->
                _updateChallenge.value = state
            }
        }
    }

    fun startListeningToChallengeUpdate(challenge: Challenge) {
        _challenges.update { currentState ->
            if (currentState is RequestState.Success) {
                val updatedChallenge = currentState.data?.map { currentChallenge ->
                    if (currentChallenge.id == challenge.id) challenge else currentChallenge
                }
                RequestState.Success(updatedChallenge)
            } else {
                currentState
            }
        }
    }



    fun fetchChallenges() {
        viewModelScope.launch {
            _challenges.value = RequestState.Loading
            fetchChallengesUseCase.invoke()
                .collect { state ->
                    _challenges.value = state
                }
        }
    }


    private fun getUserProfile(userId: String) {
        viewModelScope.launch {
            withContext(dispatchProvider.io) {
                userProfileResponse = RequestState.Loading
                userProfileResponse = getUserProfileUseCase(userId) // Could return UserProfile or GetUserProfileResponse
            }
        }
    }
}