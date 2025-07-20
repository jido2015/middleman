package com.project.middleman.navigation.feature

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.middleman.feature.dashboard.presentation.DashboardScreen
import com.project.middleman.challengedetails.presentation.ChallengeDetailsScreen
import com.project.middleman.feature.createchallenge.presentation.CreateChallengeTitleScreen
import com.project.middleman.feature.createchallenge.presentation.InputAmountScreen
import com.project.middleman.feature.createchallenge.presentation.ChallengeSummaryScreen
import com.project.middleman.feature.createchallenge.viewmodel.CreateChallengeViewModel
import com.project.middleman.feature.openchallenges.presentation.ChallengeListScreen
import com.project.middleman.navigation.NavigationRoute
import com.project.middleman.navigation.viewmodel.AppStateViewModel


fun NavGraphBuilder.featureNavigation(
    modifier: Modifier,
    navController: NavHostController,
    onScrollDown: () -> Unit,
    onScrollUp: () -> Unit,
    appStateViewModel: AppStateViewModel,
    createViewModel: CreateChallengeViewModel,
) {


    // Create Title for Challenge Routes
    composable(route = NavigationRoute.CreateChallengeTitleScreen.route) {
        appStateViewModel.setBottomBarVisibility(false)
        appStateViewModel.setTopBarVisibility(false)
        appStateViewModel.setNavigationTopBarVisibility(true)
        appStateViewModel.setNavigationCurrentProgress(1f)
        appStateViewModel.setNavigationTitle("Create Wager")
        CreateChallengeTitleScreen(
            viewModel = createViewModel,
            onSaveChallenge = {
                navController.navigate(NavigationRoute.InputAmountScreen.route)
            }
        )
    }

    // Create Stake for Challenge Routes
    composable(route = NavigationRoute.InputAmountScreen.route) {
        appStateViewModel.setTopBarVisibility(false)
        appStateViewModel.setNavigationTopBarVisibility(true)
        appStateViewModel.setNavigationCurrentProgress(2f)
        appStateViewModel.setNavigationTitle("Stake Amount")
        InputAmountScreen(
            viewModel = createViewModel,
            onCreateChallenge = {
                navController.navigate(NavigationRoute.ChallengeSummaryScreen.route)
            }
        )
    }

    // Tag a friend for Challenge Routes
    composable(route = NavigationRoute.ChallengeSummaryScreen.route) {
        appStateViewModel.setNavigationCurrentProgress(3f)
        appStateViewModel.setNavigationTitle("Review Your Wager")
        ChallengeSummaryScreen(
            viewModel = createViewModel,
            createWagerButton = {
                navController.navigate(NavigationRoute.DashboardScreen.route) {
                    popUpTo(0) // Clear all back stack
                    launchSingleTop = true // Avoid re-creating if already on that screen
                }

            }
        )
    }

    composable(route = NavigationRoute.DashboardScreen.route) {
        appStateViewModel.setBottomBarVisibility(true)
        appStateViewModel.setNavigationTopBarVisibility(false)

        DashboardScreen(

            onProceedClicked = {}, onScrollDown = {
            onScrollDown()
            Log.d("ScrollDownT", "Scrolling Down")
        }, onScrollUp = {
            onScrollUp()
        },
            createWagerButton = {
                appStateViewModel.setCreateWagerVisibility(true)
            }
        )

    }

    composable(route = NavigationRoute.ChallengeListScreen.route) {
        appStateViewModel.setBottomBarVisibility(true)
        appStateViewModel.setNavigationTopBarVisibility(false)
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {


            ChallengeListScreen(
                onCardChallengeClick = { challenge ->
                   // sharedViewModel.challenge = challenge
                    navController.navigate(NavigationRoute.ChallengeDetailsScreen.route)
                }
            )
        }
    }

    composable(route = NavigationRoute.ChallengeDetailsScreen.route) {
        appStateViewModel.setBottomBarVisibility(true)
        appStateViewModel.setNavigationTopBarVisibility(false)

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val challenge = appStateViewModel.challenge
            if (challenge != null) {

                ChallengeDetailsScreen(
                    challengeDetails = challenge,
                    onAcceptChallenge = {}
                )
            }
        }
    }
}
