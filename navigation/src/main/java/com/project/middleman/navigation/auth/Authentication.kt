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
import com.stevdzasan.messagebar.MessageBarState


@Composable
fun AuthNavigationHost(
    authViewModel: AuthViewModel,
    startDestinationName: String,
    navController: NavHostController,
    messageBarState: MessageBarState,
) {
    NavHost(
        navController = navController,
        startDestination = startDestinationName,
        modifier = Modifier.fillMaxSize()
    ) {
        authenticationNavigation(
            authViewModel = authViewModel,
            navController = navController,
            messageBarState = messageBarState,
        )    }
}


fun NavGraphBuilder.authenticationNavigation(
    authViewModel: AuthViewModel,
    navController: NavHostController,
    messageBarState: MessageBarState
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
                },
                messageBarState = messageBarState,
            )
        }
    }

}