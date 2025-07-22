package com.project.middleman.core.source.data.model

data class Challenge(
    val id: String = "",
    val payoutAmount: Double = 0.0,
    val title: String = "",
    val category: String = "",
    val participant: Map<String, ParticipantProgress> = emptyMap(),
    val visibility: String = "public", // or "invite-only or public" or "private"
    val status: String = "open", // open, active, completed
    val winnerId: String? = null,
    val createdAt: Long = 0L,
    val startDate: Long = 0L,
)

data class ParticipantProgress(
    val status: String = "", // Creator, Participant
    val userId: String = "",
    val name: String = "",
    val joinedAt: Long = 0L,
    val amount: Double = 0.0,
    val won: Boolean = false,
    val winAmount: Double = 0.00
)
