package com.project.middleman.challengedetails.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.project.middleman.core.common.BetStatus
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.model.Participant
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.core.source.domain.challenge.repository.AcceptParticipantResponse
import com.project.middleman.core.source.domain.challenge.repository.DeleteParticipantResponse
import com.project.middleman.core.source.domain.challenge.usecase.AcceptParticipantUseCase
import com.project.middleman.core.source.domain.challenge.usecase.FetchParticipantsUseCase
import com.project.middleman.core.source.domain.challenge.usecase.RemoveParticipantUseCase
import com.project.middleman.core.source.domain.challenge.usecase.AcceptChallengeUseCase
import com.project.middleman.core.source.domain.challenge.usecase.GetChallengeDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeDetailsViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val acceptChallengeUseCase: AcceptChallengeUseCase,
    private val deleteParticipantUseCase: RemoveParticipantUseCase,
    private val acceptParticipantUseCase: AcceptParticipantUseCase,
    private val fetchParticipantsUseCase: FetchParticipantsUseCase,
    private val getChallengeDetailsUseCase: GetChallengeDetailsUseCase
): ViewModel() {

    // Represents the state of the update challenge operation
    private val _updateChallenge = MutableStateFlow<RequestState<Challenge>>(RequestState.Loading)
    val updateChallenge: StateFlow<RequestState<Challenge>> = _updateChallenge

    private val _getChallengeState = MutableStateFlow<RequestState<Challenge>>(RequestState.Loading)
    val getChallengeState: StateFlow<RequestState<Challenge>> = _getChallengeState

    var loadingState = mutableStateOf(false)

    private val _deleteParticipantState = MutableStateFlow<DeleteParticipantResponse>(RequestState.Loading)
    val deleteParticipantState: StateFlow<DeleteParticipantResponse?> = _deleteParticipantState

    private val _acceptParticipantState = MutableStateFlow<AcceptParticipantResponse>(RequestState.Loading)
    val acceptParticipantState: StateFlow<AcceptParticipantResponse?> = _acceptParticipantState

    private val _participants = MutableStateFlow<RequestState<List<Participant>>>(RequestState.Loading)
    val participants: StateFlow<RequestState<List<Participant>>> = _participants


    fun setLoading(loading: Boolean){
        loadingState.value = loading
    }

    fun getCurrentUser(): FirebaseUser? {
        val user = firebaseAuth.currentUser
        return user
    }

    // Participant accepts challenge
    fun onChallengeAccepted(challenge: Challenge){

        val updatedChallenge = challenge.copy( status = BetStatus.PENDING.name)

        val participant = Participant(
            status = "Participant",
            joinedAt = System.currentTimeMillis(),
            amount = updatedChallenge.payoutAmount / 2,
            userId = getCurrentUser()?.uid ?: "",
            displayName = getCurrentUser()?.displayName ?: "",
            photoUrl = getCurrentUser()?.photoUrl?.toString() ?: "",
            won = false,
            winAmount = 0.0
        )


        // Update challenge with participant. But for now,
        // let's hardcode the participant id since we are not ready for user inviting  someone yet
        Log.d("LogChallengeId", updatedChallenge.id)
        viewModelScope.launch {
            _updateChallenge.value = RequestState.Loading
            acceptChallengeUseCase.invoke(updatedChallenge, participant).collect { state ->
                _updateChallenge.value = state
            }
        }
    }

    fun fetchParticipants( challengeId: String) {
        viewModelScope.launch {
            _participants.value = RequestState.Loading
            fetchParticipantsUseCase.invoke(challengeId)
                .collect { state ->
                    _participants.value = state

                }
        }
    }

    fun removeParticipant(challengeId: String, participantId: String) {
        viewModelScope.launch {
            _deleteParticipantState.value = RequestState.Loading
            val result =
                deleteParticipantUseCase(BetStatus.OPEN.name, challengeId, participantId)
            _deleteParticipantState.value = result
        }
    }

    fun getUpdatedChallengeDetails(challengeId: String){
        viewModelScope.launch {
            _getChallengeState.value = RequestState.Loading
            getChallengeDetailsUseCase.invoke(challengeId)
                .collect { state ->
                    _getChallengeState.value = state
                }
        }
    }

    fun acceptParticipantRequest(challengeId: String, participantId: String) {
        viewModelScope.launch {
            _acceptParticipantState.value = RequestState.Loading
            val result =
                acceptParticipantUseCase(BetStatus.ACTIVE.name, challengeId, participantId)
            _acceptParticipantState.value = result
        }
    }

    fun getChallengeDetails(challenge: Challenge) {
        _updateChallenge.value = RequestState.Success(challenge)
    }

}