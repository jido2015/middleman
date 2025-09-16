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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.middleman.composables.progressbar.CircularProgressBar
import com.middleman.composables.topbar.MainNavigationTopBar
import com.project.middleman.challengedetails.component.AnimatedParticipantCard
import com.project.middleman.challengedetails.component.ChallengeActionButtons
import com.project.middleman.challengedetails.component.SummaryRequestInfo
import com.project.middleman.challengedetails.component.participantActionMessage
import com.project.middleman.challengedetails.component.creatorActionMessage
import com.project.middleman.challengedetails.component.creatorActionWin
import com.project.middleman.challengedetails.component.participantActionWin
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeDetailsScreen(
    onBackClicked: () -> Unit,
    challengeDetailsViewModel: ChallengeDetailsViewModel = hiltViewModel(),
    challengeDetails: Challenge = Challenge()

) {
    val showSheet by challengeDetailsViewModel.showSheet.collectAsState()
    val sheetState = rememberModalBottomSheetState()


    var challenge by remember { mutableStateOf(challengeDetails) }
    var actionMessage by remember { mutableStateOf("") }
    var winMessage by remember { mutableStateOf("") }

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


    Box(modifier = Modifier.fillMaxSize()) {

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (mainNav, lazyColumn, actions) = createRefs()

            // Top bar (still outside scrollable area)
            MainNavigationTopBar(
                details = "Wager Overview",
                handleBackPressed = { onBackClicked() },
                modifier = Modifier.constrainAs(mainNav){
                    top.linkTo(parent.top)
                }
                    .zIndex(1f) // Keep it above the scroll content
                    .background(white) // Optional: match your theme background
            )

            // Scrollable content
            LazyColumn(
                modifier = Modifier.fillMaxWidth().constrainAs(lazyColumn){
                    top.linkTo(mainNav.bottom, margin = 20.dp)
                }
                    .background(white)
                    .padding(horizontal = 12.dp),
                contentPadding = PaddingValues(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            )  {
                // Challenge card section
                item {
                    ChallengeDetailUi(
                        modifier = Modifier
                            .fillMaxWidth(),
                        challenge = challenge,
                    )

                    Spacer(modifier = Modifier.height(40.dp)) // Space before "Invitations"
                }

                // Invitations list
                items(participants.filter { it.status == "Participant" }) { singleParticipant ->

                    AnimatedParticipantCard(
                        challengeDetailsViewModel,
                        singleParticipant,
                        challenge,
                        creator = creator,
                        currentUser = currentUser
                    )
                    Spacer(modifier = Modifier.height(40.dp)) // Space before "Invitations"
                }
                item {
                    CircularProgressBar(
                        currentStake = challenge.payoutAmount,
                        totalStake = if(challenge.status == BetStatus.OPEN.name ||
                            challenge.status == BetStatus.PENDING.name) challenge.payoutAmount *2
                        else challenge.payoutAmount,
                    )
                    Spacer(modifier = Modifier.height(500.dp)) // Space before "Invitations"
                }
            }

            // Bottom action buttons pinned

            Box(
                modifier = Modifier.fillMaxWidth().background(white).constrainAs(actions){
                    bottom.linkTo(parent.bottom)
                }
            ){
                ChallengeActionButtons(
                    showAcceptSummary = {
                        challengeDetailsViewModel.openSheet()
                    },
                    modifier = Modifier
                        .padding(bottom = 16.dp),
                    winMessage = winMessage,
                    actionMessage = actionMessage,
                    challengeDetailsViewModel = challengeDetailsViewModel,
                    creatorId = creator?.userId,
                    challenge = challenge,
                    participant = participant,
                )
            }
        }
    }

    SummaryRequestInfo(
        showSheet = showSheet,
        challengeDetailsViewModel = challengeDetailsViewModel,
        sheetState = sheetState,
        challenge = challenge)
}


@Composable
@Preview(showBackground = true)
fun ChallengeDetailsPreview() {
    ChallengeDetailsScreen(
        onBackClicked = {}
    )
}
