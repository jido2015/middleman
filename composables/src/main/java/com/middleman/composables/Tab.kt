package com.middleman.composables

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*

@Composable
fun DynamicTabSwitch(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = listOf("All", "Active", "Past")
    val density = LocalDensity.current

    var tabWidths by remember { mutableStateOf<Map<String, Int>>(emptyMap()) }
    var indicatorOffsetPx by remember { mutableStateOf(0) }
    var indicatorWidthPx by remember { mutableStateOf(0) }

    val animatedOffset by animateDpAsState(
        targetValue = with(density) { indicatorOffsetPx.toDp() },
        label = "offset"
    )
    val animatedWidth by animateDpAsState(
        targetValue = with(density) { indicatorWidthPx.toDp() },
        label = "width"
    )

    SubcomposeLayout(modifier = modifier.height(40.dp)) { constraints ->
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

        // Compute new offset and width in px
        indicatorOffsetPx = tabs
            .takeWhile { it != selectedTab }
            .sumOf { widths[it] ?: 0 }

        indicatorWidthPx = widths[selectedTab] ?: 0

        val totalWidth = widths.values.sum()
        val layoutHeight = placeables.values.maxOfOrNull { it.height } ?: constraints.minHeight

        layout(totalWidth, layoutHeight) {
            // Draw indicator
            val indicatorPlaceable = subcompose("indicator") {
                Box(
                    modifier = Modifier
                        .offset(x = animatedOffset)
                        .width(animatedWidth)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(50))
                        .background(Color.White)
                        .shadow(2.dp, RoundedCornerShape(50))
                )
            }[0].measure(constraints)

            indicatorPlaceable.place(0, 0)

            // Draw tab texts
            var xPos = 0
            tabs.forEach { tab ->
                val width = widths[tab] ?: 0
                val placeable = subcompose("tab_$tab") {
                    Box(
                        modifier = Modifier
                            .width(with(density) { width.toDp() })
                            .fillMaxHeight()
                            .clickable { onTabSelected(tab) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = tab,
                            fontWeight = FontWeight.Medium,
                            color = if (tab == selectedTab) Color.Black else Color.Gray
                        )
                    }
                }[0].measure(constraints)

                placeable.place(xPos, 0)
                xPos += width
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DynamicTabSwitchPreview() {
    var selectedTab by remember { mutableStateOf("All") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DynamicTabSwitch(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
