package com.project.middleman.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.middleman.composables.tab.Tab
import com.project.middleman.core.common.viewmodel.SharedViewModel
import com.project.middleman.feature.authentication.AuthViewModel
import com.project.middleman.navigation.auth.AuthNavigationHost
import com.project.middleman.navigation.feature.FeatureContentLayout
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarState
import com.stevdzasan.messagebar.rememberMessageBarState

private fun getStartDestination(isAuthenticated: Boolean): NavigationRoute {
    return if (isAuthenticated) {
        NavigationRoute.ChallengeListScreen
    } else {
        NavigationRoute.AuthenticationScreen
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    messageBarState: MessageBarState = rememberMessageBarState(),
    authViewModel: AuthViewModel = hiltViewModel()
) {

    val isAuthenticated by authViewModel.isUserAuthenticated.collectAsState()

    // State management
    val sharedViewModel: SharedViewModel = viewModel()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var selectedTab by rememberSaveable { mutableStateOf(Tab.Home) }

    // Derived state
    val startDestinationName by remember {
        derivedStateOf {
            getStartDestination(isAuthenticated)
        }
    }


    val canPop = navController.previousBackStackEntry != null
    val showBackButton = canPop && currentRoute != NavigationRoute.ChallengeListScreen.route


    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
    ) { innerPadding ->
        Box(
            Modifier.safeContentPadding()
        ) {
            ContentWithMessageBar(
                messageBarState = messageBarState,
                modifier = Modifier.padding(
                    bottom = innerPadding.calculateBottomPadding()  // only bottom padding
                )
            ) {
                if (isAuthenticated) {
                    FeatureContentLayout(
                        navController = navController,
                        selectedTab = selectedTab,
                        currentRoute = currentRoute,
                        onTabSelected = { selectedTab = it },
                        messageBarState = messageBarState,
                        sharedViewModel = sharedViewModel,
                        modifier = modifier
                    )
                } else {
                    AuthNavigationHost(
                        authViewModel = authViewModel,
                        navController = navController,
                        messageBarState = messageBarState,
                        startDestinationName = startDestinationName.route,
                    )
                }
            }
        }
    }
}
