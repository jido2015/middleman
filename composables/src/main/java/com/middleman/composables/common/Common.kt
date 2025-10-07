package com.middleman.composables.common

import androidx.compose.ui.graphics.Color


fun getChallengeColor(status: String): Color {
    return when (status) {
        "PARTICIPANT_WINS" -> Color(0xFF4CAF50)
        "CREATOR_WINS" -> Color(0xFF4CAF50)
        "ACTIVE" -> Color(0xFF4CAF50)     // Green
        "PENDING" -> Color(0xFFFFC107)  // Amber
        "OPEN" -> Color(0xFF2196F3)   // Blue
        "CLOSED" -> Color(0xFF9E9E9E)   // Grey
        "CREATOR_DISPUTE" -> Color(0xFFFFA500) // Orange
        "PARTICIPANT_DISPUTE" -> Color(0xFFFFA500) // Orange
        else -> Color(0xFF000000)       // Default: Black
    }
}

