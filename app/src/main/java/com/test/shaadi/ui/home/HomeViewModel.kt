package com.test.shaadi.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.test.shaadi.data.local.db.dao.UserDAO
import com.test.shaadi.data.local.db.entity.UserEntity
import com.test.shaadi.data.network.Resource
import com.test.shaadi.data.repository.user.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepo: UserRepo
) : ViewModel() {

    fun getUserDataFromServer(count: Int = 10) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = userRepo.getUserDataFromServer(count)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, msg = exception.message ?: "Error Occurred!"))
        }
    }


    fun insertAllUsersToDB(userList: ArrayList<UserEntity>) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = userRepo.insertAll(userList)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, msg = exception.message ?: "Error Occurred!"))
        }
    }

    fun getAllUsersFromDB() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = userRepo.getAll()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, msg = exception.message ?: "Error Occurred!"))
        }
    }


    fun updateUserInDB(userEntity: UserEntity) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = userRepo.updateUser(userEntity)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, msg = exception.message ?: "Error Occurred!"))
        }
    }
}