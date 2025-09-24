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
import com.project.middleman.core.source.data.model.Challenge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.middleman.feature.openchallenges.presentation.challengeui.ActiveChallengeUi
import com.project.middleman.feature.openchallenges.presentation.challengeui.CompletedChallengeUi
import com.project.middleman.feature.openchallenges.presentation.challengeui.OpenChallengeUi
import com.project.middleman.feature.openchallenges.presentation.compoenents.OpenChallengeTabPager
import com.project.middleman.feature.openchallenges.presentation.uistate_handler.FetchChallengeResponseHandler
import com.project.middleman.feature.openchallenges.presentation.uistate_handler.UpdateChallengeWrapper
import com.project.middleman.feature.openchallenges.viewmodel.OpenChallengeViewModel

@Composable
fun ChallengeListScreen(
    onCardChallengeClick: (Challenge) -> Unit,
    openChallengeViewModel: OpenChallengeViewModel = hiltViewModel(),
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


        val pages = listOf<@Composable () -> Unit>(
            {
                OpenChallengeUi(
                    challenges = challenges,
                    onCardChallengeClick = onCardChallengeClick
                )
            },
            {
                ActiveChallengeUi(
                    challenges = challenges,
                    onCardChallengeClick = onCardChallengeClick
                )
            },
            {
                CompletedChallengeUi(
                    challenges = challenges,
                    onCardChallengeClick = onCardChallengeClick
                )
            }
        )

        OpenChallengeTabPager(pages = pages)

    }
}


@Preview(showBackground = true)
@Composable
fun ChallengeListPreview() {
    ChallengeListScreen(
        onCardChallengeClick = {}
    )

}
