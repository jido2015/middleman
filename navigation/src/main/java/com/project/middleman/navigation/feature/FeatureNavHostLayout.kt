package com.project.middleman.navigation.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.middleman.composables.tab.Tab
import com.middleman.composables.topBar.MainToolBar
import com.middleman.feature.notification.AnimatedNotificationBar
import com.project.middleman.core.common.viewmodel.SharedViewModel
import com.project.middleman.navigation.AnimatedBottomTab
import com.project.middleman.navigation.NavigationRoute
import com.stevdzasan.messagebar.MessageBarState
import androidx.compose.runtime.getValue

@Composable
fun FeatureContentLayout(
    navController: NavHostController,
    currentRoute: String?,
    messageBarState: MessageBarState,
    sharedViewModel: SharedViewModel,
    selectedTab: Tab,
    onTabSelected: (Tab) -> Unit,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    // Handle tab navigation within the feature navigation context
    LaunchedEffect(selectedTab) {
        val targetRoute = when (selectedTab) {
            Tab.Home -> NavigationRoute.ChallengeListScreen.route
            Tab.Add -> NavigationRoute.CreateChallengeScreen.route
            Tab.Explore -> NavigationRoute.ChallengeListScreen.route
        }

        // Only navigate if we're not already on the target route
        if (currentRoute != targetRoute) {
            try {
                navController.navigate(targetRoute) {
                    launchSingleTop = true
                    restoreState = true
                }
            } catch (e: Exception) {
                println("Feature navigation error: ${e.message}")
            }
        }
    }

    // Update selected tab based on current route
    LaunchedEffect(navBackStackEntry?.destination?.route) {
        when (navBackStackEntry?.destination?.route) {
            NavigationRoute.ChallengeListScreen.route -> onTabSelected(Tab.Home)
            NavigationRoute.CreateChallengeScreen.route -> onTabSelected(Tab.Add)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Green)
        ) {
            AnimatedNotificationBar(modifier, onProceedClicked = {})

            Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(
                topStart = 30.dp,
                topEnd = 30.dp
            )).background(Color.White)) {
                Column {
                    MainToolBar(
                        showTopBar = true,
                        toolBarTitle = "Dashboard",
                        profilePhoto = "", // placeholder URL
                        showBackButton = true,
                        onBackClick = {}
                    )
                    FeatureNavigationHost(
                        navController = navController,
                        messageBarState = messageBarState,
                        sharedViewModel = sharedViewModel,
                        modifier = modifier
                    )
                }
            }

        }

        AnimatedBottomTab(
            selectedTab = selectedTab,
            onTabSelected = onTabSelected,
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }

}

@Composable
fun FeatureNavigationHost(
    navController: NavHostController,
    messageBarState: MessageBarState,
    sharedViewModel: SharedViewModel,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.ChallengeListScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {
        featureNavigation(navController =navController, messageBarState = messageBarState,
            sharedViewModel = sharedViewModel,
            modifier = modifier)
    }
}

