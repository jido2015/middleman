package com.project.middleman.feature.createchallenge.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
fun VisibilityCheckbox(
    visibilityState: MutableState<Boolean>,
    modifier: Modifier
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Checkbox(
            checked = visibilityState.value,
            onCheckedChange = { visibilityState.value = it }
        )
        Spacer(modifier = Modifier.width(8.dp))
        val labelText = if (visibilityState.value) {
            "Public – This bet will be visible to everyone in the app's open bets list."
        } else {
            "Invite-Only – Only people with a direct invite can view or join this bet."
        }
        Text(text = labelText, style = Typography.labelSmall.copy(fontSize = 14.sp))
    }

}
