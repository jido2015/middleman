package com.project.middleman.feature.createchallenge.components


fun calculateFee(betAmount: Double): Double {
    val rawFee = when (betAmount) {
        in 0.0..20.0 -> betAmount * 0.06        // 6%
        in 20.01..100.0 -> betAmount * 0.04     // 4%
        else -> betAmount * 0.025               // 2.5%
    }

    return rawFee.coerceAtMost(25.0) // Cap fee at $25
}
