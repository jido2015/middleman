package com.project.middleman.challengedetails.presentation

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.project.middleman.challengedetails.component.action.participantActionMessage
import com.project.middleman.challengedetails.component.action.creatorActionMessage
import com.project.middleman.challengedetails.component.action.creatorActionWin
import com.project.middleman.challengedetails.component.action.participantActionWin
import com.project.middleman.challengedetails.component.action.viewersActionMessage
import com.project.middleman.challengedetails.uistate_handler.AcceptParticipantWrapper
import com.project.middleman.challengedetails.uistate_handler.ChallengeDetailsWrapper
import com.project.middleman.challengedetails.uistate_handler.DeleteParticipantWrapper
import com.project.middleman.challengedetails.uistate_handler.FetchParticipantWrapper
import com.project.middleman.challengedetails.uistate_handler.GetChallengeDetailsWrapper
import com.project.middleman.challengedetails.viewmodel.ChallengeDetailsViewModel
import com.project.middleman.core.common.BetStatus
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.model.Participant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeDetailsScreen(
    onBackClicked: () -> Unit,
    challengeDetailsViewModel: ChallengeDetailsViewModel = hiltViewModel(),
    challengeDetails: Challenge = Challenge()

) {

    var participants by remember { mutableStateOf(emptyList<Participant>()) }

    var challenge by remember { mutableStateOf(challengeDetails) }

    var actionMessage by remember { mutableStateOf("") }
    var winMessage by remember { mutableStateOf("") }




    val creator =  challenge.participant.entries.find { it.value.status == "Creator" }?.value

    val participant = challenge.participant.entries.find { it.value.status == "Participant" }?.value

    val currentUser = challengeDetailsViewModel.getCurrentUser()

    actionMessage = when (currentUser?.uid) {
        participant?.userId -> {
            participantActionMessage(challenge)
        }
        creator?.userId -> {
            creatorActionMessage()
        }
        else -> {
            viewersActionMessage(challenge)
        }
    }

    winMessage = when (currentUser?.uid) {
        participant?.userId -> {
            participantActionWin(challenge)
        }
        creator?.userId -> {
            creatorActionWin(challenge)
        }
        else -> {
            ""
        }
    }


    ChallengeDetailUi(challenge,
        challengeDetailsViewModel, participants,
        actionMessage, winMessage,
        creator, participant,
        currentUser, onBackClicked)

    LaunchedEffect(key1 = challengeDetails.id) {
        challengeDetailsViewModel.getChallengeDetails(challengeDetails)
    }

    challengeDetailsViewModel.getUpdatedChallengeDetails( challengeDetails.id)
    // Get challenge details
    GetChallengeDetailsWrapper(
        onErrorMessage = {
            Log.d("GetChallengeDetailsWrapper", "Failed")
        },
        onSuccess = {
            challenge = it
            Log.d("GetChallengeDetailsWrapper", "Success")
        }
    )

    // Accept participant
    AcceptParticipantWrapper(
        onErrorMessage = {
            Log.d("AcceptParticipantWrapper", "Failed")

        },
        onSuccessMessage = {
           challenge = challenge.copy(status = BetStatus.ACTIVE.name)
            Log.d("AcceptParticipantWrapper", "Success")
        }
    )


    ChallengeDetailsWrapper(
        onSuccess = { challengeDetails ->
            challenge = challengeDetails
        },
        onErrorMessage = {
            //Exception(it)
        },
        onSuccessMessage = {
        })



    // Delete participant
    DeleteParticipantWrapper(
        onSuccess = {
            challenge = challenge.copy(status = BetStatus.OPEN.name,
                participant = challenge.participant
                    .filterValues { it.status != "Participant" }
            )
        },
        onErrorMessage = {
            Log.d("DeleteParticipantWrapper", "Failed")
        })


    challengeDetailsViewModel.setLoading(true)
    challengeDetailsViewModel.fetchParticipants(challenge.id)


    fun getParticipants(result: List<Participant>) {
        challengeDetailsViewModel.setLoading(false)
        participants = result
        Log.d("getParticipant", result.toString())
    }

    //Observe challenges

    FetchParticipantWrapper(
        getParticipants = {
            getParticipants(it)
        },
        onErrorMessage = {
            //   Exception(it)
        }
    )

}


@Composable
@Preview(showBackground = true)
fun ChallengeDetailsPreview() {
    ChallengeDetailsScreen(
        onBackClicked = {}
    )
}
