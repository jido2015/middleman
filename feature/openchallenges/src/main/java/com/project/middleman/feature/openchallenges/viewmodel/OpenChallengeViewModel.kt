package com.project.middleman.feature.openchallenges.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.middleman.core.source.data.DispatchProvider
import com.project.middleman.core.source.data.sealedclass.RequestState
import com.project.middleman.core.source.domain.challenge.repository.FetchChallengesResponse
import com.project.middleman.core.source.domain.challenge.usecase.FetchChallengesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OpenChallengeViewModel @Inject constructor(
    private val fetchChallengesUseCase: FetchChallengesUseCase,
    private val dispatchProvider: DispatchProvider
): ViewModel() {
    var openChallengeResponse by mutableStateOf<FetchChallengesResponse>(RequestState.Loading)
        private set

    var loadingState = mutableStateOf(false)
    fun setLoading(loading: Boolean){
        loadingState.value = loading
    }

    fun fetchOpenChallenges(){
        viewModelScope.launch {
            withContext(dispatchProvider.io) {
                Log.d("getChallenges", "Called")

                openChallengeResponse = RequestState.Loading
                openChallengeResponse = fetchChallengesUseCase.invoke()

            }
        }
    }
}