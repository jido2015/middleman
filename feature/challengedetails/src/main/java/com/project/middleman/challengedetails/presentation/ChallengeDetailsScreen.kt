package com.project.middleman.challengedetails.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.middleman.composables.topbar.MainNavigationTopBar
import com.project.middleman.challengedetails.component.CreatorActionButton
import com.project.middleman.challengedetails.component.ParticipantActionButton
import com.project.middleman.challengedetails.component.participantActionMessage
import com.project.middleman.challengedetails.component.ViewersActionButton
import com.project.middleman.challengedetails.component.creatorActionMessage
import com.project.middleman.challengedetails.component.viewersActionMessage
import com.project.middleman.challengedetails.presentation.uistate_handler.ChallengeDetailsWrapper
import com.project.middleman.challengedetails.viewmodel.ChallengeDetailsViewModel
import com.project.middleman.core.source.data.model.Challenge
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.white

@Composable
fun ChallengeDetailsScreen(
    onBackClicked: () -> Unit,
    challengeDetailsViewModel: ChallengeDetailsViewModel = hiltViewModel(),
    onAcceptChallenge: () -> Unit,
    challengeDetails: Challenge = Challenge()){

    var challenge by remember { mutableStateOf(challengeDetails) }
    var actionMessage by remember { mutableStateOf("") }

    LaunchedEffect(key1 = challengeDetails.id) {
        challengeDetailsViewModel.getChallengeDetails(challengeDetails)
    }

    ChallengeDetailsWrapper(
        onSuccess = { challengeDetails ->
            challenge = challengeDetails
        },
        onErrorMessage = {
           //Exception(it)
        },
        onSuccessMessage = {
        })

    val creatorId = challenge.participant.entries.find { it.value.status == "Creator" }?.value?.userId
    val participantId = challenge.participant.entries.find { it.value.status == "Participant" }?.value?.userId

    actionMessage = if (challengeDetailsViewModel.getCurrentUser()?.uid == participantId){
        participantActionMessage(challenge)
    } else if (challengeDetailsViewModel.getCurrentUser()?.uid == creatorId){
        creatorActionMessage()
    }else{
        viewersActionMessage(challenge)
    }

    Column(modifier = Modifier.padding(12.dp)) {
        // ✅ Main Navigation Top Bar
        MainNavigationTopBar(
            details = "Wager Overview",
            handleBackPressed = {
                onBackClicked()
            }
        )
        ConstraintLayout(modifier = Modifier.fillMaxSize().background(color = white).padding(top = 12.dp)) {
            val (card,actionButtons) = createRefs()
            ChallengeDetailUi(
                modifier = Modifier.constrainAs(card) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                challenge = challenge, onAcceptChallenge)


            Column(
                modifier = Modifier.constrainAs(actionButtons){
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ){
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = white))


                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, top = 20.dp),
                    text = actionMessage,
                    style = Typography.labelSmall.copy(fontSize = 14.sp),
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Creator Actions
                if (challengeDetailsViewModel.getCurrentUser()?.uid == creatorId ){
                    // Creator Actions
                    Log.d("Creator", "Creator Message Reached")

                    CreatorActionButton(challenge)

                } else
                    // Participant Actions
                    if (challengeDetailsViewModel.getCurrentUser()?.uid == participantId){

                         ParticipantActionButton(challenge)
                } else {
                    // Viewers Actions

                       ViewersActionButton( challengeDetailsViewModel = challengeDetailsViewModel,
                         challenge = challenge,
                        onAcceptChallenge = onAcceptChallenge)
                }
            }
        }
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