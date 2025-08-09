package com.project.middleman.feature.openchallenges.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.project.middleman.core.source.data.model.Challenge
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth
import com.project.middleman.feature.openchallenges.presentation.compoenents.ChallengeDialog
import com.project.middleman.feature.openchallenges.presentation.uistate_handler.FetchChallengeResponseHandler
import com.project.middleman.feature.openchallenges.presentation.uistate_handler.UpdateChallengeWrapper
import com.project.middleman.feature.openchallenges.viewmodel.OpenChallengeViewModel

@Composable
fun ChallengeListScreen(
    onCardChallengeClick: (Challenge) -> Unit,
    openChallengeViewModel: OpenChallengeViewModel = hiltViewModel()
) {
    var challenges by remember { mutableStateOf(emptyList<Challenge>()) }

    UpdateChallengeWrapper(
        onErrorMessage = { message ->
            //Exception(message)
        },
        onSuccessMessage = {
            //"Challenge Accepted"
        }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        openChallengeViewModel.setLoading(true)
        openChallengeViewModel.fetchChallenges()

        fun getChallenges(result: List<Challenge>) {
            openChallengeViewModel.setLoading(false)
            challenges = result
            Log.d("getChallenges", result.toString())
        }

        //Observe challenges
        FetchChallengeResponseHandler(

            getChallenges = {
                getChallenges(it)
            },
            onErrorMessage = {
             //   Exception(it)
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(challenges) { challenge ->
                ChallengeCardItem(
                    userUid = FirebaseAuth.getInstance().currentUser,
                    challenge = challenge,
                    onChallengeClick = {

                        onCardChallengeClick(challenge)
                       // updatedChallenge = it
                        //Show dialog to accept challenge
                       // showDialog = true
                    })
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }


}


@Preview(showBackground = true)
@Composable
fun ChallengeListPreview() {
    ChallengeListScreen(
        onCardChallengeClick = {}
    )

}
