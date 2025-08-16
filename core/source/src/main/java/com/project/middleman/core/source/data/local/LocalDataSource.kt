package com.project.middleman.core.source.data.local

import com.project.middleman.core.source.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    suspend fun upsert(user: UserEntity)
    fun observeCurrentUser(): Flow<UserEntity?>
    suspend fun clear()

    suspend fun getCurrentUserOnce(): UserEntity?
}
