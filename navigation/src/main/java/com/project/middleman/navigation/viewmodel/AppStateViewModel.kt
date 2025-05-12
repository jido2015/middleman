package com.project.middleman.navigation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AppStateViewModel @Inject constructor() : ViewModel() {
    private val _showTopBar = MutableStateFlow(true)
    val showTopBar: StateFlow<Boolean> = _showTopBar

    // Needs to setup room database
    var isFirstTime: Boolean = true

    fun setTopBarVisibility(visible: Boolean) {
        _showTopBar.value = visible
    }
}
