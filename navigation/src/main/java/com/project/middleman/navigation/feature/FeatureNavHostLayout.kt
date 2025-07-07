package com.project.middleman.navigation.feature

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.middleman.feature.notification.AnimatedNotificationBar
import com.project.middleman.core.common.viewmodel.SharedViewModel
import com.project.middleman.navigation.AnimatedBottomTab
import com.project.middleman.navigation.NavigationRoute
import com.stevdzasan.messagebar.MessageBarState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.middleman.composables.topbar.MainToolBar
import com.project.middleman.navigation.HandleTabNavigation
import com.project.middleman.navigation.UpdateSelectedTabOnNavigation

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

    // 🧠 State to control AnimatedNotificationBar
    var isNotificationVisible by remember { mutableStateOf(false) }
    var isRotated by remember { mutableStateOf(false) }

    UpdateSelectedTabOnNavigation(navBackStackEntry) { it }
    HandleTabNavigation(selectedTab, currentRoute, navController)

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
                        messageBarState = messageBarState,
                        sharedViewModel = sharedViewModel,
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
            onTabSelected = onTabSelected,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}


@Composable
fun FeatureNavigationHost(
    navController: NavHostController,
    messageBarState: MessageBarState,
    sharedViewModel: SharedViewModel,
    modifier: Modifier,
    onScrollDown: () -> Unit,
    onScrollUp: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.DashboardScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {
        featureNavigation(navController =navController, messageBarState = messageBarState,
            sharedViewModel = sharedViewModel,
            modifier = modifier, onScrollDown = {
                onScrollDown()}, onScrollUp = {onScrollUp()})
    }
}
