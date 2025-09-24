package com.project.middleman.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import com.middleman.composables.tab.Tab


@Composable
internal fun HandleTabNavigation(
    selectedTab: Tab,
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry?
) {
    LaunchedEffect(selectedTab) {
        val targetRoute = when (selectedTab) {
            Tab.Home -> NavigationRoute.DashboardScreen.route
            Tab.Explore -> NavigationRoute.ChallengeTabScreen.route
            Tab.DEFAULT -> return@LaunchedEffect
        }


        if (navBackStackEntry?.destination?.route != targetRoute) {
            Log.d("HandleTabNavigation", "Navigating to $targetRoute from ${navBackStackEntry?.id}")
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
