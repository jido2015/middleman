package com.project.middleman.core.injection

import com.project.middleman.core.source.data.local.UserLocalDataSource
import com.project.middleman.core.source.data.local.UserLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalBindingsModule {
    @Binds
    abstract fun bindUserLocalDataSource(
        impl: UserLocalDataSourceImpl
    ): UserLocalDataSource
}