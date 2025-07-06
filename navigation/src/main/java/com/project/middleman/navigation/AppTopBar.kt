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
            Tab.Home -> NavigationRoute.ChallengeListScreen.route
            Tab.Add -> NavigationRoute.CreateChallengeScreen.route
            Tab.Explore -> NavigationRoute.ChallengeListScreen.route
        }

        // Only navigate if we're not already on the target route
        if (currentRoute != targetRoute) {
            try {
                // Check if the route exists in the current navigation graph
                val graph = navController.graph
                val destination = graph.findNode(targetRoute)
                
                if (destination != null) {
                    navController.navigate(targetRoute) {
                        launchSingleTop = true
                        restoreState = true
                    }
                } else {
                    // If the route doesn't exist in the current graph, 
                    // it might be because we're in the feature navigation
                    // In this case, we'll let the feature navigation handle it
                    println("Route $targetRoute not found in current navigation graph")
                }
            } catch (e: Exception) {
                // Handle navigation errors gracefully
                println("Navigation error: ${e.message}")
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
