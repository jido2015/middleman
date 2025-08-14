package com.project.middleman.core.common

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun formatDateOfBirth(input: String): String {
    val digits = input.filter { it.isDigit() }
    val limited = if (digits.length >= 8) digits.substring(0, 8) else digits

    return buildString {
        limited.forEachIndexed { index, c ->
            when (index) {
                0, 1 -> append(c)              // MM
                2 -> append("/").append(c)     // add slash after MM
                3 -> append(c)                 // DD
                4 -> append("/").append(c)     // add slash after DD
                else -> append(c)              // YYYY
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
fun isUser18OrOlder(dobString: String): Boolean {
    return try {
        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        val dob = LocalDate.parse(dobString, formatter)
        val today = LocalDate.now()

        val age = ChronoUnit.YEARS.between(dob, today)
        age >= 18
    } catch (e: Exception) {
        false // parsing failed or invalid date
    }
}
