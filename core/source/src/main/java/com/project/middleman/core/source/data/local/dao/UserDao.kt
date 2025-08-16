package com.project.middleman.core.source.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.project.middleman.core.source.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Upsert
    suspend fun upsertUser(user: UserEntity)

    @Query("SELECT * FROM userData LIMIT 1")
    fun observeUser(): Flow<UserEntity?>

    @Query("SELECT * FROM userData LIMIT 1")
    suspend fun getCurrentUserOnce(): UserEntity?  // nullable

    @Query("DELETE FROM userData")
    suspend fun clear()
}
