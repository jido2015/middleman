package com.middleman.feature.dashboard.components

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource

@Composable
fun handleScrollDirection(
    scrollState: ScrollState,
    onScrollDown: () -> Unit,
    onScrollUp: () -> Unit
): NestedScrollConnection {
    var lastScrollOffset by remember { mutableIntStateOf(0) }
    var lastDirection by remember { mutableStateOf<ScrollDirection?>(null) }


    LaunchedEffect(scrollState.value) {
        val currentOffset = scrollState.value
        val newDirection = when {
            currentOffset > lastScrollOffset -> ScrollDirection.DOWN
            currentOffset < lastScrollOffset -> ScrollDirection.UP
            else -> null
        }

        if (newDirection != null && newDirection != lastDirection) {
            when (newDirection) {
                ScrollDirection.DOWN -> onScrollDown()
                ScrollDirection.UP -> onScrollUp()
            }
            lastDirection = newDirection
        }

        lastScrollOffset = currentOffset
    }

    return remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // Optionally handle gesture immediately, e.g. for quick close
                return Offset.Zero
            }
        }
    }
}
