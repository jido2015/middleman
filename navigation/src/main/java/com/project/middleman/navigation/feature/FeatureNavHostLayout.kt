package com.project.middleman.navigation.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.middleman.composables.tab.Tab
import com.middleman.feature.notification.AnimatedNotificationBar
import com.project.middleman.core.common.viewmodel.SharedViewModel
import com.project.middleman.navigation.AnimatedBottomTab
import com.project.middleman.navigation.NavigationRoute
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
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)
        ) {
            AnimatedNotificationBar(modifier, onProceedClicked = {})

            Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(
                topStart = 30.dp,
                topEnd = 30.dp
            )).background(Color.White)) {
                FeatureNavigationHost(
                    navController = navController,
                    messageBarState = messageBarState,
                    sharedViewModel = sharedViewModel,
                    appStateViewModel = appStateViewModel,
                    modifier = modifier
                )
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

