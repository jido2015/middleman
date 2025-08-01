package com.project.middleman.core.source.data.model

data class Challenge(
    val id: String = "",
    val payoutAmount: Double = 0.0,
    val title: String = "",
    val category: String = "",
    val participant: Map<String, ParticipantProgress> = emptyMap(),
    val visibility: Boolean = true, // or "invite-only or public"
    val status: String = "open", // open, pending, closed.
    // Open means the challenge is open for participants to join.
    // Pending means the a participant has requested to join the challenge.
    // Closed means the challenge has been taken and no participants can join it again.
    val winnerId: String? = null,
    val createdAt: Long = 0L,
    val startDate: Long = 0L,
    val description: String = ""
)

data class ParticipantProgress(
    val status: String = "", // Creator, Participant
    val userId: String = "",
    val displayName: String = "",
    val photoUrl: String = "",
    val joinedAt: Long = 0L,
    val amount: Double = 0.0,
    val won: Boolean = false,
    val winAmount: Double = 0.00
)
