package com.project.middleman.core.source.data.model

data class Challenge(
    val id: String = "",
    val payoutAmount: Double = 0.0,
    val title: String = "",
    val description: String = "",
    val participant: Map<String, ParticipantProgress> = emptyMap(),
    val visibility: String = "public", // or "invite-only or public"
    val status: String = "open", // open, active, completed
    val winnerId: String? = null,
    val createdAt: String = "",
    val startDate: String = "",
    val endDate: String = ""
)

data class ParticipantProgress(
    val status: String = "", // Creator, Participant
    val userId: String = "",
    val name: String = "",
    val joinedAt: String = "",
    val amount: Double = 0.0,
    val won: Boolean = false,
    val winAmount: Double = 0.00
)
