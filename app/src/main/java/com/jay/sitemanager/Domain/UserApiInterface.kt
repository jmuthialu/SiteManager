package com.jay.sitemanager.Domain

import com.jay.sitemanager.DataModels.User
import retrofit2.http.GET

interface UserApiInterface {

    @GET("photos")
    suspend fun getUsers(): List<User>

}