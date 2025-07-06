package com.project.middleman.navigation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.project.middleman.core.source.domain.authentication.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import android.util.Log
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AppStateViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {

    private val _showTopBar = MutableStateFlow(true)
    val showTopBar: StateFlow<Boolean> = _showTopBar

    private val _isUserLoggedIn = MutableStateFlow(true)
    val isUserLogged: StateFlow<Boolean> = _showTopBar


    val isUserAuthenticated get() = repo.isUserAuthenticatedInFirebase

    fun setTopBarVisibility(visible: MutableState<Boolean>) {
        _showTopBar.value = visible.value
    }

    fun setUserLoggedIn(login: MutableState<Boolean>) {
        _isUserLoggedIn.value = login.value
    }
}
