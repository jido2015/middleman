package com.project.middleman.navigation.auth

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.project.middleman.feature.authentication.presentation.PhoneNumberScreen
import com.project.middleman.feature.authentication.presentation.AuthenticationScreen
import com.project.middleman.feature.authentication.presentation.DateOfBirthScreen
import com.project.middleman.feature.authentication.presentation.DisplayNameScreen
import com.project.middleman.feature.authentication.presentation.FullNameScreen
import com.project.middleman.feature.authentication.presentation.PhoneVerificationScreen
import com.project.middleman.feature.authentication.viewmodel.CreateProfileViewModel
import com.project.middleman.navigation.AuthNavigationRoute
import com.project.middleman.core.common.appstate.viewmodel.AppStateViewModel
import com.project.middleman.feature.authentication.viewmodel.AuthViewModel
import com.project.middleman.navigation.NavigationRoute


@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.authenticationNavigation(
    authViewModel: AuthViewModel,
    createProfileViewModel: CreateProfileViewModel,
    appStateViewModel: AppStateViewModel,
    navController: NavHostController
) {

    composable(route = AuthNavigationRoute.AccountSetupScreen.route) {
        // Ensure visibility is set before the screen is shown
        appStateViewModel.setNavigationTopBarVisibility(false)

        AuthenticationScreen(
            gotoProfileSetup = {
                Log.d("Navigation", "gotoProfileSetup called")
                navController.navigate(AuthNavigationRoute.DateOfBirthScreen.route)
            },
            proceedToDashBoard = {
                navController.navigate(NavigationRoute.DashboardScreen.route)
            }
        )
    }

    composable(route = AuthNavigationRoute.DateOfBirthScreen.route) {
        appStateViewModel.setNavigationTopBarVisibility(true)
        appStateViewModel.setNavigationCurrentProgress(1f)
        // Ensure visibility is set before the screen is shown
        DateOfBirthScreen(
            viewModel = createProfileViewModel,
            onSaveChallenge = {
                navController.navigate(AuthNavigationRoute.PhoneLineScreen.route)
            }
        )
    }


    composable(route = AuthNavigationRoute.PhoneLineScreen.route) {
        appStateViewModel.setNavigationTopBarVisibility(true)
        appStateViewModel.setNavigationCurrentProgress(2f)
        // Ensure visibility is set before the screen is shown
        PhoneNumberScreen(
            viewModel = createProfileViewModel,
            onSaveChallenge = {
                navController.navigate(AuthNavigationRoute.NumberVerificationScreen.route)
            }
        )
    }

    composable(route = AuthNavigationRoute.NumberVerificationScreen.route) {
        appStateViewModel.setNavigationTopBarVisibility(true)
        appStateViewModel.setNavigationCurrentProgress(3f)
        // Ensure visibility is set before the screen is shown
        PhoneVerificationScreen(
            viewModel = createProfileViewModel,
            onSaveChallenge = {
                navController.navigate(AuthNavigationRoute.FullNameScreen.route)
            }
        )
    }

    composable(route = AuthNavigationRoute.FullNameScreen.route) {
        appStateViewModel.setNavigationTopBarVisibility(true)
        appStateViewModel.setNavigationCurrentProgress(4f)
        // Ensure visibility is set before the screen is shown
        FullNameScreen(
            viewModel = createProfileViewModel,
            onSaveChallenge = {
                navController.navigate(AuthNavigationRoute.DisplayNameScreen.route)
            }
        )
    }

    composable(route = AuthNavigationRoute.DisplayNameScreen.route) {
        appStateViewModel.setNavigationTopBarVisibility(true)
        appStateViewModel.setNavigationCurrentProgress(5f)
        // Ensure visibility is set before the screen is shown
        DisplayNameScreen(
            viewModel = createProfileViewModel,
            onSaveChallenge = {
                navController.navigate(NavigationRoute.DashboardScreen.route)
            }
        )
    }

    Log.d("Navigation", "=== authenticationNavigation END ===")
}