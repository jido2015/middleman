package com.middleman.composables.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.colorAccent
import com.project.middleman.designsystem.themes.lightColorAccent
import com.project.middleman.designsystem.themes.white

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    containerColor : Color = colorAccent,
    borderColor : Color = lightColorAccent,
    textColor : Color = white
) {
    Button(
        modifier = modifier
            .height(50.dp),
        colors = buttonColors(containerColor = containerColor),
        onClick = { onClick()},
        shape = RoundedCornerShape(100),
        border = BorderStroke(1.dp, borderColor)
    ) {
        Text(
            text,
            style = Typography.titleLarge.copy(fontSize = 13.sp, color = textColor)
        )
    }

}