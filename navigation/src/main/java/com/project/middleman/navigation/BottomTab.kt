package com.project.middleman.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.middleman.composables.tab.CustomBottomTab
import com.middleman.composables.tab.Tab

@Composable
internal fun AnimatedBottomTab(
    showBottomBarSheet: Boolean,
    selectedTab: Tab,
    onTabSelected: (Tab) -> Unit,
    modifier: Modifier,
    onCreateButtonSelected: () -> Unit
) {

    Box(
        modifier = modifier
    ){
        AnimatedVisibility(
            visible = showBottomBarSheet,
            exit = fadeOut(animationSpec = tween(durationMillis = 500)),
            enter = fadeIn(animationSpec = tween(durationMillis = 300))
        ) {
            CustomBottomTab(
                selectedTab = selectedTab,
                onTabSelected = onTabSelected,
                onCreateButtonSelected = {onCreateButtonSelected()})
        }
    }

}
