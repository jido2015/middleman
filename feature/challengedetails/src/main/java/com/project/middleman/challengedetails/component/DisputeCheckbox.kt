package com.project.middleman.challengedetails.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.middleman.designsystem.themes.Typography

@Composable
fun DisputeCheckbox(
    visibilityState: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable { visibilityState.value = !visibilityState.value }
    ) {
        Checkbox(
            modifier = Modifier.size(24.dp),
            checked = visibilityState.value,
            onCheckedChange = { visibilityState.value = it }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Don’t show next time",
            style = Typography.labelSmall.copy(fontSize = 12.sp)
        )
    }
}
