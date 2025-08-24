package com.project.middleman.core.common


enum class BetStatus(val description: String) {
    OPEN("Challenge is open for participation"),
    PENDING("Challenge result is pending"),
    ACTIVE("Challenge is active"),
    COMPLETED("Challenge is completed"),

    PARTICIPANT_WINS ("Challenge is claimed"),
    CREATOR_WINS ("Challenge is claimed"),
    REVIEW ("Challenge is being reviewed"),
}

enum class UserBetStatus {
    PENDING,
    WON,
    LOST
}