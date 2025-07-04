package com.project.middleman.designsystem.themes

import com.project.middleman.designsystem.R
import androidx.compose.material3.Typography
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

//Set font family
val jakartaFont = FontFamily(

    Font(R.font.jakarta_bold, FontWeight.Bold),
    Font(R.font.jakarta_light, FontWeight.Light),
    Font(R.font.jakarta_medium, FontWeight.Medium),
    Font(R.font.jakarta_regular, FontWeight.Normal),
)

// Set of Material typography styles to start with
val Typography = Typography(
    labelMedium = TextStyle(
        fontFamily = jakartaFont,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
    ),
    titleLarge = TextStyle(
        fontFamily =  jakartaFont,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
    ),

    labelSmall = TextStyle(
        fontFamily = jakartaFont,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
    ),

    labelLarge = TextStyle(
        fontFamily = jakartaFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = jakartaFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = jakartaFont,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
    ),
    displayLarge = TextStyle(
        fontFamily = jakartaFont,
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
    )
)
