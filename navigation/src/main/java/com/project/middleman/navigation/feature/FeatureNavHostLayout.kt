package com.project.middleman.navigation.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.middleman.composables.tab.Tab
import com.middleman.feature.notification.AnimatedNotificationBar
import com.project.middleman.navigation.AnimatedBottomTab
import com.project.middleman.navigation.NavigationRoute
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.middleman.composables.topbar.MainToolBar
import com.middleman.feature.dashboard.presentation.CreateBetModalSheet
import com.project.middleman.navigation.HandleTabNavigation
import com.project.middleman.navigation.UpdateSelectedTabOnNavigation
import com.project.middleman.navigation.viewmodel.AppStateViewModel

@Composable
fun FeatureContentLayout(
    navController: NavHostController,
    currentRoute: String?,
    appStateViewModel: AppStateViewModel,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var selectedTab by rememberSaveable { mutableStateOf(Tab.Home) }
    val showCreateWagerSheet by appStateViewModel.showCreateWagerSheet.collectAsState()


    // 🧠 State to control AnimatedNotificationBar
    var isNotificationVisible by remember { mutableStateOf(false) }
    var isRotated by remember { mutableStateOf(false) }

    // ✅ Update selected tab on navigation
    UpdateSelectedTabOnNavigation(navBackStackEntry) { it }

        // ✅ Create a modal bottom sheet
        CreateBetModalSheet(
            openBottomSheet = showCreateWagerSheet,
            onDismissRequest = { appStateViewModel.setShowCreateWagerSheet(false) },
            onNewBetClicked = {
                appStateViewModel.setShowCreateWagerSheet(false)
                navController.navigate(NavigationRoute.CreateChallengeScreen.route)
            },
            onCreateFromExistingClicked = { /* handle existing bet */ }
        )


    // ✅ Handle tab navigation
    HandleTabNavigation(selectedTab, currentRoute, navController)

    //
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)
        ) {

            // ✅ Notification controlled by scroll
            AnimatedNotificationBar(
                modifier = modifier,
                visible = isNotificationVisible,
                isRotated = isRotated,
                onToggle = {
                    isNotificationVisible = !isNotificationVisible
                    isRotated = !isRotated
                },
                onProceedClicked = {}
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Color.White)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {

                    MainToolBar(
                        showTopBar = true,
                        toolBarTitle = "Dashboard",
                        profilePhoto = "",
                        showBackButton = true,
                        onBackClick = {}
                    )

                    // ✅ Pass real scroll callbacks
                    FeatureNavigationHost(
                        navController = navController,
                        appStateViewModel = appStateViewModel,
                        modifier = modifier,
                        onScrollDown = {
                            if (isNotificationVisible) {
                                isNotificationVisible = false
                                isRotated = false
                            }

                        },
                        onScrollUp = {

                        }
                    )
                }
            }
        }

        AnimatedBottomTab(
            selectedTab = selectedTab,
            onTabSelected = {selectedTab = it},
            modifier = Modifier.align(Alignment.BottomCenter),
            onCreateButtonSelected = {appStateViewModel.setShowCreateWagerSheet(true)}
        )
    }
}


@Composable
fun FeatureNavigationHost(
    navController: NavHostController,
    appStateViewModel: AppStateViewModel,
    modifier: Modifier,
    onScrollDown: () -> Unit,
    onScrollUp: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.DashboardScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {
        featureNavigation(navController = navController,
            appStateViewModel = appStateViewModel,
            modifier = modifier, onScrollDown = {
                onScrollDown()}, onScrollUp = {onScrollUp()})
    }
}
