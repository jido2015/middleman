package com.project.middleman.navigation

sealed class NavigationRoute(val route: String) {
    object AuthenticationScreen : NavigationRoute("AuthenticationScreen")
    object ChallengeListScreen : NavigationRoute("ChallengeListScreen")
    object CreateChallengeScreen : NavigationRoute("CreateChallengeScreen")
    object ChallengeDetailsScreen : NavigationRoute("ChallengeDetailsScreen")
    object DashboardScreen : NavigationRoute("DashboardScreen")
}