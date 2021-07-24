package com.test.shaadi.data.repository.user

import com.test.shaadi.data.local.db.dao.UserDAO
import com.test.shaadi.data.local.db.entity.UserEntity
import com.test.shaadi.data.network.api.UserApi
import javax.inject.Inject

class UserRepo @Inject constructor(private val userApi: UserApi, private val userDAO: UserDAO) {

    suspend fun getUserDataFromServer(count: Int = 10) = userApi.getUserData(count)

    suspend fun insertAll(userList: ArrayList<UserEntity>) = userDAO.insert(userList)
    suspend fun getAll() = userDAO.getAll()
    suspend fun updateUser(userEntity: UserEntity) = userDAO.updateUser(userEntity)
}