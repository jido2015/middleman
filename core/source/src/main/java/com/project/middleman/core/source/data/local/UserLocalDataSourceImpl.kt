package com.project.middleman.core.source.data.local

import com.project.middleman.core.source.data.local.dao.UserDao
import com.project.middleman.core.source.data.local.entity.UserEntity
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(
    private val userDao: UserDao
) : UserLocalDataSource {
    override suspend fun upsert(user: UserEntity) = userDao.upsertUser(user)
    override fun observeCurrentUser() = userDao.observeUser()
    override suspend fun clear() = userDao.clear()
}
