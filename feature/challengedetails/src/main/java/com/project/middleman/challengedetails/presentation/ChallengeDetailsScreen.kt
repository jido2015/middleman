package com.project.middleman.challengedetails.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.middleman.challengedetails.presentation.uistate_handler.ChallengeDetailsWrapper
import com.project.middleman.challengedetails.viewmodel.ChallengeDetailsViewModel
import com.project.middleman.core.source.data.model.Challenge

@Composable
fun ChallengeDetailsScreen(
    challengeDetailsViewModel: ChallengeDetailsViewModel = hiltViewModel(),
    onAcceptChallenge: () -> Unit,
    challengeDetails: Challenge){

    var challenge by remember { mutableStateOf(challengeDetails) }
    challengeDetailsViewModel.getChallengeDetails(challengeDetails)

    ChallengeDetailsWrapper(
        onSuccess = { challengeDetails ->
            challenge = challengeDetails
        },
        onErrorMessage = {
           //Exception(it)
        },
        onSuccessMessage = {
        })


    ConstraintLayout {
        val (details, acceptChallenge) = createRefs()

        Column(
            modifier = Modifier.constrainAs(details) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                bottom.linkTo(acceptChallenge.top)
            }
        ){

            Text(text = "Title")
            Text(text = challenge.title)
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Terms and Conditions")
            Text(text = challenge.description)
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Stake")
            Text(text = (challenge.payoutAmount/2).toString())
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Participants")
            Text(text = challenge.participant.entries.find {
                it.value.status == "Creator" }?.value?.name ?: "Unknown Creator")
            Text(text = challenge.participant.entries.find {
                it.value.status == "Participant" }?.value?.name ?: "No participant yet.")

        }


        if (challengeDetailsViewModel.getCurrentUser() == challenge.participant.entries.find{
                it.value.status == "Creator"}?.value?.userId ){

            Button(onClick = {

                //"Complete"
            }, modifier = Modifier.constrainAs(acceptChallenge) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
                Text(text = "")
            }
        } else{
            Button(onClick = {
                challengeDetailsViewModel.onChallengeAccepted(challenge)
                onAcceptChallenge()

                //"Challenge Accepted"
            }, modifier = Modifier.constrainAs(acceptChallenge) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
                Text(text = "Accept & Join")
            }
        }

    }
}