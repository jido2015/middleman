package com.project.middleman.feature.createchallenge.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.colorAccent
import com.project.middleman.designsystem.themes.colorBlack

@Composable
fun LabeledCheckbox(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.padding(bottom = 28.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            modifier = Modifier.scale(1.5f), // 👈 1.0 = normal, 1.5 = 150% size
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = colorAccent,        // Color when checked
                uncheckedColor = colorAccent,       // Color when unchecked
                checkmarkColor = Color.White       // The checkmark inside
            )
        )
        Text(
            text = label,
            modifier = Modifier
                .clickable { onCheckedChange(!checked) }
                .padding(start = 8.dp), // spacing between checkbox and text
                    style =  Typography.labelSmall.copy(
                    fontSize = 14.sp, color = colorBlack)
        )
    }
}
