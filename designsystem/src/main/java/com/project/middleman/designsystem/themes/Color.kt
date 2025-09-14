package com.project.middleman.designsystem.themes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val white = Color(0xFFFFFFFF)
val colorAccent = Color(0xFF7446E4)


val deepColorAccent = Color(0xFF6334D8)
val lightColorAccent = Color(0xFFAD8CFF)
val lightVColorAccent = Color(0xFFE8DEF8)
val DeepGrey = Color(0xFF81868B)
val Grey = Color(0xFF9CA3AF)
val surface = Color(0xFFF6F6F6)
val borderGrey = Color(0xFFE7E7E7)
val lightGrey = Color(0xFFDADADA)
val colorBlack = Color(0xFF000000)
val cardDarkColor= Color(0xFF0E0E0E)
val verificationDarkColor= Color(0xFF212121)

val colorGreen = Color(0xFF34C759)
val surfaceBrandLight = Color(0xFFFBF9FF)
val surfaceBrandLighter = Color(0xFFE8DEF8)
val red = Color(0xFFFF3B30)

val cardMultiColor = white
val verificationColor = surface
val proceedArrowColor = colorBlack


//val cardMultiColor: Color
//    @Composable get() = if (isSystemInDarkTheme()) cardDarkColor else surfaceBrandLight
//
//val verificationColor: Color
//    @Composable get() = if (isSystemInDarkTheme()) verificationDarkColor else surface
//
//val proceedArrowColor: Color
//    @Composable get() = if (isSystemInDarkTheme()) white else colorBlack