package com.project.middleman.navigation

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.project.middleman.navigation.feature.FeatureContentLayout
import com.project.middleman.core.common.appstate.viewmodel.AppStateViewModel
import com.project.middleman.feature.authentication.viewmodel.AuthViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
) {


    val activity = LocalActivity.current as ComponentActivity
    val appStateViewModel: AppStateViewModel = hiltViewModel(activity)
    val authViewModel: AuthViewModel = hiltViewModel(activity)
    // Separate NavControllers for auth and feature flows
    val featureNavController = rememberNavController()


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
                FeatureContentLayout(
                    authViewModel = authViewModel,
                    navController = featureNavController,
                    currentRoute = AuthNavigationRoute.AccountSetupScreen.route,
                    appStateViewModel = appStateViewModel,
                    modifier = modifier
                )

        }
    }
}
