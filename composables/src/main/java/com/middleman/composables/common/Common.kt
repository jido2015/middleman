package com.middleman.composables.common

import androidx.compose.ui.graphics.Color


fun getChallengeColor(status: String): Color {
    return when (status) {
        "PARTICIPANT_WINS" -> Color(0xFF2196F3)
        "CREATOR_WINS" -> Color(0xFF2196F3)
        "OPEN" -> Color(0xFF4CAF50)     // Green
        "PENDING" -> Color(0xFFFFC107)  // Amber
        "ACTIVE" -> Color(0xFF2196F3)   // Blue
        "CLOSED" -> Color(0xFF9E9E9E)   // Grey
        else -> Color(0xFF000000)       // Default: Black
    }
}

