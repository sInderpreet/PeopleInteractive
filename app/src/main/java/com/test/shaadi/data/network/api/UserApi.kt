package com.test.shaadi.data.network.api

import com.test.shaadi.data.network.pojo.user.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("api")
    suspend fun getUserData(@Query("results") result: Int = 10): Response<UserResponse>


}