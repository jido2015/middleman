package com.project.middleman.core.common


enum class BetStatus(val description: String) {
    OPEN("Challenge is open for participation"),
    PENDING("Challenge participant is pending"),
    ACTIVE("Challenge is active"),
    CLOSED("Challenge is completed"),

    PARTICIPANT_WINS ("Challenge is claimed"),
    CREATOR_WINS ("Challenge is claimed"),
    REVIEW ("Challenge is being reviewed"),
}

fun getChallengeStatusName(status: String): String {
    return when (status) {
        "PARTICIPANT_WINS" -> "ACTIVE"
        "CREATOR_WINS" -> "ACTIVE"
        else -> status       // Default: Black
    }
}


enum class UserBetStatus {
    PENDING,
    WON,
    LOST
}