package com.project.middleman.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.project.middleman.feature.authentication.presentation.AuthenticationScreen
import com.project.middleman.feature.createchallenge.presentation.CreateChallengeScreen
import com.project.middleman.feature.openchallenges.presentation.ChallengeListScreen
import com.project.middleman.navigation.viewmodel.AppStateViewModel
import com.stevdzasan.messagebar.MessageBarState

fun NavGraphBuilder.featureNavigation(
    appStateViewModel: AppStateViewModel,
    messageBarState: MessageBarState,
    navController: NavHostController
) {

    composable(route = NavigationRoute.AuthenticationScreen.name) {
        // Ensure visibility is set before the screen is shown

            AuthenticationScreen(
                onSignIn = {
                    navController.navigate(NavigationRoute.CreateChallengeScreen.name)
                },
                messageBarState = messageBarState
            )
    }

    composable(route = NavigationRoute.CreateChallengeScreen.name) {
        CreateChallengeScreen(
            onSaveChallenge = {
                navController.navigate(NavigationRoute.ChallengeListScreen.name)
            }
        )
    }

    composable(route = NavigationRoute.ChallengeListScreen.name) {
        ChallengeListScreen(
            messageBarState = messageBarState,
            onChallengeClick = { challenge ->
              //  navController.navigate("challengeDetail/${challenge.id}")
            }
        )
    }

}
