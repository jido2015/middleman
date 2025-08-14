package com.project.middleman.core.injection

import android.content.Context
import androidx.room.Room
import com.project.middleman.core.source.data.local.AppDatabase
import com.project.middleman.core.source.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app.db")
            // Add .addMigrations(MIGRATION_1_2, ...) when you version up
            .fallbackToDestructiveMigration(false)
            .build()

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

}