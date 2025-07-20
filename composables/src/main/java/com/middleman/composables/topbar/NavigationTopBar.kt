package com.middleman.composables.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.middleman.composables.progressbar.ProgressBarUI
import com.project.middleman.designsystem.themes.colorBlack
import com.project.middleman.designsystem.themes.surface
import com.middleman.composables.R
import com.project.middleman.designsystem.themes.Typography

@Composable
fun NavigationTopBarWithProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    showNavigationTopBarSheet: Boolean = false,
    title: String = "Create Wager"
) {

    if (showNavigationTopBarSheet) {

    Column(
        modifier = modifier.padding(bottom = 20.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {

            IconButton(
                onClick = {},
                modifier = Modifier
                    .background(color = surface, shape = RoundedCornerShape(15.dp))
                    .size(36.dp) // Size of the entire circle
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_left),
                    contentDescription = "Close",
                    tint = colorBlack,
                    modifier = Modifier.padding(10.dp) // Padding to center the icon nicely
                )
            }

            Text(
                title,
                style = Typography.bodyMedium.copy(fontSize = 16.sp, color = colorBlack),
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
            )

            IconButton(
                onClick = {},
                modifier = Modifier
                    .background(color = surface, shape = RoundedCornerShape(15.dp))
                    .size(36.dp) // Size of the entire circle
            ) {
                Icon(
                    painter = painterResource(R.drawable.help),
                    contentDescription = "Help",
                    tint = colorBlack,
                    modifier = Modifier.padding(10.dp) // Padding to center the icon nicely
                )
            }
        }

        ProgressBarUI(
            progress,
            modifier = Modifier
        )

    }
}
}

@Preview(showBackground = true)
@Composable
fun NavigationTopBarScreenPreview() {
    MaterialTheme {
        NavigationTopBarWithProgressBar(2f)
    }
}
