package com.middleman.feature.dashboard.presentation

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.middleman.feature.dashboard.R
import com.middleman.feature.dashboard.components.SpendableCardUI
import com.middleman.feature.dashboard.components.VerificationProgress
import com.middleman.feature.dashboard.components.handleScrollDirection
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.colorAccent
import com.project.middleman.designsystem.themes.colorBlack
import com.project.middleman.designsystem.themes.lightColorAccent
import com.project.middleman.designsystem.themes.surfaceBrandLight
import com.project.middleman.designsystem.themes.white


@Composable
fun DashboardScreen(
    onScrollDown: () -> Unit,
    onScrollUp: () -> Unit,
    onProceedClicked: () -> Unit
) {
    val scrollState = rememberScrollState()

    val scrollHandler = handleScrollDirection(
        scrollState = scrollState,
        onScrollDown = onScrollDown,
        onScrollUp = onScrollUp
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollHandler)
            .verticalScroll(scrollState)
            .background(white)
    ) {
        SpendableCardUI()
        Spacer(modifier = Modifier.height(24.dp))
        VerificationProgress(onProceedClicked = onProceedClicked)

        Text(
            "WAGERS",
            style = Typography.titleLarge.copy(fontSize = 16.sp, color = colorBlack),
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
        )

        Box(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(surfaceBrandLight)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
                    .background(surfaceBrandLight)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image_ads),
                    contentDescription = "My image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    contentScale = ContentScale.Crop
                )

                Text(
                    "Start wagering with your peers",
                    style = Typography.titleLarge.copy(fontSize = 16.sp, color = colorBlack),
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Text(
                    "Setup a bet with a friend and you winning asap.",
                    style = Typography.labelMedium.copy(fontSize = 16.sp),
                    color = colorBlack
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = buttonColors(containerColor = colorAccent),
                    onClick = { },
                    shape = RoundedCornerShape(100),
                    border = BorderStroke(1.dp, lightColorAccent)
                ) {
                    Text(
                        "Get Started",
                        style = Typography.titleLarge.copy(fontSize = 14.sp, color = white)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewDashboardScreen() {
    MaterialTheme {
        DashboardScreen(onProceedClicked = {}, onScrollDown = {}, onScrollUp = {})
    }

}