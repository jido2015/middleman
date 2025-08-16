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
import com.project.middleman.core.source.data.local.UserLocalDataSource
import com.project.middleman.core.source.data.local.entity.UserEntity
import com.project.middleman.core.source.data.model.Participant
import com.project.middleman.core.source.data.model.UserDTO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateChallengeViewModel @Inject constructor(
    private val repo: CreateChallengeUseCase,
    local: UserLocalDataSource,
) : ViewModel() {

    var localUser by mutableStateOf<UserEntity?>(null)
    var title by mutableStateOf("")
    var category by mutableStateOf("")
    var selectedTimeInMillis by mutableLongStateOf(0)
    var stake by mutableDoubleStateOf(0.0)
    var visibility by mutableStateOf(false)
    var description by mutableStateOf("")

    var createChallengeResponse by mutableStateOf<CreateChallengeResponse>(RequestState.Success(null))
        private set


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

    fun createChallenge() {
        if (localUser == null) {
            Log.w("CreateChallengeViewModel", "No user found in DB, cannot create challenge")
            createChallengeResponse = RequestState.Error(IllegalStateException("User not found"))
            return
        }

        Log.d("CreateChallengeViewModel", "Creating challenge for user: ${localUser?.uid}")

        val creator = Participant(
            status = "Creator",
            joinedAt = System.currentTimeMillis(),
            amount = stake,
            displayName = localUser?.displayName!!,
            photoUrl = localUser?.photoUrl!!,
            userId = localUser?.uid!!,
            won = false,
            winAmount = 0.0
        )

        val challenge = Challenge(
            id = UUID.randomUUID().toString(),
            title = title,
            participant = mapOf(localUser?.uid!! to creator),
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
                RequestState.Error(e)
            }
        }
    }
}
