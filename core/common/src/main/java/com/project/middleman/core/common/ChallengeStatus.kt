package com.project.middleman.core.common


enum class ChallengeStatus(val description: String) {
    OPEN("Challenge is open for participation"),
    PENDING("Challenge participant is pending"),
    ACTIVE("Challenge is active"),
    CLOSED("Challenge is completed"),

    PARTICIPANT_WINS ("Challenge is claimed"),
    PARTICIPANT_DISPUTE ("Challenge is disputed"),
    CREATOR_DISPUTE ("Challenge is disputed"),

    CREATOR_WINS ("Challenge is claimed"),

    REVIEW ("Challenge is being reviewed"),
}

fun getChallengeStatusName(status: String): String {
    return when (status) {
        "PARTICIPANT_WINS" -> "ACTIVE"
        "CREATOR_WINS" -> "ACTIVE"
        "PARTICIPANT_DISPUTE" -> "DISPUTE"     // Green
        "CREATOR_DISPUTE" -> "DISPUTE"  // Amber

        else -> status       // Default: Black
    }
}