package com.middleman.feature.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.cardMultiColor
import com.project.middleman.designsystem.themes.colorBlack
import com.project.middleman.designsystem.themes.surfaceBrandLight

@Composable
fun SpendableCardUI() {

    Box(modifier = Modifier.fillMaxWidth().background(cardMultiColor)

        .padding(horizontal = 20.dp, vertical = 18.dp)){
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text("Spendable", style = Typography.bodySmall,
                modifier = Modifier.padding(bottom = 4.dp))

            Text("$0.00", style = MaterialTheme.
            typography.labelLarge.copy(fontSize = 32.sp))
        }
    }

}

@Composable
@Preview(showBackground = true)
fun PreviewSpendableCard() {
    MaterialTheme {
        SpendableCardUI()
    }

}