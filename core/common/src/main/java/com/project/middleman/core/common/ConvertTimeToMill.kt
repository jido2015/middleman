package com.project.middleman.core.common

import java.util.Calendar

fun getSelectedTimeInMillis(hour: Int, minute: Int): Long {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)

        // If time is in the past today, move to tomorrow
        if (timeInMillis < System.currentTimeMillis()) {
            add(Calendar.DAY_OF_MONTH, 1)
        }
    }
    return calendar.timeInMillis
}
