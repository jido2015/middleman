package com.project.middleman.feature.createchallenge.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.colorBlack

@Composable
fun LabelValueRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(end = 20.dp),
            text = label,
            style =  Typography.labelMedium.copy(fontSize = 16.sp, color = colorBlack,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = value,
            style = Typography.labelMedium.copy(
                fontSize = 16.sp,
                color = colorBlack
            ),
            textAlign = TextAlign.End, // Align text to the right
            modifier = Modifier.fillMaxWidth()
        )

    }
}
