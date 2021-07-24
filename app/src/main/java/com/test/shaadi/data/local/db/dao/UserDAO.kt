package com.test.shaadi.data.local.db.dao

import androidx.room.*
import com.test.shaadi.data.local.db.entity.UserEntity

@Dao
interface UserDAO {
    @Query("SELECT * FROM userentity")
    suspend fun getAll(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: ArrayList<UserEntity>):List<Long>

    @Update
    suspend fun updateUser(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)

}