package com.project.middleman.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.middleman.composables.tab.Tab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AppTopBar(
    showTopBar: Boolean,
    toolBarTitle: String,
    showBackButton: Boolean,
    onBackClick: () -> Unit
) {
    if (showTopBar) {
        TopAppBar(
            title = { Text(text = toolBarTitle) },
            navigationIcon = {
                if (showBackButton) {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            }
        )
    }
}

@Composable
internal fun HandleTabNavigation(
    selectedTab: Tab,
    currentRoute: String?,
    navController: androidx.navigation.NavHostController
) {
    LaunchedEffect(selectedTab) {
        val targetRoute = when (selectedTab) {
            Tab.Home -> NavigationRoute.ChallengeListScreen
            Tab.Add -> NavigationRoute.CreateChallengeScreen
            Tab.Explore -> NavigationRoute.ChallengeListScreen
        }

        if (currentRoute != targetRoute.route) {
            navController.navigate(targetRoute.route) {
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
            NavigationRoute.ChallengeListScreen.route -> onTabUpdate(Tab.Home)
            NavigationRoute.CreateChallengeScreen.route -> onTabUpdate(Tab.Add)
        }
    }
}
