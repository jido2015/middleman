package com.project.middleman.designsystem.themes

import com.project.middleman.designsystem.R
import androidx.compose.material3.Typography
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

//Set font family
val gothamFont = FontFamily(

    Font(R.font.gotham_rnd_bold, FontWeight.Bold),
    Font(R.font.gotham_rnd_light, FontWeight.Light),
    Font(R.font.gotham_rnd_medium, FontWeight.Medium)
)

// Set of Material typography styles to start with
val Typography = Typography(
    labelMedium = TextStyle(
        fontFamily = gothamFont,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
    ),
    titleLarge = TextStyle(
        fontFamily =  gothamFont,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
    ),

    labelSmall = TextStyle(
        fontFamily = gothamFont,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
    ),

    labelLarge = TextStyle(
        fontFamily = gothamFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = gothamFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = gothamFont,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
    ),
    displayLarge = TextStyle(
        fontFamily = gothamFont,
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
    )
)
