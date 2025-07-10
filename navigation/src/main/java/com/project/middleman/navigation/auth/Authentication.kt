package com.project.middleman.navigation.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.middleman.feature.authentication.AuthViewModel
import com.project.middleman.feature.authentication.presentation.AuthenticationScreen
import com.project.middleman.navigation.NavigationRoute


@Composable
fun AuthNavigationHost(
    authViewModel: AuthViewModel,
    startDestinationName: String,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = startDestinationName,
        modifier = Modifier.fillMaxSize()
    ) {
        authenticationNavigation(
            authViewModel = authViewModel
        )
    }
}


fun NavGraphBuilder.authenticationNavigation(
    authViewModel: AuthViewModel
) {

    composable(route = NavigationRoute.AuthenticationScreen.route) {
        // Ensure visibility is set before the screen is shown

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            AuthenticationScreen(
                //  viewModel = authViewModel,
                onSignIn = {
                    authViewModel.onLoginComplete()
                }
            )
        }
    }

}