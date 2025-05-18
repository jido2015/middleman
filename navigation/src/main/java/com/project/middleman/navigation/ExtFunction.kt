package com.project.middleman.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import com.project.middleman.navigation.viewmodel.AppStateViewModel


@Composable
fun ToolbarSetup(
    currentComposable: String?,
    appStateViewModel: AppStateViewModel,
    toolBarTitle: MutableState<String>,
    toolBarSubTitle: MutableState<String>,
    toolBarVisibility: MutableState<Boolean>
) {

    LaunchedEffect(currentComposable) {
        when (currentComposable) {
            NavigationRoute.AuthenticationScreen.name -> {
                appStateViewModel.setTopBarVisibility(toolBarVisibility)
                toolBarTitle.value = "Welcome Back,"
                toolBarSubTitle.value = "Please log in to your account"
            }
        }
    }
}