package com.project.middleman.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import com.middleman.composables.tab.CustomTab
import com.middleman.composables.tab.Tab


@Composable
internal fun AnimatedBottomTab(
    selectedTab: Tab,
    onTabSelected: (Tab) -> Unit,
    currentRoute: String?,
    modifier: Modifier = Modifier
) {

    val animationSpec =
    AnimatedVisibility(
        visible = currentRoute == NavigationRoute.ChallengeListScreen.route,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        ) + fadeIn(
            animationSpec = tween(durationMillis = 600)
        ),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        ) + fadeOut(
            animationSpec = tween(durationMillis = 400)
        ),
        modifier = modifier
    ) {
        val shouldAnimateScale = currentRoute == NavigationRoute.ChallengeListScreen.route
        val scale by animateFloatAsState(
            targetValue = if (shouldAnimateScale) 1f else 0.8f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            ),
            label = "scale"
        )

        CustomTab(
            selectedTab = selectedTab,
            onTabSelected = onTabSelected,
            modifier = Modifier.scale(scale)
        )
    }
}
