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
import com.project.middleman.core.common.BetStatus
import com.project.middleman.core.source.data.local.UserLocalDataSource
import com.project.middleman.core.source.data.local.entity.UserEntity
import com.project.middleman.core.source.data.model.Participant
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateChallengeViewModel @Inject constructor(
    private val repo: CreateChallengeUseCase,
    local: UserLocalDataSource,
) : ViewModel() {

    private val _showSheet = MutableStateFlow(false)
    val showSheet: StateFlow<Boolean> = _showSheet
    var localUser by mutableStateOf<UserEntity?>(null)
    var title by mutableStateOf("")
    var category by mutableStateOf("")
    var selectedTimeInMillis by mutableLongStateOf(0)
    var stake by mutableDoubleStateOf(0.0)
    var visibility by mutableStateOf(false)
    var description by mutableStateOf("")


    private val _createChallengeResponse = MutableSharedFlow<String>()
    val createChallengeResponse: SharedFlow<String> = _createChallengeResponse

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
            return
        }

        Log.d("CreateLocal", "Creating challenge for user: $localUser")

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
            payoutAmount = stake,
            description = description
        )

        viewModelScope.launch {
            try {
              repo.invoke(challenge)
                _createChallengeResponse.emit("created")
            } catch (e: Exception) {
                Log.e("CreateChallengeViewModel", "Error creating challenge", e)
                _createChallengeResponse.emit("Error creating challenge")
            }
        }
    }


    fun openSheet() {
        _showSheet.value = true
    }

    fun closeSheet() {
        _showSheet.value = false
        Log.d("CreateChallengeViewModel", "Sheet closed")
    }

}
