package com.project.middleman.designsystem.themes

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.Window
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = colorAccent,
    onPrimary = white,

    secondary = lightColorAccent,
    onSecondary = colorBlack,   // Teal is bright, so black text works here

    tertiary = deepColorAccent,
    onTertiary = white,         // Deep orange is dark, white text for contrast

    background = colorBlack,
    onBackground = white,

    surface = colorBlack,
    onSurface = white,
)

private val LightColorScheme = lightColorScheme(
    primary = colorAccent,
    onPrimary = white,

    secondary = lightColorAccent,
    onSecondary = colorBlack,

    tertiary = deepColorAccent,
    onTertiary = colorBlack,

    background = white,
    onBackground = colorBlack,

    surface = white,
    onSurface = colorBlack,
)

@Composable
fun MiddlemanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    //val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val colorScheme = LightColorScheme


    val view = LocalView.current
    SideEffect {
        if (!view.isInEditMode) {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}


@Composable
fun SetStatusBarStyle(
    backgroundColor: Color,
    useDarkIcons: Boolean
) {
    val view = LocalView.current

    SideEffect {
        if (!view.isInEditMode) {
            val window = (view.context as Activity).window
            window.statusBarColor = backgroundColor.toArgb()
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = useDarkIcons
        }
    }
}

