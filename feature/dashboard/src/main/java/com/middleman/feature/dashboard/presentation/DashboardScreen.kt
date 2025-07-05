package com.middleman.feature.dashboard.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.middleman.feature.dashboard.components.SpendableCardUI
import com.middleman.feature.dashboard.components.VerificationProgress
import com.project.middleman.designsystem.themes.surfaceBrandLight
import com.project.middleman.designsystem.themes.white

@Composable
fun DashboardScreen(
    onProceedClicked: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(surfaceBrandLight)
    ) {

        val (topCompose, bottomCompose) = createRefs()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
                .constrainAs(topCompose) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(bottomCompose.top)
                }) {
            SpendableCardUI()
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(white)
                .padding(start = 20.dp, end = 20.dp)
                .constrainAs(bottomCompose) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    top.linkTo(topCompose.bottom)
                    height = Dimension.fillToConstraints // ✅ Tell it to fill between top and bottom

                }) {
            Spacer(modifier = Modifier.height(24.dp))
            VerificationProgress(onProceedClicked = onProceedClicked)
        }

    }
}


@Composable
@Preview(showBackground = true)
fun PreviewDashboardScreen() {
    MaterialTheme {
        DashboardScreen(onProceedClicked = {})
    }

}