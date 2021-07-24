package com.test.shaadi.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.test.shaadi.utils.AppConstants


@Entity
data class UserEntity(

    @PrimaryKey var id: Int,
    @ColumnInfo(name = "first_name") var firstName: String?,
    @ColumnInfo(name = "last_name") var lastName: String?,
    @ColumnInfo(name = "age") var age: Int?,
    @ColumnInfo(name = "state") var state: String?,
    @ColumnInfo(name = "country") var country: String?,
    @ColumnInfo(name = "status") var status: String = AppConstants.UserStatus.STATUS_NOT_DEFINED,
    @ColumnInfo(name = "image_url") var userAvatar: String?,
)
