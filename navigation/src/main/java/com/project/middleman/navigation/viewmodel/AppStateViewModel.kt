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

    private val _showNotificationBarSheet = MutableStateFlow(false)
    val showNotificationBarSheet: StateFlow<Boolean> = _showNotificationBarSheet

    private val _showNavigationTopBarSheet = MutableStateFlow(false)
    val showNavigationTopBarSheet: StateFlow<Boolean> = _showNavigationTopBarSheet

    private val _navigationCurrentProgress = MutableStateFlow(0f)
    val navigationCurrentProgress: StateFlow<Float> = _navigationCurrentProgress

    private val _navigationTitle = MutableStateFlow("")
    val navigationTitle: StateFlow<String> = _navigationTitle


    fun setNotificationBarVisibility(visible: Boolean) {
        _showNotificationBarSheet.value = visible
    }

    fun setBottomBarVisibility(visible: Boolean) {
        _showBottomTabSheet.value = visible
    }

    fun setCreateWagerVisibility(visible: Boolean) {
        _showCreateWagerSheet.value = visible
    }

    fun setNavigationTopBarVisibility(visible: Boolean) {
        _showNavigationTopBarSheet.value = visible
    }

    fun setNavigationCurrentProgress(progress: Float) {
        _navigationCurrentProgress.value = progress
    }

    fun setNavigationTitle(title: String) {
        _navigationTitle.value = title
    }
}
