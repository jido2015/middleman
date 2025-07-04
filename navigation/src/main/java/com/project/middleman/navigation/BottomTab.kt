package com.project.middleman.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.middleman.composables.tab.CustomNavigationTab
import com.middleman.composables.tab.Tab

@Composable
internal fun AnimatedBottomTab(
    selectedTab: Tab,
    onTabSelected: (Tab) -> Unit,
    modifier: Modifier = Modifier
) {
        CustomNavigationTab(
            selectedTab = selectedTab,
            onTabSelected = onTabSelected,
            modifier = modifier
        )

}
