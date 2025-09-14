package com.middleman.composables.button

import android.graphics.drawable.Drawable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.middleman.composables.R

@Composable
fun ProceedButton(
    imageVector: Painter,
    proceedClicked: () -> Unit, size: Dp,
    modifier: Modifier, text: String,
    color: Color = Color.Unspecified, paddingValues: PaddingValues,
    imageModifier: Modifier = Modifier
){

    Row(modifier = modifier) {
        Icon(
            painter = imageVector,
            contentDescription = text,
            tint = color,
            modifier = imageModifier.clip(CircleShape).padding(paddingValues).clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null // Let Material 3 apply default ripple
            ) {
                proceedClicked()
            }.size(size)

        )
    }
}
