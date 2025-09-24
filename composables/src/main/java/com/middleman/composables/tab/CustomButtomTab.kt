package com.middleman.composables.tab

import com.middleman.composables.R
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.project.middleman.designsystem.themes.lightColorAccent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import com.project.middleman.designsystem.themes.colorAccent
import com.project.middleman.designsystem.themes.white

enum class Tab {
    Home, Explore, DEFAULT
}

@Composable
fun CustomBottomTab(
    modifier: Modifier = Modifier,
    selectedTab: Tab,
    onTabSelected: (Tab) -> Unit,
    onCreateButtonSelected: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(114.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,          // fully transparent at the top
                        Color.White.copy(alpha = 0.6f)  // semi-transparent white at bottom
                    )
                )
            )
    ){

         Card(
             shape = RoundedCornerShape(100.dp),
             elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
             colors = CardDefaults.cardColors(containerColor = Color.White),
             modifier = Modifier.align(Alignment.Center).width(192.dp).height(48.dp)
         ) {
             Row(
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(top = 2.dp),
                 horizontalArrangement = Arrangement.Center,
                 verticalAlignment = Alignment.CenterVertically
             ) {
                 Row(
                     horizontalArrangement = Arrangement.spacedBy(20.dp),
                     verticalAlignment = Alignment.CenterVertically
                 ) {
                     TabIcon(
                         tab = Tab.Home,
                         selected = selectedTab == Tab.Home || selectedTab == Tab.DEFAULT,
                         onClick = { onTabSelected(Tab.Home)
                         }
                     )

                     CircularTabButton(
                         onClick = {
                             onCreateButtonSelected()
                         }
                     )

                     TabIcon(
                         tab = Tab.Explore,
                         selected = selectedTab == Tab.Explore,
                         onClick = { onTabSelected(Tab.Explore)}
                     )
                 }
             }
         }
     }

}

@Composable
fun TabIcon(
    tab: Tab,
    selected: Boolean,
    onClick: () -> Unit
) {
    val iconRes = when (tab) {
        Tab.Home -> if (selected) R.drawable.home_filled else R.drawable.home
        Tab.Explore -> if (selected) R.drawable.explore_filled else R.drawable.explore
        else -> error("Unsupported tab")
    }

    Box(
        modifier = Modifier
            .size(35.dp)
            .clip(CircleShape)
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(
                    bounded = false,
                    radius = 24.dp,
                    color = Color.White
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = Color.Unspecified, // use the drawable's own fill/stroke
            modifier = Modifier.size(24.dp)
        )
    }
}


@Composable
fun CircularTabButton(
    onClick: () -> Unit,
    backgroundColor: Color = colorAccent,
    borderColor: Color = lightColorAccent,
    borderWidth: Dp = 2.dp,
    size: Dp = 43.dp
) {
    val infiniteTransition = rememberInfiniteTransition()

    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val glowColor = borderColor.copy(alpha = glowAlpha)

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .border(BorderStroke(borderWidth, glowColor), CircleShape)
            .background(backgroundColor)
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(
                    bounded = false,
                    radius = 100.dp,
                    color = white.copy(alpha = 0.9f)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(18.dp),
            painter = painterResource(R.drawable.add),
            contentDescription = null,
            tint = Color.White
        )
    }
}




@Preview(showBackground = true)
@Composable
fun CustomTabPreview(){
    CustomBottomTab(
        selectedTab = Tab.Home,
        onTabSelected = {},
        onCreateButtonSelected = {}
    )
}

