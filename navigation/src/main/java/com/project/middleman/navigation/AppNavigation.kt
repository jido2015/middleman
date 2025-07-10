package com.project.middleman.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.project.middleman.feature.authentication.AuthViewModel
import com.project.middleman.navigation.auth.AuthNavigationHost
import com.project.middleman.navigation.feature.FeatureContentLayout
import com.project.middleman.navigation.viewmodel.AppStateViewModel
private fun getStartDestination(isAuthenticated: Boolean): NavigationRoute {
    return if (isAuthenticated) {
        NavigationRoute.DashboardScreen
    } else {
        NavigationRoute.AuthenticationScreen
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val isAuthenticated by authViewModel.isUserAuthenticated.collectAsState()

    // State management
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val appStateViewModel: AppStateViewModel = hiltViewModel()

    // Derived state
    val startDestinationName by remember {
        derivedStateOf {
            getStartDestination(isAuthenticated)
        }
    }

    val canPop = navController.previousBackStackEntry != null
    canPop && currentRoute != NavigationRoute.DashboardScreen.route

    Scaffold(
       contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->

        Box(
            Modifier.safeContentPadding()
        )
        Box(
            modifier = Modifier.fillMaxSize().padding(
                bottom = innerPadding.calculateBottomPadding()  // only bottom padding
            )
        ) {
            if (isAuthenticated) {
                    FeatureContentLayout(
                        navController = navController,
                        currentRoute = currentRoute,
                        appStateViewModel = appStateViewModel,
                        modifier = modifier)

            } else{
                AuthNavigationHost(
                    authViewModel = authViewModel,
                    navController = navController,
                    startDestinationName = startDestinationName.route,
                )
            }
        }
    }
}
