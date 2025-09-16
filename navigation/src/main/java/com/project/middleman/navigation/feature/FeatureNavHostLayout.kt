package com.project.middleman.navigation.feature

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.middleman.composables.topbar.NavigationTopBarWithProgressBar
import com.middleman.feature.dashboard.presentation.CreateBetModalSheet
import com.project.middleman.designsystem.themes.white
import com.project.middleman.feature.createchallenge.viewmodel.CreateChallengeViewModel
import com.project.middleman.navigation.HandleTabNavigation
import com.project.middleman.navigation.UpdateSelectedTabOnNavigation
import com.project.middleman.core.common.appstate.viewmodel.AppStateViewModel
import com.project.middleman.feature.authentication.viewmodel.AuthViewModel
import com.project.middleman.feature.authentication.viewmodel.CreateProfileViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FeatureContentLayout(
    authViewModel: AuthViewModel,
    navController: NavHostController,
    currentRoute: String?,
    appStateViewModel: AppStateViewModel,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var selectedTab by rememberSaveable { mutableStateOf(Tab.Home) }
    val showCreateWagerSheet by appStateViewModel.showCreateWagerSheet.collectAsState()
    val showBottomBarSheet by appStateViewModel.showBottomTabSheet.collectAsState()
    val showNotificationBarSheet by appStateViewModel.showNotificationBarSheet.collectAsState()
    val showNavigationTopBarSheet by appStateViewModel.showNavigationTopBarSheet.collectAsState()
    val navigationCurrentProgress by appStateViewModel.navigationCurrentProgress.collectAsState()
    val navigationTitle by appStateViewModel.navigationTitle.collectAsState()
    val createProfileViewModel: CreateProfileViewModel = hiltViewModel()

    // 🧠 State to control AnimatedNotificationBar
    var isNotificationVisible by remember { mutableStateOf(false) }
    var isRotated by remember { mutableStateOf(false) }

    // Create ViewModel once at the parent level to prevent recreation
    val createViewModel: CreateChallengeViewModel = hiltViewModel()

    // ✅ Update selected tab on navigation
    UpdateSelectedTabOnNavigation(navBackStackEntry) { it }

        // ✅ Create a modal bottom sheet
        CreateBetModalSheet(
            openBottomSheet = showCreateWagerSheet,
            onDismissRequest = { appStateViewModel.setCreateWagerVisibility(false) },
            onNewBetClicked = {
                appStateViewModel.setCreateWagerVisibility(false)
                navController.navigate(NavigationRoute.CreateChallengeTitleScreen.route)

            },
            onCreateFromExistingClicked = { /* handle existing bet */ }
        )


    // ✅ Handle tab navigation

    if(showBottomBarSheet){
        HandleTabNavigation(selectedTab, currentRoute, navController)
    }

    //
    Box(modifier = Modifier.fillMaxSize().background(if (showNotificationBarSheet){
        Color.Black } else { Color.Transparent })) {

        Column(modifier = Modifier.fillMaxSize()
        ) {

            // ✅ Notification Top Modal Sheet controlled by scroll
            AnimatedNotificationBar(
                isNotificationBarSheet = showNotificationBarSheet,
                isMessageVisible = isNotificationVisible,
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
            ) {
                Column(modifier = Modifier.fillMaxSize().background(white)) {

                    // ✅ Navigation Top Bar With Progress Bar
                    NavigationTopBarWithProgressBar(
                        handleBackPressed = { navController.popBackStack() },
                        title = navigationTitle,
                        progress = navigationCurrentProgress/5f,
                        showNavigationTopBarSheet = showNavigationTopBarSheet,
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 16.dp))

                    // ✅ Pass real scroll callbacks


                    NavHost(
                        navController = navController,
                        startDestination = currentRoute!!,
                        modifier = Modifier.fillMaxSize()
                    ){
                        featureNavigation(
                            backStackEntry = navBackStackEntry,
                            authViewModel = authViewModel,
                            navController = navController,
                            onScrollDown = {
                                if (isNotificationVisible) {
                                    isNotificationVisible = false
                                    isRotated = false
                                }
                            },
                            onScrollUp = {

                            }, // Pass the ViewModel from parent
                            appStateViewModel = appStateViewModel,
                            createChallengeViewModel = createViewModel,
                            createProfileViewModel = createProfileViewModel
                        )
                    }

                }
            }
        }

        AnimatedBottomTab(
            showBottomBarSheet = showBottomBarSheet,
            selectedTab = selectedTab,
            onTabSelected = {selectedTab = it},
            modifier = Modifier.align(Alignment.BottomCenter),
            onCreateButtonSelected = {
                appStateViewModel.setCreateWagerVisibility(true)
            }
        )
    }
}

