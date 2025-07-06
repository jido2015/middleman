package com.project.middleman.navigation.feature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.middleman.feature.dashboard.presentation.DashboardScreen
import com.project.middleman.challengedetails.presentation.ChallengeDetailsScreen
import com.project.middleman.core.common.viewmodel.SharedViewModel
import com.project.middleman.feature.createchallenge.presentation.CreateChallengeScreen
import com.project.middleman.navigation.NavigationRoute
import com.stevdzasan.messagebar.MessageBarState


fun NavGraphBuilder.featureNavigation(
    modifier: Modifier,
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    messageBarState: MessageBarState,
) {

    composable(route = NavigationRoute.CreateChallengeScreen.route) {

        CreateChallengeScreen(
            onSaveChallenge = {
                navController.navigate(NavigationRoute.ChallengeListScreen.route)
            }
        )
    }

    composable(route = NavigationRoute.ChallengeListScreen.route) {

        DashboardScreen(onProceedClicked = {})
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//
//
//
//            ChallengeListScreen(
//                messageBarState = messageBarState,
//                onCardChallengeClick = { challenge ->
//                    sharedViewModel.challenge = challenge
//                    navController.navigate(NavigationRoute.ChallengeDetailsScreen.route)
//                }
//            )
//        }
    }

    composable(route = NavigationRoute.ChallengeDetailsScreen.route) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val challenge = sharedViewModel.challenge
            if (challenge != null) {

                ChallengeDetailsScreen(
                    messageBarState = messageBarState,
                    challengeDetails = challenge,
                    onAcceptChallenge = {}
                )
            }
        }
    }
}
