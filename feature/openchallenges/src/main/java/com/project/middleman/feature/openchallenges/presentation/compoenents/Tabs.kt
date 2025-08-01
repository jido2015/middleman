package com.project.middleman.feature.openchallenges.presentation.compoenents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.middleman.composables.topbar.MainToolBar
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.feature.openchallenges.R
import kotlinx.coroutines.launch
import androidx.compose.animation.core.tween

@Composable
fun OpenChallengeTabPager(pages: List<@Composable () -> Unit>, modifier: Modifier = Modifier) {

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()

    val selectedColor = Color.Black
    val unselectedColor = Color.LightGray

    Column(modifier = modifier.fillMaxSize()) {
        MainToolBar(
            showTopBar = true
        )
        Column(modifier = modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "All",
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(
                                    0,
                                    animationSpec = tween(durationMillis = 500)
                                )
                            }
                        },
                    style = Typography.bodyLarge.copy(
                        fontSize = 16.sp,
                        color = if (pagerState.currentPage == 0) selectedColor else unselectedColor
                    )
                )
                Text(
                    text = "Active",
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(
                                    1,
                                    animationSpec = tween(durationMillis = 500)
                                )
                            }
                        },
                    style = Typography.bodyLarge.copy(
                        fontSize = 16.sp,
                        color = if (pagerState.currentPage == 1) selectedColor else unselectedColor
                    )
                )
                Text(
                    text = "Completed",
                    modifier = Modifier
                        .clickable {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(
                                    2,
                                    animationSpec = tween(durationMillis = 500)
                                )
                            }
                        },
                    style = Typography.bodyLarge.copy(
                        fontSize = 16.sp,
                        color = if (pagerState.currentPage == 2) selectedColor else unselectedColor
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(bottom = 18.dp, top = 12.dp),
                    painter = painterResource(id = R.drawable.filter),
                    contentDescription = "Icon",
                    contentScale = ContentScale.Crop
                )
            }

            HorizontalPager(
                state = pagerState
            ) { page ->
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp) // Padding inside each page
                        .wrapContentSize(Alignment.Center)
                ) {
                    pages[page]()  // Show each Composable here
                }
            }
        }
    }
}
