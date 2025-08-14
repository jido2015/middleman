package com.project.middleman.navigation

enum class MarketplaceSubtitle(val text: String) {
    BROWSE("Browse live challenges and pick your next match."),
    JOIN("Join games, place wagers, and compete to win."),
    REAL_TIME("Real-time challenges across your favorite games."),
    CHOOSE("Choose a game. Accept a wager. Show your skills."),
    EXPLORE("Explore head-to-head matchups and make your move."),
    ARENA("The arena is live. Find your challenge now."),
    WAGERS_LIVE("Wagers are live — who will you take on?");

    companion object {
        fun random(): String {
            return entries.toTypedArray().random().text
        }
    }
}

enum class ChallengeOverviewSubtitle(val text: String) {
    REVIEW("Review the details and join the challenge."),
    SEE("See who you're up against and what's at stake."),
    INFO("Game rules, opponent info, and wager amount."),
    NEED_TO_KNOW("Everything you need to know before playing."),
    READY("Ready up—this is your chance to win."),
    CONFIRM("Confirm the matchup and accept the wager.");

    companion object {
        fun random(): String {
            return entries.toTypedArray().random().text
        }
    }
}

