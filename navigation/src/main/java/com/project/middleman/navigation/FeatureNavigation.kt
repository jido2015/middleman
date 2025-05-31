package com.project.middleman.navigation

import androidx.compose.ui.graphics.BlendMode.Companion.Screen
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

        //toolBarVisibility.value = false
        AuthenticationScreen(
            onSignIn = {
                navController.navigate(NavigationRoute.ChallengeListScreen.name){
                    launchSingleTop = true
                    popUpTo(NavigationRoute.ChallengeListScreen.name) {
                        inclusive = true
                    }
                }
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
        // toolBarVisibility.value = true
        ChallengeListScreen(
            messageBarState = messageBarState,
            onCardChallengeClick = { challenge ->
                //  navController.navigate("challengeDetail/${challenge.id}")
            }
        )
    }
}
