package com.middleman.composables.tab

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun CustomTabSwitch(
    tabs: List<String>,
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFFF5F5F5),
    indicatorColor: Color = Color.White,
    selectedTextColor: Color = Color.Black,
    unselectedTextColor: Color = Color.Gray,
    height: Dp = 40.dp,
    cornerRadius: Dp = 50.dp,
    shadow: Dp = 2.dp
) {
    val density = LocalDensity.current
    var tabWidths by remember { mutableStateOf<Map<String, Int>>(emptyMap()) }
    var indicatorOffsetPx by remember { mutableIntStateOf(0) }
    var indicatorWidthPx by remember { mutableIntStateOf(0) }

    val animatedOffset by animateDpAsState(
        targetValue = with(density) { indicatorOffsetPx.toDp() },
        label = "offset"
    )
    val animatedWidth by animateDpAsState(
        targetValue = with(density) { indicatorWidthPx.toDp() },
        label = "width"
    )

    SubcomposeLayout(modifier = modifier.height(height)) { constraints ->
        val placeables = tabs.associate { tab ->
            val placeable = subcompose(tab) {

                Text(
                    text = tab,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }[0].measure(constraints)

            tab to placeable
        }

        val widths = placeables.mapValues { it.value.width }
        tabWidths = widths

        indicatorOffsetPx = tabs
            .takeWhile { it != selectedTab }
            .sumOf { widths[it] ?: 0 }

        indicatorWidthPx = widths[selectedTab] ?: 0

        val totalWidth = widths.values.sum()
        val layoutHeight = placeables.values.maxOfOrNull { it.height } ?: constraints.minHeight

        layout(totalWidth, layoutHeight) {
            // Draw indicator
            val indicator = subcompose("indicator") {
                Box(
                    modifier = Modifier
                        .offset(x = animatedOffset)
                        .width(animatedWidth)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(cornerRadius))
                        .background(indicatorColor)
                        .shadow(shadow, RoundedCornerShape(cornerRadius))
                )
            }[0].measure(constraints)
            indicator.place(0, 0)

            // Draw tabs
            var xPos = 0
            tabs.forEach { tab ->
                val width = widths[tab] ?: 0
                val tabItem = subcompose("tab_$tab") {
                    Box(
                        modifier = Modifier
                            .width(with(density) { width.toDp() })
                            .fillMaxHeight()
                            .clickable { onTabSelected(tab) },
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            imageVector =
                                when (tab) {
                                    "Popular" -> {
                                        Icons.Default.Home
                                    }
                                    "New" -> {
                                        Icons.Default.Add
                                    }
                                    "Recommended" ->{
                                        Icons.Default.AcUnit
                                    }
                                    else -> {
                                        Icons.Default.Home
                                    }
                                },
                            contentDescription = null,
                            tint = if (tab == selectedTab) selectedTextColor else unselectedTextColor
                        )


                    }
                }[0].measure(constraints)

                tabItem.place(xPos, 0)
                xPos += width
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomTabSwitchPreview() {
    var selected by remember { mutableStateOf("All") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTabSwitch(
            tabs = listOf("Popular", "New", "Recommended"),
            selectedTab = selected,
            onTabSelected = { selected = it }
        )
    }
}

