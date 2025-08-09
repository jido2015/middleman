package com.project.middleman.core.common


enum class BetStatus(val description: String) {
    OPEN("Bet is open for participation"),
    CLOSED("Bet is closed"),
    PENDING("Bet result is pending"),
    ACTIVE("Bet is active"),
    COMPLETED("Bet is completed")
}

enum class UserBetStatus {
    PENDING,
    WON,
    LOST
}