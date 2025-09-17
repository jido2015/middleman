package com.project.middleman.challengedetails.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.project.middleman.core.common.BetStatus
import com.project.middleman.core.source.data.local.UserLocalDataSource
import com.project.middleman.core.source.data.local.entity.UserEntity
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.model.Participant
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.core.source.domain.challenge.repository.AcceptParticipantResponse
import com.project.middleman.core.source.domain.challenge.repository.DeleteParticipantResponse
import com.project.middleman.core.source.domain.challenge.usecase.AcceptParticipantUseCase
import com.project.middleman.core.source.domain.challenge.usecase.FetchParticipantsUseCase
import com.project.middleman.core.source.domain.challenge.usecase.RemoveParticipantUseCase
import com.project.middleman.core.source.domain.challenge.usecase.AcceptChallengeUseCase
import com.project.middleman.core.source.domain.challenge.usecase.ConcludeChallengeUseCase
import com.project.middleman.core.source.domain.challenge.usecase.GetChallengeDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeDetailsViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val acceptChallengeUseCase: AcceptChallengeUseCase,
    private val deleteParticipantUseCase: RemoveParticipantUseCase,
    private val acceptParticipantUseCase: AcceptParticipantUseCase,
    private val fetchParticipantsUseCase: FetchParticipantsUseCase,
    private val getChallengeDetailsUseCase: GetChallengeDetailsUseCase,
    private val concludeChallengeUseCase: ConcludeChallengeUseCase,
    local: UserLocalDataSource,
    ): ViewModel() {

    private val _showSheet = MutableStateFlow(false)
    val showSheet: StateFlow<Boolean> = _showSheet


    private val _showAddParticipantButton = MutableStateFlow(false)
    val showAddParticipantButton: StateFlow<Boolean> = _showAddParticipantButton

    var localUser by mutableStateOf<UserEntity?>(null)

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


    init {
        viewModelScope.launch {
            local.observeCurrentUser()
                .filterNotNull()
                .first() // suspend until first non-null user
                .let { user ->
                    localUser = user
                    Log.d("syncUser", "First real user observed2: $user")
                    // trigger one-time logic here
                }
        }
    }

    // Participant accepts challenge
    fun onChallengeAccepted(challenge: Challenge){
        if (localUser == null) {
            Log.w("CreateChallengeViewModel", "No user found in DB, cannot create challenge")
            _updateChallenge.value = RequestState.Error(IllegalStateException("User not found"))
            return
        }


        val updatedChallenge = challenge.copy( status = BetStatus.PENDING.name)

        val participant = Participant(
            status = "Participant",
            joinedAt = System.currentTimeMillis(),
            amount = updatedChallenge.payoutAmount,
            userId = localUser?.uid ?: "",
            displayName = localUser?.displayName ?: "",
            photoUrl = localUser?.photoUrl ?: "",
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

    fun concludeChallenge(challenge: Challenge, status: String) {
        val updatedChallenge = challenge.copy( status = status)
        viewModelScope.launch {
            _updateChallenge.value = RequestState.Loading
            concludeChallengeUseCase.invoke(updatedChallenge, status).collect { state ->
                _updateChallenge.value = state
            }
        }
    }


    fun concludeFinalChallenge(winnerId: String?, challenge: Challenge, status: String) {
        val updatedChallenge = challenge.copy( status = status, winnerId = winnerId)
        viewModelScope.launch {
            _updateChallenge.value = RequestState.Loading
            concludeChallengeUseCase.invoke(updatedChallenge, status).collect { state ->
                _updateChallenge.value = state
            }
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
            val result = acceptParticipantUseCase(BetStatus.ACTIVE.name, challengeId, participantId)
            _acceptParticipantState.value = result
        }
    }

    fun getChallengeDetails(challenge: Challenge) {
        _updateChallenge.value = RequestState.Success(challenge)
    }

    fun openSheet() {
        _showSheet.value = true
    }

    fun closeSheet() {
        _showSheet.value = false
    }

    fun showAddParticipantButton( boolean: Boolean) {
        _showAddParticipantButton.value = boolean
    }
}