package com.project.middleman.core.source.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.middleman.core.source.data.local.dao.UserDao
import com.project.middleman.core.source.data.local.entity.UserEntity


@Database(entities = [UserEntity::class], version = 1)
@TypeConverters(Converters::class)

abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

}