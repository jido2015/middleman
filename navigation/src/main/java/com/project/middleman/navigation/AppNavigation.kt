package com.project.middleman.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.project.middleman.navigation.viewmodel.AppStateViewModel
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarState
import com.stevdzasan.messagebar.rememberMessageBarState


private fun getStartDestination(isFirstTime: Boolean): NavigationRoute {

    //Check if user already logged in. If yes, show Dashboard screen.
    // Else, show onboarding screen or Login screen.
    return if (isFirstTime) {
        NavigationRoute.AuthenticationScreen
    } else {
        NavigationRoute.CreateChallengeScreen
    }
}

// Navigation routes
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(modifier: Modifier = Modifier,
                  messageBarState: MessageBarState = rememberMessageBarState(),
                  appStateViewModel: AppStateViewModel = hiltViewModel()) {
    val showTopBar by appStateViewModel.showTopBar.collectAsState()
    val navController = rememberNavController()
    val currentComposable = navController.currentBackStackEntryAsState().value?.destination?.route

    val toolBarTitle = remember { mutableStateOf("") }
    val toolBarSubTitle = remember { mutableStateOf("") }

    val startDestinationName by remember {
        derivedStateOf {
            getStartDestination(isFirstTime = appStateViewModel.isFirstTime).name
        }
    }

    // Set the top bar title and subtitle based on the current composable
    ToolbarSetup(currentComposable, appStateViewModel, toolBarTitle, toolBarSubTitle)



    ContentWithMessageBar(messageBarState = messageBarState) {

    Scaffold(
        topBar = {
                if (showTopBar) {
                    TopAppBar(
                        title = { Text(text = "SafeMeet") },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                            }
                        }
                    )
                }
        },
    ) { innerPadding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                NavHost(navController = navController, startDestination = startDestinationName) {
                    featureNavigation(appStateViewModel, navController = navController, messageBarState = messageBarState)
                }
        }

    }
    }
}
