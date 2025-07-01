package com.project.middleman.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.project.middleman.challengedetails.presentation.ChallengeDetailsScreen
import com.project.middleman.core.common.viewmodel.SharedViewModel
import com.project.middleman.feature.authentication.presentation.AuthenticationScreen
import com.project.middleman.feature.createchallenge.presentation.CreateChallengeScreen
import com.project.middleman.feature.openchallenges.presentation.ChallengeListScreen
import com.project.middleman.navigation.viewmodel.AppStateViewModel
import com.stevdzasan.messagebar.MessageBarState

fun NavGraphBuilder.authenticationNavigation(
    modifier: Modifier,
    navController: NavHostController,
    messageBarState: MessageBarState
) {

    composable(route = NavigationRoute.AuthenticationScreen.route) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            AuthenticationScreen(
                onSignIn = {
                    navController.navigate(NavigationRoute.ChallengeListScreen.route) {
                        popUpTo(NavigationRoute.ChallengeListScreen.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                messageBarState = messageBarState,
            )
        }
    }

}
fun NavGraphBuilder.featureNavigation(
    modifier: Modifier,
    appStateViewModel: AppStateViewModel,
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

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ChallengeListScreen(
                messageBarState = messageBarState,
                onCardChallengeClick = { challenge ->
                    sharedViewModel.challenge = challenge
                    navController.navigate(NavigationRoute.ChallengeDetailsScreen.route)
                }
            )
        }
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
