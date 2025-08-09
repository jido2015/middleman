package com.project.middleman.challengedetails.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.middleman.composables.topbar.MainNavigationTopBar
import com.project.middleman.challengedetails.component.AddParticipantView
import com.project.middleman.challengedetails.component.ChallengeActionButtons
import com.project.middleman.challengedetails.component.InvitationComposeCard
import com.project.middleman.challengedetails.component.participantActionMessage
import com.project.middleman.challengedetails.component.creatorActionMessage
import com.project.middleman.challengedetails.component.viewersActionMessage
import com.project.middleman.challengedetails.uistate_handler.AcceptParticipantWrapper
import com.project.middleman.challengedetails.uistate_handler.ChallengeDetailsWrapper
import com.project.middleman.challengedetails.uistate_handler.DeleteParticipantWrapper
import com.project.middleman.challengedetails.uistate_handler.FetchParticipantWrapper
import com.project.middleman.challengedetails.uistate_handler.GetChallengeDetailsWrapper
import com.project.middleman.challengedetails.viewmodel.ChallengeDetailsViewModel
import com.project.middleman.core.common.BetStatus
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.core.source.data.model.Participant
import com.project.middleman.designsystem.themes.white

@Composable
fun ChallengeDetailsScreen(
    onBackClicked: () -> Unit,
    challengeDetailsViewModel: ChallengeDetailsViewModel = hiltViewModel(),
    onAcceptChallenge: () -> Unit,
    challengeDetails:
    Challenge = Challenge()
) {

    var challenge by remember { mutableStateOf(challengeDetails) }
    var actionMessage by remember { mutableStateOf("") }

    var participants by remember { mutableStateOf(emptyList<Participant>()) }

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


    val creator =  challenge.participant.entries.find { it.value.status == "Creator" }?.value

    val participant = challenge.participant.entries.find { it.value.status == "Participant" }?.value


    LaunchedEffect(challenge) {
        Log.d("ChallengeRecomposition", "Challenge updated: ${challenge.participant}")
    }

    val currentUser = challengeDetailsViewModel.getCurrentUser()

    actionMessage = when (currentUser?.uid) {
        participant?.userId -> {
            participantActionMessage(challenge)
        }
        currentUser?.uid -> {
            creatorActionMessage()
        }
        else -> {
            viewersActionMessage(challenge)
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {

        // Top bar (still outside scrollable area)
        MainNavigationTopBar(
            details = "Wager Overview",
            handleBackPressed = { onBackClicked() },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .zIndex(1f) // Keep it above the scroll content
                .background(white) // Optional: match your theme background
        )

        // Scrollable content
        LazyColumn(
            modifier = Modifier
                .padding(top = 70.dp, bottom = 100.dp) // Adjust top padding to leave space for the fixed top bar
                .fillMaxSize()
                .background(white)
                .padding(horizontal = 12.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        )  {
            // Challenge card section
            item {
                ChallengeDetailUi(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    challenge = challenge,
                )

                Spacer(modifier = Modifier.height(100.dp)) // Space before "Invitations"
            }

            // Section add participant
            item {
                AddParticipantView(
                    creator?.userId,
                    currentUser?.uid,
                            challenge)
            }

            // Invitations list
            items(participants.filter { it.status == "Participant" }) { singleParticipant ->
                InvitationComposeCard(
                    challengeDetailsViewModel,
                    singleParticipant,
                    challenge,
                    creator = creator,
                    currentUser = currentUser
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        // Bottom action buttons pinned
        ChallengeActionButtons(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            actionMessage = actionMessage,
            challengeDetailsViewModel = challengeDetailsViewModel,
            creatorId = creator?.userId,
            challenge = challenge,
            participant = participant,
            onAcceptChallenge = onAcceptChallenge
        )
    }
}


@Composable
@Preview(showBackground = true)
fun ChallengeDetailsPreview(){
    ChallengeDetailsScreen(
        onAcceptChallenge = {},
        challengeDetails = Challenge(),
        onBackClicked = {}
    )
}