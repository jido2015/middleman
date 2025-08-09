package com.project.middleman.challengedetails.uistate_handler

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.middleman.challengedetails.viewmodel.ChallengeDetailsViewModel
import com.project.middleman.core.source.data.model.Participant
import com.project.middleman.core.source.data.sealedclass.RequestState



@Composable
fun FetchParticipantWrapper(
    viewModel: ChallengeDetailsViewModel = hiltViewModel(),
    getParticipants: (result: List<Participant>) -> Unit,
    onErrorMessage: (message: String) -> Unit
) {
    val participantResponse by viewModel.participants.collectAsState()

    when (participantResponse) {
        is RequestState.Loading -> {
            // Show loading state if needed
        }

        is RequestState.Success -> {
            (participantResponse as RequestState.Success<List<Participant>>).data?.let {
                Log.d("DisplayChallengesData", it.toString())

                LaunchedEffect(it) {
                    getParticipants(it)
                }
            }
        }

        is RequestState.Error -> {
            val error = (participantResponse as RequestState.Error).error
            LaunchedEffect(Unit) {
                onErrorMessage(error.message.toString())
                Log.d("DisplayParticipantsData", error.message.toString())
                viewModel.setLoading(false)
            }
        }
    }
}
