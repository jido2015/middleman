package com.project.middleman.challengedetails.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.model.ParticipantProgress
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.core.source.domain.challenge.usecase.UpdateChallengeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeDetailsViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val updateChallengeUseCase: UpdateChallengeUseCase
): ViewModel() {

    // Represents the state of the update challenge operation
    private val _updateChallenge = MutableStateFlow<RequestState<Challenge>>(RequestState.Loading)
    val updateChallenge: StateFlow<RequestState<Challenge>> = _updateChallenge


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

        val updatedChallenge = challenge.copy(title = "In-Progress", status = "pending")

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


    fun getChallengeDetails(challenge: Challenge) {
        _updateChallenge.value = RequestState.Success(challenge)
    }

}