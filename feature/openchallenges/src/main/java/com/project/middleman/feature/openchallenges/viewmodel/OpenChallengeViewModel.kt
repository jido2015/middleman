package com.project.middleman.feature.openchallenges.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.model.ParticipantProgress
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.core.source.domain.challenge.usecase.FetchChallengesUseCase
import com.project.middleman.core.source.domain.challenge.usecase.UpdateChallengeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OpenChallengeViewModel @Inject constructor(
    private val fetchChallengesUseCase: FetchChallengesUseCase,
    private val firebaseAuth: FirebaseAuth,
    private val updateChallengeUseCase: UpdateChallengeUseCase
): ViewModel() {

    private val _challenges = MutableStateFlow<RequestState<List<Challenge>>>(RequestState.Loading)
    val challenges: StateFlow<RequestState<List<Challenge>>> = _challenges

    // Represents the state of the update challenge operation
    private val _updateChallenge = MutableStateFlow<RequestState<Challenge>>(RequestState.Loading)
    val updateChallenge: StateFlow<RequestState<Challenge>> = _updateChallenge

    var loadingState = mutableStateOf(false)


    fun setLoading(loading: Boolean){
        loadingState.value = loading
    }


    fun getCurrentUser(): String? {
        val user = firebaseAuth.currentUser
        return user?.uid
    }

    // Participant accepts challenge
    fun onChallengeAccepted(challenge: Challenge){

        val participant = ParticipantProgress(
            status = "Participant",
            name = firebaseAuth.currentUser?.displayName.toString(),
            joinedAt = System.currentTimeMillis(),
            amount = challenge.payoutAmount / 2,
            userId = getCurrentUser().toString(),
            won = false,
            winAmount = 0.0
        )

        Log.d("LogChallengeId", challenge.id)
        viewModelScope.launch {
            _updateChallenge.value = RequestState.Loading
            updateChallengeUseCase.invoke(challenge, participant).collect { state ->
                _updateChallenge.value = state
            }
        }
    }



    fun startListeningToChallengeUpdate(challenge: Challenge) {
        _challenges.update { currentState ->
            if (currentState is RequestState.Success) {
                val updatedList = currentState.data?.map { currentChallenge ->
                    if (currentChallenge.id == challenge.id) challenge else currentChallenge
                }
                RequestState.Success(updatedList)
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
}