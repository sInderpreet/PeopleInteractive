package com.test.shaadi.di

import android.content.Context
import androidx.room.Room
import com.test.shaadi.data.local.db.AppDatabase
import com.test.shaadi.data.local.db.dao.UserDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DBModule {

    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDAO {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "Shaadi"
        ).build()
    }
}