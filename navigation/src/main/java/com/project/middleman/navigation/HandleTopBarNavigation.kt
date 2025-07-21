package com.project.middleman.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.middleman.composables.tab.Tab


@Composable
internal fun HandleTabNavigation(
    selectedTab: Tab,
    currentRoute: String?,
    navController: NavHostController
) {
    LaunchedEffect(selectedTab) {
        val targetRoute = when (selectedTab) {
            Tab.Home -> NavigationRoute.DashboardScreen.route
            Tab.Explore -> NavigationRoute.ChallengeTabScreen.route
        }

        if (currentRoute != targetRoute) {
            navController.navigate(targetRoute) {
                launchSingleTop = true
                restoreState = true
            }
        }
    }
}

@Composable
internal fun UpdateSelectedTabOnNavigation(
    navBackStackEntry: androidx.navigation.NavBackStackEntry?,
    onTabUpdate: (Tab) -> Unit
) {
    LaunchedEffect(navBackStackEntry?.destination?.route) {
        when (navBackStackEntry?.destination?.route) {
            NavigationRoute.ChallengeTabScreen.route -> onTabUpdate(Tab.Explore)
            NavigationRoute.DashboardScreen.route -> onTabUpdate(Tab.Home)
        }
    }
}
