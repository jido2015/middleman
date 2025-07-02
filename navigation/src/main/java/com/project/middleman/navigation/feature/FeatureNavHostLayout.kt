package com.project.middleman.navigation.feature

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.middleman.composables.tab.Tab
import com.project.middleman.core.common.viewmodel.SharedViewModel
import com.project.middleman.navigation.AnimatedBottomTab
import com.project.middleman.navigation.NavigationRoute
import com.project.middleman.navigation.feature.featureNavigation
import com.project.middleman.navigation.viewmodel.AppStateViewModel
import com.stevdzasan.messagebar.MessageBarState


@Composable
fun FeatureContentLayout(
    navController: NavHostController,
    currentRoute: String?,
    messageBarState: MessageBarState,
    sharedViewModel: SharedViewModel,
    appStateViewModel: AppStateViewModel,
    selectedTab: Tab,
    onTabSelected: (Tab) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedBottomTab(
            selectedTab = selectedTab,
            onTabSelected = onTabSelected,
            currentRoute = currentRoute,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp)
        ) {
                FeatureNavigationHost(
                navController = navController,
                messageBarState = messageBarState,
                sharedViewModel = sharedViewModel,
                appStateViewModel = appStateViewModel,
                modifier = modifier
            )
        }
    }
}

@Composable
fun FeatureNavigationHost(
    navController: NavHostController,
    messageBarState: MessageBarState,
    sharedViewModel: SharedViewModel,
    appStateViewModel: AppStateViewModel,
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
