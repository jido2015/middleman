package com.project.middleman.feature.authentication.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.project.middleman.core.source.data.sealedclass.AuthState
import com.project.middleman.feature.authentication.viewmodel.AuthViewModel


@Composable
fun IsUserAuthenticated(
    viewModel: AuthViewModel = hiltViewModel(),
    authenticated: (Boolean) -> Unit,
    gotoProfileSetup: () -> Unit
) {
    val authState by viewModel.isUserAuthenticated.collectAsState()

    when (authState) {
        AuthState.Loading -> {
            // Show splash/loading
        }
        AuthState.Authenticated -> LaunchedEffect(Unit) { authenticated(true) }
        AuthState.Unauthenticated -> LaunchedEffect(Unit) { gotoProfileSetup() }
    }
}
