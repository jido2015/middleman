package com.project.middleman.navigation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.project.middleman.core.source.data.model.Challenge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AppStateViewModel @Inject constructor(
) : ViewModel() {
    var challenge by mutableStateOf<Challenge?>(null)

    private val _showCreateWagerSheet = MutableStateFlow(false)
    val showCreateWagerSheet: StateFlow<Boolean> = _showCreateWagerSheet


    private val _showBottomTabSheet = MutableStateFlow(false)
    val showBottomTabSheet: StateFlow<Boolean> = _showBottomTabSheet


    fun setBottomBarVisibility(visible: Boolean) {
        _showBottomTabSheet.value = visible
    }

    fun setShowCreateWagerSheet(visible: Boolean) {
        _showCreateWagerSheet.value = visible
    }
}
