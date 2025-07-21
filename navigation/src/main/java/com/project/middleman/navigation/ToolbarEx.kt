package com.project.middleman.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import com.project.middleman.navigation.viewmodel.AppStateViewModel


@Composable
fun ToolbarSetup(
    currentComposable: String?,
    appStateViewModel: AppStateViewModel,
    toolBarTitle: MutableState<String>,
    toolBarSubTitle: MutableState<String>,
    toolBarVisibility: MutableState<Boolean>
) {

    LaunchedEffect(currentComposable) {
        when (currentComposable) {
            NavigationRoute.AuthenticationScreen.route -> {
              //  appStateViewModel.setTopBarVisibility(toolBarVisibility)
                toolBarTitle.value = "Welcome Back,"
                toolBarSubTitle.value = "Please log in to your account"
            }
            NavigationRoute.ChallengeTabScreen.route -> {
              //  appStateViewModel.setTopBarVisibility(toolBarVisibility)
                toolBarTitle.value = "Marketplace"
                toolBarSubTitle.value = MarketplaceSubtitle.random()
            }
            NavigationRoute.ChallengeDetailsScreen.route -> {
                //appStateViewModel.setTopBarVisibility(toolBarVisibility)
                toolBarTitle.value = "Challenge Overview"
                toolBarSubTitle.value = ChallengeOverviewSubtitle.random()
            }
        }
    }
}

enum class MarketplaceSubtitle(val text: String) {
    BROWSE("Browse live challenges and pick your next match."),
    JOIN("Join games, place wagers, and compete to win."),
    REAL_TIME("Real-time challenges across your favorite games."),
    CHOOSE("Choose a game. Accept a wager. Show your skills."),
    EXPLORE("Explore head-to-head matchups and make your move."),
    ARENA("The arena is live. Find your challenge now."),
    WAGERS_LIVE("Wagers are live — who will you take on?");

    companion object {
        fun random(): String {
            return entries.toTypedArray().random().text
        }
    }
}

enum class ChallengeOverviewSubtitle(val text: String) {
    REVIEW("Review the details and join the challenge."),
    SEE("See who you're up against and what's at stake."),
    INFO("Game rules, opponent info, and wager amount."),
    NEED_TO_KNOW("Everything you need to know before playing."),
    READY("Ready up—this is your chance to win."),
    CONFIRM("Confirm the matchup and accept the wager.");

    companion object {
        fun random(): String {
            return entries.toTypedArray().random().text
        }
    }
}

