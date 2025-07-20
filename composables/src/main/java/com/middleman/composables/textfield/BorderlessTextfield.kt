package com.middleman.composables.textfield

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.sp
import com.project.middleman.designsystem.themes.Grey
import com.project.middleman.designsystem.themes.Typography
import com.project.middleman.designsystem.themes.colorAccent

@Composable
fun BorderlessTextField(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onImeAction: () -> Unit = {}
) {
    val customTextSelectionColors = TextSelectionColors(
        handleColor = Color.Green,
        backgroundColor = Grey
    )

    val inputTextStyle = Typography.bodySmall.copy(
        fontSize = 20.sp,
        color = Color.Black,
        lineHeight = 24.sp
    )

    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        BasicTextField(
            modifier = modifier
                .testTag("borderlessTextField"),
            value = text,
            onValueChange = onValueChange,
            textStyle = inputTextStyle,
            cursorBrush = SolidColor(colorAccent),
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    if (text.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = inputTextStyle.copy(color = Color.Gray)
                        )
                    }
                    innerTextField()
                }
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onAny = { onImeAction() }
            )
        )
    }
}