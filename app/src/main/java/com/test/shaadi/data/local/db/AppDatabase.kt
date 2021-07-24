package com.test.shaadi.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.shaadi.data.local.db.dao.UserDAO
import com.test.shaadi.data.local.db.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO
}