package com.project.middleman.navigation

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.project.middleman.feature.authentication.viewmodel.AuthViewModel
import com.project.middleman.navigation.auth.AuthNavigationHost
import com.project.middleman.navigation.feature.FeatureContentLayout
import com.project.middleman.navigation.viewmodel.AppStateViewModel

private fun getStartDestination(isAuthenticated: Boolean): NavigationRoute {
    return if (isAuthenticated) {
        NavigationRoute.DashboardScreen
    } else {
        AuthNavigationRoute.AccountSetupScreen
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel()
) {
   val isAuthenticated by authViewModel.isUserAuthenticated.collectAsState()

    // Separate NavControllers for auth and feature flows
    val authNavController = rememberNavController()
    val featureNavController = rememberNavController()
    
    val navBackStackEntry by featureNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    val activity = LocalActivity.current as ComponentActivity
    val appStateViewModel: AppStateViewModel = hiltViewModel(activity)

    // Derived state
    val startDestinationName by remember {
        derivedStateOf {
            getStartDestination(isAuthenticated)
        }
    }

    // Handle authentication state changes
    LaunchedEffect(isAuthenticated) {
        if (isAuthenticated) {
            // When user becomes authenticated, navigate to dashboard
            featureNavController.navigate(NavigationRoute.DashboardScreen.route) {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

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
                    navController = featureNavController,
                    currentRoute = currentRoute,
                    appStateViewModel = appStateViewModel,
                    modifier = modifier
                )
            } else {
                AuthNavigationHost(
                    authViewModel = authViewModel,
                    navController = authNavController,
                    appStateViewModel = appStateViewModel,
                    startDestinationName = startDestinationName.route,
                )
            }
        }
    }
}
