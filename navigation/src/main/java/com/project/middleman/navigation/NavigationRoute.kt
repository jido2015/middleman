package com.project.middleman.navigation

sealed class NavigationRoute(val route: String) {
    object AuthenticationScreen : NavigationRoute("AuthenticationScreen")
    object ChallengeListScreen : NavigationRoute("ChallengeListScreen")
    object CreateChallengeTitleScreen : NavigationRoute("CreateChallengeTitleScreen")
    object InputAmountScreen : NavigationRoute("InputAmountScreen")
    object ChallengeSummaryScreen : NavigationRoute("ChallengeSummaryScreen")
    object ChallengeDetailsScreen : NavigationRoute("ChallengeDetailsScreen")
    object DashboardScreen : NavigationRoute("DashboardScreen")
}