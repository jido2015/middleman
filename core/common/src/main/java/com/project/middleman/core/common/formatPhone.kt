package com.project.middleman.core.common

fun formatPhoneNumber(input: String): String {
    // Remove all non-digit characters
    val digitsOnly = input.filter { it.isDigit() }

    // Format as: XXX-XXX-XXXX
    return buildString {
        digitsOnly.forEachIndexed { index, c ->
            when (index) {
                3 -> append("-$c") // after first 3 digits
                6 -> append("-$c") // after next 3 digits
                else -> append(c)
            }
        }
    }
}
