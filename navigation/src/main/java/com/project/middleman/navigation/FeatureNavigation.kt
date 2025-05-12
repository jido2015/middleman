package com.project.middleman.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.project.middleman.feature.authentication.presentation.AuthenticationScreen
import com.project.middleman.feature.createchallenge.presentation.ChallengeScreen
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

            ChallengeScreen()
    }
}
