package com.project.middleman.navigation

sealed class NavigationRoute(val route: String) {
    object ChallengeTabScreen : NavigationRoute("ChallengeTabScreen")
    object CreateChallengeTitleScreen : NavigationRoute("CreateChallengeTitleScreen")
    object InputAmountScreen : NavigationRoute("InputAmountScreen")
    object DescriptionScreen : NavigationRoute("DescriptionScreen")
    object ChallengeSummaryScreen : NavigationRoute("ChallengeSummaryScreen")
    object ChallengeDetailsScreen : NavigationRoute("ChallengeDetailsScreen")
    object DashboardScreen : NavigationRoute("DashboardScreen")
}


sealed class AuthNavigationRoute(val route: String) {
    object AccountSetupScreen : NavigationRoute("AccountSetupScreen")
    object FullNameScreen : NavigationRoute("FullNameScreen")
    object PhoneLineScreen : NavigationRoute("PhoneLineScreen")
    object NumberVerificationScreen : NavigationRoute("NumberVerificationScreen")
    object DisplayNameScreen : NavigationRoute("DisplayNameScreen")
    object DateOfBirthScreen : NavigationRoute("DateOfBirthScreen")

}