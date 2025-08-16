package com.project.middleman.feature.openchallenges.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.core.source.domain.profile.repository.GetUserProfileResponse
import com.project.middleman.core.source.domain.challenge.usecase.FetchChallengesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OpenChallengeViewModel @Inject constructor(
    private val fetchChallengesUseCase: FetchChallengesUseCase,
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
}