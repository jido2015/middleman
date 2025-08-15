package com.project.middleman.navigation.auth

import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.middleman.composables.topbar.NavigationTopBarWithProgressBar
import com.project.middleman.designsystem.themes.white
import com.project.middleman.feature.authentication.presentation.PhoneNumberScreen
import com.project.middleman.feature.authentication.presentation.AuthenticationScreen
import com.project.middleman.feature.authentication.presentation.DateOfBirthScreen
import com.project.middleman.feature.authentication.presentation.DisplayNameScreen
import com.project.middleman.feature.authentication.presentation.FullNameScreen
import com.project.middleman.feature.authentication.presentation.PhoneVerificationScreen
import com.project.middleman.feature.authentication.viewmodel.CreateProfileViewModel
import com.project.middleman.navigation.AuthNavigationRoute
import com.project.middleman.core.common.appstate.viewmodel.AppStateViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AuthNavigationHost(
    startDestinationName: String,
    navController: NavHostController,
) {

    val activity = LocalActivity.current as ComponentActivity
    val appStateViewModel: AppStateViewModel = hiltViewModel(activity)

    val showNavigationTopBarSheet by appStateViewModel.showNavigationTopBarSheet.collectAsState()
    val navigationCurrentProgress by appStateViewModel.navigationCurrentProgress.collectAsState()
    val navigationTitle by appStateViewModel.navigationTitle.collectAsState()
    val createProfileViewModel: CreateProfileViewModel = hiltViewModel()

    Column(modifier = Modifier.fillMaxSize().background(white).padding(top = 50.dp)) {
        // ✅ Navigation Top Bar With Progress Bar
        NavigationTopBarWithProgressBar(
            handleBackPressed = { navController.popBackStack() },
            title = "Personalise your account",
            progress = navigationCurrentProgress/5f,
            showNavigationTopBarSheet = showNavigationTopBarSheet,
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 16.dp))

        Log.d("Navigation", "About to create NavHost")
        NavHost(
            navController = navController,
            startDestination = startDestinationName,
            modifier = Modifier.fillMaxSize()
        ) {
            Log.d("Navigation", "NavHost created, about to call authenticationNavigation")

            authenticationNavigation(
                createProfileViewModel = createProfileViewModel,
                appStateViewModel = appStateViewModel,
                navController = navController
            )
        }
    }
    
    Log.d("Navigation", "=== AuthNavigationHost END ===")
}
@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.authenticationNavigation(
    createProfileViewModel: CreateProfileViewModel,
    appStateViewModel: AppStateViewModel,
    navController: NavHostController
) {
    Log.d("Navigation", "=== authenticationNavigation START ===")
    
    composable(route = AuthNavigationRoute.AccountSetupScreen.route) {
        // Ensure visibility is set before the screen is shown
        appStateViewModel.setNavigationTopBarVisibility(false)

        AuthenticationScreen(
            appStateViewModel = appStateViewModel,
            gotoProfileSetup = {
                Log.d("Navigation", "gotoProfileSetup called")
                navController.navigate(AuthNavigationRoute.DateOfBirthScreen.route)
            })
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
            appStateViewModel = appStateViewModel,
            viewModel = createProfileViewModel,
            onSaveChallenge = {
            }
        )
    }

    Log.d("Navigation", "=== authenticationNavigation END ===")
}