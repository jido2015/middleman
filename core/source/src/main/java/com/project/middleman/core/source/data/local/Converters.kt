package com.project.middleman.core.source.data.local

import androidx.room.TypeConverter
import com.google.firebase.Timestamp

object Converters {

    @TypeConverter
    fun fromTimestamp(value: Timestamp?): Long? {
        return value?.toDate()?.time // convert to epoch milliseconds
    }

    @TypeConverter
    fun toTimestamp(value: Long?): Timestamp? {
        return value?.let { Timestamp(it / 1000, ((it % 1000) * 1_000_000).toInt()) }
    }
}