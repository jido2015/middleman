package com.project.middleman.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.project.middleman.navigation.viewmodel.AppStateViewModel
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarState
import com.stevdzasan.messagebar.rememberMessageBarState


private fun getStartDestination(isAuthenticated: Boolean): NavigationRoute {

    //Check if user already logged in. If yes, show Dashboard screen.
    // Else, show onboarding screen or Login screen.
    return if (isAuthenticated) {
        NavigationRoute.ChallengeListScreen
    } else {
        NavigationRoute.AuthenticationScreen
    }
}

// Navigation routes
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(modifier: Modifier = Modifier,
                  messageBarState: MessageBarState = rememberMessageBarState(),
                  appStateViewModel: AppStateViewModel = hiltViewModel()) {
    val showTopBar by appStateViewModel.showTopBar.collectAsState()
    val isFirstTime by remember { mutableStateOf(appStateViewModel.isUserAuthenticated) }

    val toolBarVisibility = remember { mutableStateOf(true) }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentComposable = navBackStackEntry?.destination
    val currentRoute = currentComposable?.route

    val canPop = navController.previousBackStackEntry != null

    val showBackButton = canPop && currentRoute != NavigationRoute.ChallengeListScreen.name

    val toolBarTitle = remember { mutableStateOf("") }
    val toolBarSubTitle = remember { mutableStateOf("") }

    val startDestinationName by remember {
        derivedStateOf {
            getStartDestination(isAuthenticated = appStateViewModel.isUserAuthenticated).name
        }
    }

    // Set the top bar title and subtitle based on the current composable
    ToolbarSetup(currentComposable.toString(), appStateViewModel, toolBarTitle, toolBarSubTitle,
        toolBarVisibility = toolBarVisibility)



    Scaffold(
        topBar = {
                if (showTopBar) {

                        TopAppBar(
                            title = { Text(text = "SafeMeet") },
                            navigationIcon = {
                                if (showBackButton){
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                                    }
                                }else null
                            }
                        )
                    }

        },
        floatingActionButton = {
            if (currentRoute == NavigationRoute.ChallengeListScreen.name){
                FloatingActionButton(
                    onClick = {
                        navController.navigate(NavigationRoute.CreateChallengeScreen.name)
                    }
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Challenge")
                }
            }

        } ,
    ) { innerPadding ->

        ContentWithMessageBar(messageBarState = messageBarState, modifier = Modifier.padding(innerPadding)) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
                NavHost(navController = navController, startDestination = startDestinationName) {
                    featureNavigation(
                        appStateViewModel,
                        navController = navController,
                        messageBarState = messageBarState
                    )
                }
            }
        }
    }
}
